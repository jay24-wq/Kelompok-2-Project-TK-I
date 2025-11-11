import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Toko {
    private int idToko;
    private String namaToko;
    private String alamatToko;
    private String pemilikToko;
    
    public Toko() {}
    public Toko(int idToko, String namaToko, String alamatToko, String pemilikToko) {
        this.idToko = idToko;
        this.namaToko = namaToko;
        this.alamatToko = alamatToko;
        this.pemilikToko = pemilikToko;
    }

    public int getIdToko() { return idToko; }
    public String getNamaToko() { return namaToko; }
    public String getAlamatToko() { return alamatToko; }
    public String getPemilikToko() { return pemilikToko; }
    public void setNamaToko(String namaToko) { this.namaToko = namaToko; }
    public void setAlamatToko(String alamatToko) { this.alamatToko = alamatToko; }
    public void setPemilikToko(String pemilikToko) { this.pemilikToko = pemilikToko; }

     public void tambahToko(String namaToko, String alamatToko, String pemilikToko) throws SQLException {
        String sql = "INSERT INTO tabel_toko (nama_toko, alamat_toko, pemilik_toko) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaToko);
            pstmt.setString(2, alamatToko);
            pstmt.setString(3, pemilikToko);
            pstmt.executeUpdate();
        }
    }
    
    public void updateToko(Toko toko) throws SQLException {
        String sql = "UPDATE tabel_toko SET nama_toko = ?, alamat_toko = ?, pemilik_toko = ? WHERE id_toko = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, toko.getNamaToko());
            pstmt.setString(2, toko.getAlamatToko());
            pstmt.setString(3, toko.getPemilikToko());
            pstmt.setInt(4, toko.getIdToko());
            pstmt.executeUpdate();
        }
    }
    
    public void deleteToko(int idToko) throws SQLException {
        String sql = "DELETE FROM tabel_toko WHERE id_toko = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            if (pstmt.executeUpdate() == 0) throw new SQLException("Toko ID " + idToko + " tidak ditemukan.");
        }
    }

    public List<Toko> getAllToko() throws SQLException {
        List<Toko> daftar = new ArrayList<>();
        String sql = "SELECT id_toko, nama_toko, alamat_toko, pemilik_toko FROM tabel_toko ORDER BY id_toko";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                daftar.add(new Toko(rs.getInt("id_toko"), rs.getString("nama_toko"), rs.getString("alamat_toko"), rs.getString("pemilik_toko")));
            }
        }
        return daftar;
    }
    
    public Toko getTokoById(int idToko) throws SQLException {
        String sql = "SELECT id_toko, nama_toko, alamat_toko, pemilik_toko FROM tabel_toko WHERE id_toko = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Toko(rs.getInt("id_toko"), rs.getString("nama_toko"), rs.getString("alamat_toko"), rs.getString("pemilik_toko"));
            }
        }
        return null;
    }
    
