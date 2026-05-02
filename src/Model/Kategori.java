package Model;

public class Kategori {
    private int idKategori;
    private String namaKategori;
    private int idTokoFk;
    
    public Kategori(int idKategori, String namaKategori, int idTokoFk) {
        this.idKategori = idKategori;
        this.namaKategori = namaKategori;
        this.idTokoFk = idTokoFk;
    }
    
    public Kategori(String namaKategori, int idTokoFk) {
        this.namaKategori = namaKategori;
        this.idTokoFk = idTokoFk;
    }

    // --- Getter dan Setter ---
    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) { this.idKategori = idKategori; }
    public String getNamaKategori() { return namaKategori; }
    public void setNamaKategori(String namaKategori) { this.namaKategori = namaKategori; }
    public int getIdTokoFk() { return idTokoFk; }
    public void setIdTokoFk(int idTokoFk) { this.idTokoFk = idTokoFk; }

    @Override
    public String toString() {
        return "ID: " + idKategori + ", Nama Kategori: " + namaKategori;
    }
}