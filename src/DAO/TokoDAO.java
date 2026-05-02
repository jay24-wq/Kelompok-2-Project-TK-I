package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.DatabaseConnection;
import Model.Toko;

public class TokoDAO {

    // 1. TAMBAH TOKO (C)
    public int tambahToko(String namaToko, String alamatToko, String pemilikToko) throws SQLException {
        String sql = "INSERT INTO tabel_toko (nama_toko, alamat_toko, pemilik_toko) VALUES (?, ?, ?)";
    
        // Tambahkan flag RETURN_GENERATED_KEYS
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, namaToko);
            pstmt.setString(2, alamatToko);
            pstmt.setString(3, pemilikToko);
            pstmt.executeUpdate();

            // Ambil kunci/ID yang baru saja dibuat oleh database
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Mengembalikan ID baru
                } else {
                    throw new SQLException("Gagal mendapatkan ID Toko baru.");
                }
            }
        }
    }
    
    // 2. UPDATE TOKO (U)
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
    
    // 3. HAPUS TOKO (D)
    public void deleteToko(int idToko) throws SQLException {
        String sql = "DELETE FROM tabel_toko WHERE id_toko = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            if (pstmt.executeUpdate() == 0) throw new SQLException("Toko ID " + idToko + " tidak ditemukan.");
        }
    }

    // 4. AMBIL SEMUA TOKO (R)
    public List<Toko> getAllToko() throws SQLException {
        List<Toko> daftar = new ArrayList<>();
        String sql = "SELECT id_toko, nama_toko, alamat_toko, pemilik_toko FROM tabel_toko ORDER BY id_toko";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                daftar.add(new Toko(
                    rs.getInt("id_toko"), 
                    rs.getString("nama_toko"), 
                    rs.getString("alamat_toko"), 
                    rs.getString("pemilik_toko")
                ));
            }
        }
        return daftar;
    }
    
    // 5. AMBIL SATU TOKO BERDASARKAN ID (R)
    public Toko getTokoById(int idToko) throws SQLException {
        String sql = "SELECT id_toko, nama_toko, alamat_toko, pemilik_toko FROM tabel_toko WHERE id_toko = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Toko(
                        rs.getInt("id_toko"), 
                        rs.getString("nama_toko"), 
                        rs.getString("alamat_toko"), 
                        rs.getString("pemilik_toko")
                    );
                }
            }
        }
        return null;
    }
}