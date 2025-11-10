import java.util.ArrayList;
import java.util.List;

public class Produk {
  private int idProduk;
  private String namaProduk;
  private double harga;
  private List<Kategori> daftarKategori;

  public Produk(String namaProduk, double harga) {
    this.namaProduk = namaProduk;
    this.harga = harga;
    this.daftarKategori = new ArrayList<>();
  }

  public Produk(int idProduk, String namaProduk, double harga) {
    this.idProduk = idProduk;
    this.namaProduk = namaProduk;
    this.harga = harga;
    this.daftarKategori = new ArrayList<>();
  }

  public int getIdProduk() {
    return idProduk;
  }

  public String getNamaProduk() {
    return namaProduk;
  }

  public void setNamaProduk(String namaProduk) {
    this.namaProduk = namaProduk;
  }

  public double getHarga() {
    return harga;
  }

  public void setHarga(double harga) {
    this.harga = harga;
  }

  public List<Kategori> getDaftarKategori() {
    return daftarKategori;
  }

  public void setDaftarKategori(List<Kategori> daftarKategori) {
    this.daftarKategori = daftarKategori;
  }

  @Override
  public String toString() {
    return "ID: " + idProduk + ", Nama: " + namaProduk + ", Harga: " + harga;
  }
}
