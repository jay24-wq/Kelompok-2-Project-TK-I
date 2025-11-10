import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Toko {

    public void tambahProduk(Produk p) throws SQLException {
        String sql = "INSERT INTO tabel_produk (nama_produk, harga) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, p.getNamaProduk());
            pstmt.setDouble(2, p.getHarga());
            pstmt.executeUpdate();
            System.out.println("BERHASIL: Produk " + p.getNamaProduk() + " ditambahkan.");
        }
    }

    public void updateProduk(Produk p) throws SQLException {
        String sql = "UPDATE tabel_produk SET nama_produk = ?, harga = ? WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, p.getNamaProduk());
            pstmt.setDouble(2, p.getHarga());
            pstmt.setInt(3, p.getIdProduk());
            pstmt.executeUpdate();
            System.out.println("BERHASIL: Produk ID " + p.getIdProduk() + " diperbarui.");
        }
    }
    
    public void deleteProduk(int idProduk) throws SQLException {
        String sql = "DELETE FROM tabel_produk WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idProduk);
            pstmt.executeUpdate();
            System.out.println("BERHASIL: Produk ID " + idProduk + " dihapus.");
        }
    }
    
    public Produk getProdukById(int idProduk) throws SQLException {
        String sql = "SELECT * FROM tabel_produk WHERE id_produk = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idProduk);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Produk(rs.getInt("id_produk"), rs.getString("nama_produk"), rs.getDouble("harga"));
                }
            }
        }
        return null; 
    }
    
    public List<Produk> getAllProduk() throws SQLException {
        List<Produk> produkList = new ArrayList<>(); 
        String sql = "SELECT * FROM tabel_produk ORDER BY id_produk";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                produkList.add(new Produk(
                    rs.getInt("id_produk"),
                    rs.getString("nama_produk"),
                    rs.getDouble("harga")
                ));
            }
        }
        return produkList;
    }
    
    public void tambahKategori(String namaKategori) throws SQLException {
        String sql = "INSERT INTO tabel_kategori (nama_kategori) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, namaKategori);
            pstmt.executeUpdate();
            System.out.println("BERHASIL: Kategori " + namaKategori + " ditambahkan.");
        }
    }
    
    public void updateKategori(Kategori k) throws SQLException {
        String sql = "UPDATE tabel_kategori SET nama_kategori = ? WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, k.getNamaKategori());
            pstmt.setInt(2, k.getIdKategori());
            pstmt.executeUpdate();
            System.out.println("BERHASIL: Kategori ID " + k.getIdKategori() + " diperbarui.");
        }
    }
    
    public void deleteKategori(int idKategori) throws SQLException {
        String sql = "DELETE FROM tabel_kategori WHERE id_kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idKategori);
            pstmt.executeUpdate();
            System.out.println("BERHASIL: Kategori ID " + idKategori + " dihapus.");
        }
    }

}
