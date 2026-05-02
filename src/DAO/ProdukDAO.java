package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.DatabaseConnection;
import Model.Produk;

public class ProdukDAO {

    // 1. TAMBAH PRODUK (C)
    public void tambahProduk(Produk produk) throws SQLException {
    String sqlProduk = "INSERT INTO tabel_produk (id_toko_fk, nama_produk, harga) VALUES (?, ?, ?)";
    String sqlRelasi = "INSERT INTO tabel_produk_kategori (id_produk_fk, id_kategori_fk) VALUES (?, ?)";
    
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Mulai Transaksi

            try (PreparedStatement pstmtP = conn.prepareStatement(sqlProduk, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmtP.setInt(1, produk.getIdTokoFk());
                pstmtP.setString(2, produk.getNamaProduk());
                pstmtP.setDouble(3, produk.getHarga());
                pstmtP.executeUpdate();

                // Ambil ID Produk yang baru saja dibuat
                try (ResultSet rs = pstmtP.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idBaru = rs.getInt(1);
                        // Simpan Relasi Kategori
                        try (PreparedStatement pstmtR = conn.prepareStatement(sqlRelasi)) {
                            for (Model.Kategori kat : produk.getDaftarKategori()) {
                                pstmtR.setInt(1, idBaru);
                                pstmtR.setInt(2, kat.getIdKategori());
                                pstmtR.addBatch();
                            }
                            pstmtR.executeBatch();
                        }
                    }
                }
                conn.commit(); // Simpan permanen
            } catch (SQLException e) {
                conn.rollback(); // Batalkan jika ada error
                throw e;
            }
        }
    }

    // 2. UPDATE PRODUK (U)
    public void updateProduk(int idProduk, String namaBaru, double hargaBaru, List<Model.Kategori> daftarKat) throws SQLException {
    String sqlUpdateProduk = "UPDATE tabel_produk SET nama_produk = ?, harga = ? WHERE id_produk = ?";
    String sqlDeleteRelasi = "DELETE FROM tabel_produk_kategori WHERE id_produk_fk = ?";
    String sqlInsertRelasi = "INSERT INTO tabel_produk_kategori (id_produk_fk, id_kategori_fk) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 1. Update Nama dan Harga
                try (PreparedStatement pstmtU = conn.prepareStatement(sqlUpdateProduk)) {
                    pstmtU.setString(1, namaBaru);
                    pstmtU.setDouble(2, hargaBaru);
                    pstmtU.setInt(3, idProduk);
                    pstmtU.executeUpdate();
                }

                // 2. Hapus Kategori Lama
                try (PreparedStatement pstmtD = conn.prepareStatement(sqlDeleteRelasi)) {
                    pstmtD.setInt(1, idProduk);
                    pstmtD.executeUpdate();
                }

                // 3. Masukkan Kategori Baru
                try (PreparedStatement pstmtI = conn.prepareStatement(sqlInsertRelasi)) {
                    for (Model.Kategori kat : daftarKat) {
                        pstmtI.setInt(1, idProduk);
                        pstmtI.setInt(2, kat.getIdKategori());
                        pstmtI.addBatch();
                    }
                    pstmtI.executeBatch();
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // 3. HAPUS PRODUK (D)
    public void deleteProduk(int idProduk) throws SQLException {
        String sql = "DELETE FROM tabel_produk WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProduk);
            pstmt.executeUpdate();
        }
    }

    // 4. AMBIL SATU PRODUK BERDASARKAN ID (R)
    public Produk getProdukById(int idProduk) throws SQLException {
        String sql = "SELECT id_produk, id_toko_fk, nama_produk, harga FROM tabel_produk WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProduk);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Produk(
                        rs.getInt("id_produk"), 
                        rs.getInt("id_toko_fk"), 
                        rs.getString("nama_produk"), 
                        rs.getDouble("harga")
                    );
                }
            }
        }
        return null;
    }

    // 5. AMBIL SEMUA PRODUK DALAM SATU TOKO + KATEGORI (R)
    public List<Produk> getAllProduk(int idToko) throws SQLException {
    List<Produk> daftar = new ArrayList<>();
    
    // Query ini menggabungkan nama_kategori menjadi satu string dipisahkan koma
    String sql = "SELECT p.id_produk, p.id_toko_fk, p.nama_produk, p.harga, " +
                 "GROUP_CONCAT(k.nama_kategori SEPARATOR ', ') AS daftar_kategori " +
                 "FROM tabel_produk p " +
                 "LEFT JOIN tabel_produk_kategori pk ON p.id_produk = pk.id_produk_fk " +
                 "LEFT JOIN tabel_kategori k ON pk.id_kategori_fk = k.id_kategori " +
                 "WHERE p.id_toko_fk = ? " +
                 "GROUP BY p.id_produk " +
                 "ORDER BY p.id_produk";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        
        pstmt.setInt(1, idToko);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Produk p = new Produk(
                    rs.getInt("id_produk"),
                    rs.getInt("id_toko_fk"),
                    rs.getString("nama_produk"),
                    rs.getDouble("harga")
                );
                
                // Ambil hasil GROUP_CONCAT
                String kategori = rs.getString("daftar_kategori");
                
                // Set ke variabel penampung di model Produk
                // Pastikan Anda sudah membuat method setKategoriTampilan di Produk.java
                p.setKategoriTampilan(kategori == null ? "-" : kategori);
                
                daftar.add(p);
            }
        }
    }
    return daftar;
    }
    
    public void tambahRelasiKategori(int idProduk, List<Integer> idKategoriList) throws SQLException {
    String sql = "INSERT INTO tabel_produk_kategori (id_produk_fk, id_kategori_fk) VALUES (?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Integer idKat : idKategoriList) {
                pstmt.setInt(1, idProduk);
                pstmt.setInt(2, idKat);
                pstmt.addBatch(); // Gunakan batch agar cepat jika kategori banyak
            }
            pstmt.executeBatch();
        }
    }
    
    // 6. AMBIL KATEGORI BERDASARKAN ID PRODUK
    public List<Model.Kategori> getKategoriByProdukId(int idProduk) throws SQLException {
        List<Model.Kategori> list = new ArrayList<>();
        // Query ini mengambil data kategori yang terelasi dengan produk tertentu
        String sql = "SELECT k.id_kategori, k.id_toko_fk, k.nama_kategori " +
                     "FROM tabel_kategori k " +
                     "JOIN tabel_produk_kategori pk ON k.id_kategori = pk.id_kategori_fk " +
                     "WHERE pk.id_produk_fk = ?";

        try (Connection conn = Util.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProduk);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(new Model.Kategori(
                        rs.getInt("id_kategori"),
                        rs.getString("nama_kategori"),
                        rs.getInt("id_toko_fk")
                    ));
                }
            }
        }
        return list;
    }
}