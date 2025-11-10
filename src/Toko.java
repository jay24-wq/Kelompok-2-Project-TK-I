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
}
