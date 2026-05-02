package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Util.DatabaseConnection;

public abstract class BaseDAO<T> implements GenericDAO<T> {

    protected Connection connection;
    protected String tableName; 
    protected String idColumnName; 

    public BaseDAO(String tableName, String idColumnName) {
        this.tableName = tableName;
        this.idColumnName = idColumnName;
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("Gagal mendapatkan koneksi database: " + e.getMessage());
            // Dalam aplikasi Swing, Anda mungkin ingin melempar RuntimeException di sini
        }
    }
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;
    
    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + idColumnName + " = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) throw new SQLException(tableName + " ID " + id + " tidak ditemukan.");
            return true;
        } catch (SQLException e) {
            // Logika Swing: melempar exception agar Controller yang menangkap
            throw new SQLException("Gagal menghapus data dari tabel " + tableName + ": " + e.getMessage(), e);
        }
    }
}