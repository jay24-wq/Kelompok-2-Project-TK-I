package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.DatabaseConnection;
import Model.Kategori;

public class KategoriDAO {

    // 1. TAMBAH KATEGORI (C)
    public void tambahKategori(int idToko, String namaKategori) throws SQLException {
        String sql = "INSERT INTO tabel_kategori (id_toko_fk, nama_kategori) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            pstmt.setString(2, namaKategori);
            pstmt.executeUpdate();
        }
    }

    // 2. UPDATE KATEGORI (U)
    public void updateKategori(int idKategori, String namaBaru) throws SQLException {
        String sql = "UPDATE tabel_kategori SET nama_kategori = ? WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaBaru);
            pstmt.setInt(2, idKategori);
            pstmt.executeUpdate();
        }
    }

    // 3. HAPUS KATEGORI (D)
    public void deleteKategori(int idKategori) throws SQLException {
        String sql = "DELETE FROM tabel_kategori WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idKategori);
            pstmt.executeUpdate();
        }
    }

    // 4. AMBIL SATU KATEGORI BERDASARKAN ID (R)
    public Kategori getKategoriById(int idKategori) throws SQLException {
        // Kita ambil juga id_toko_fk agar constructor lengkap
        String sql = "SELECT id_kategori, nama_kategori, id_toko_fk FROM tabel_kategori WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idKategori);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Kategori(
                        rs.getInt("id_kategori"), 
                        rs.getString("nama_kategori"),
                        rs.getInt("id_toko_fk") // Menyesuaikan model
                    );
                }
            }
        }
        return null;
    }

    // 5. AMBIL SEMUA KATEGORI DALAM SATU TOKO (R)
    public List<Kategori> getAllKategori(int idToko) throws SQLException {
        List<Kategori> daftar = new ArrayList<>();
        String sql = "SELECT id_kategori, nama_kategori, id_toko_fk FROM tabel_kategori WHERE id_toko_fk = ? ORDER BY id_kategori";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idToko);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) { 
                    daftar.add(new Kategori(
                        rs.getInt("id_kategori"), 
                        rs.getString("nama_kategori"),
                        rs.getInt("id_toko_fk") // Menyesuaikan model
                    )); 
                }
            }
        }
        return daftar;
    }
    
    public int getIdByName(String nama, int idToko) {
    int id = 0;
    String sql = "SELECT id_kategori FROM tabel_kategori WHERE nama_kategori = ? AND id_toko_fk = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, nama);
        ps.setInt(2, idToko);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) id = rs.getInt("id_kategori");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return id;
    }
}