public void tambahProduk(Produk produk) throws SQLException {
        String sql = "INSERT INTO tabel_produk (id_toko_fk, nama_produk, harga) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, produk.getIdTokoFk());
            pstmt.setString(2, produk.getNamaProduk());
            pstmt.setDouble(3, produk.getHarga());
            pstmt.executeUpdate();
        }
    }

    public void updateProduk(int idProduk, String namaBaru, double hargaBaru) throws SQLException {
        String sql = "UPDATE tabel_produk SET nama_produk = ?, harga = ? WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaBaru);
            pstmt.setDouble(2, hargaBaru);
            pstmt.setInt(3, idProduk);
            pstmt.executeUpdate();
        }
    }

    public void deleteProduk(int idProduk) throws SQLException {
        String sql = "DELETE FROM tabel_produk WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProduk);
            pstmt.executeUpdate();
        }
    }

    public Produk getProdukById(int idProduk) throws SQLException {
        String sql = "SELECT id_produk, id_toko_fk, nama_produk, harga FROM tabel_produk WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProduk);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Produk(rs.getInt("id_produk"), rs.getInt("id_toko_fk"), rs.getString("nama_produk"), rs.getDouble("harga"));
            }
        }
        return null;
    }

    public List<Produk> getAllProduk(int idToko) throws SQLException {
        List<Produk> daftar = new ArrayList<>();
        String sql = "SELECT id_produk, id_toko_fk, nama_produk, harga FROM tabel_produk WHERE id_toko_fk = ? ORDER BY id_produk";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) { daftar.add(new Produk(rs.getInt("id_produk"), rs.getInt("id_toko_fk"), rs.getString("nama_produk"), rs.getDouble("harga"))); }
            }
        }
        return daftar;
    }

    public void tambahKategori(int idToko, String namaKategori) throws SQLException {
        String sql = "INSERT INTO tabel_kategori (id_toko_fk, nama_kategori) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            pstmt.setString(2, namaKategori);
            pstmt.executeUpdate();
        }
    }

    public void updateKategori(int idKategori, String namaBaru) throws SQLException {
        String sql = "UPDATE tabel_kategori SET nama_kategori = ? WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaBaru);
            pstmt.setInt(2, idKategori);
            pstmt.executeUpdate();
        }
    }

    public void deleteKategori(int idKategori) throws SQLException {
        String sql = "DELETE FROM tabel_kategori WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idKategori);
            pstmt.executeUpdate();
        }
    }

    public Kategori getKategoriById(int idKategori) throws SQLException {
         String sql = "SELECT id_kategori, nama_kategori FROM tabel_kategori WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idKategori);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return new Kategori(rs.getInt("id_kategori"), rs.getString("nama_kategori"));
            }
        }
        return null;
    }

    public List<Kategori> getAllKategori(int idToko) throws SQLException {
        List<Kategori> daftar = new ArrayList<>();
        String sql = "SELECT id_kategori, nama_kategori FROM tabel_kategori WHERE id_toko_fk = ? ORDER BY id_kategori";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) { daftar.add(new Kategori(rs.getInt("id_kategori"), rs.getString("nama_kategori"))); }
            }
        }
        return daftar;
    }
    
    public void setRelasiProdukKategori(int idProduk, List<Integer> idKategoriList) throws SQLException {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); 

            String sqlDelete = "DELETE FROM tabel_produk_kategori WHERE id_produk_fk = ?";
            try (PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {
                pstmtDelete.setInt(1, idProduk);
                pstmtDelete.executeUpdate();
            }

            if (idKategoriList != null && !idKategoriList.isEmpty()) {
                String sqlInsert = "INSERT INTO tabel_produk_kategori (id_produk_fk, id_kategori_fk) VALUES (?, ?)";
                try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                    for (int idKategori : idKategoriList) {
                        pstmtInsert.setInt(1, idProduk);
                        pstmtInsert.setInt(2, idKategori);
                        pstmtInsert.addBatch();
                    }
                    pstmtInsert.executeBatch();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException rbk) {
                    System.err.println("Gagal Rollback: " + rbk.getMessage());
                }
            }
            throw new SQLException("Gagal mengatur relasi Produk-Kategori (pastikan ID Kategori valid): " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    public Map<Integer, List<Integer>> getAllRelasiProdukKategori(int idToko) throws SQLException {
        Map<Integer, List<Integer>> relasiMap = new HashMap<>();
        String sql = "SELECT p.id_produk_fk, p.id_kategori_fk " +
                     "FROM tabel_produk_kategori p " +
                     "JOIN tabel_produk prod ON p.id_produk_fk = prod.id_produk " +
                     "WHERE prod.id_toko_fk = ? ORDER BY p.id_produk_fk";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idToko);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int idProduk = rs.getInt("id_produk_fk");
                    int idKategori = rs.getInt("id_kategori_fk");
                    relasiMap.computeIfAbsent(idProduk, k -> new ArrayList<>()).add(idKategori);
                }
            }
        }
        return relasiMap;
    }
    
    public List<Produk> loadProdukLengkap(int idToko) throws SQLException {
        List<Produk> daftarProduk = this.getAllProduk(idToko);
        List<Kategori> daftarKategori = this.getAllKategori(idToko);
        Map<Integer, Kategori> mapKategori = daftarKategori.stream()
                .collect(Collectors.toMap(Kategori::getIdKategori, k -> k));
        Map<Integer, List<Integer>> relasiMap = this.getAllRelasiProdukKategori(idToko);
        
        for (Produk p : daftarProduk) {
            int idProduk = p.getIdProduk();
            if (relasiMap.containsKey(idProduk)) {
                List<Kategori> kategoriTerhubung = relasiMap.get(idProduk).stream()
                    .map(mapKategori::get)
                    .filter(k -> k != null)
                    .collect(Collectors.toList());
                p.setDaftarKategori(kategoriTerhubung);
            } else {
                 p.setDaftarKategori(new ArrayList<>());
            }
        }
        return daftarProduk;
    }
}

