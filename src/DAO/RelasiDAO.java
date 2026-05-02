package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Util.DatabaseConnection;
import Model.Produk;
import Model.Kategori;

public class RelasiDAO {

    // Menghubungkan Produk dengan banyak Kategori (Many-to-Many)
    public void setRelasiProdukKategori(int idProduk, List<Integer> idKategoriList) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        try {
            conn.setAutoCommit(false); // Mulai Transaksi

            // 1. Hapus relasi lama
            String deleteSql = "DELETE FROM tabel_relasi_produk_kategori WHERE id_produk_fk = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setInt(1, idProduk);
                pstmt.executeUpdate();
            }

            // 2. Masukkan relasi baru
            String insertSql = "INSERT INTO tabel_relasi_produk_kategori (id_produk_fk, id_kategori_fk) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                for (Integer idKategori : idKategoriList) {
                    pstmt.setInt(1, idProduk);
                    pstmt.setInt(2, idKategori);
                    pstmt.executeUpdate();
                }
            }

            conn.commit(); // Simpan permanen
        } catch (SQLException e) {
            conn.rollback(); // Batalkan jika ada error
            throw e;
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    // Fungsi canggih untuk menampilkan tabel Produk lengkap dengan Nama Kategorinya
    public List<Produk> loadProdukLengkap(int idToko) throws SQLException {
        List<Produk> listProduk = new ArrayList<>();
        
        // Query Join untuk mendapatkan Nama Kategori sekaligus
        String sql = "SELECT p.*, k.id_kategori, k.nama_kategori " +
                     "FROM tabel_produk p " +
                     "LEFT JOIN tabel_relasi_produk_kategori r ON p.id_produk = r.id_produk_fk " +
                     "LEFT JOIN tabel_kategori k ON r.id_kategori_fk = k.id_kategori " +
                     "WHERE p.id_toko_fk = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int idProduk = rs.getInt("id_produk");
                
                // Cek apakah produk sudah ada di list (untuk menangani join multiple categories)
                Produk p = listProduk.stream()
                        .filter(prod -> prod.getIdProduk() == idProduk)
                        .findFirst()
                        .orElse(null);

                if (p == null) {
                    p = new Produk(idProduk, rs.getInt("id_toko_fk"), 
                                   rs.getString("nama_produk"), rs.getDouble("harga"));
                    listProduk.add(p);
                }

                // Tambahkan kategori jika ada
                int idKat = rs.getInt("id_kategori");
                if (idKat > 0) {
                    p.getDaftarKategori().add(new Kategori(idKat, rs.getString("nama_kategori"), idToko));
                }
            }
        }
        return listProduk;
    }
}