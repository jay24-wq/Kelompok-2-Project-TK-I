package Model;

import java.util.ArrayList;
import java.util.List;

public class Produk {
    private int idProduk;
    private String namaProduk;
    private double harga;
    private int idTokoFk; 
    private List<Kategori> daftarKategori;
    
    // Atribut tambahan untuk mempermudah tampilan di JTable
    private String kategoriTampilan; 

    // Constructor untuk tambah data (tanpa ID karena Auto Increment)
    public Produk(int idTokoFk, String namaProduk, double harga) {
        this.idTokoFk = idTokoFk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.daftarKategori = new ArrayList<>();
    }

    // Constructor lengkap (biasanya digunakan saat mengambil data dari DB)
    public Produk(int idProduk, int idTokoFk, String namaProduk, double harga) {
        this.idProduk = idProduk;
        this.idTokoFk = idTokoFk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.daftarKategori = new ArrayList<>();
    }
    
    public Produk() {
         this.daftarKategori = new ArrayList<>();
    }

    // Getter dan Setter Standar
    public int getIdProduk() { return idProduk; }
    public void setIdProduk(int idProduk) { this.idProduk = idProduk; }
    
    public int getIdTokoFk() { return idTokoFk; }
    public void setIdTokoFk(int idTokoFk) { this.idTokoFk = idTokoFk; }
    
    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }
    
    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }
    
    public List<Kategori> getDaftarKategori() { return daftarKategori; }
    public void setDaftarKategori(List<Kategori> daftarKategori) { this.daftarKategori = daftarKategori; }
    
    // Getter dan Setter untuk Kategori Tampilan (Menyamping)
    public String getKategoriTampilan() { 
        return (kategoriTampilan == null || kategoriTampilan.isEmpty()) ? "-" : kategoriTampilan; 
    }
    
    public void setKategoriTampilan(String kategoriTampilan) { 
        this.kategoriTampilan = kategoriTampilan; 
    }
    
    @Override
    public String toString() {
        return "ID: " + idProduk + ", Nama: " + namaProduk + ", Harga: " + harga;
    }
}