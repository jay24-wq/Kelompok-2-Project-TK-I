package Model;

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
    
    public Toko(String namaToko, String alamatToko, String pemilikToko) {
        this.namaToko = namaToko;
        this.alamatToko = alamatToko;
        this.pemilikToko = pemilikToko;
    }

    public int getIdToko() { return idToko; }
    public void setIdToko(int idToko) { this.idToko = idToko; }
    public String getNamaToko() { return namaToko; }
    public void setNamaToko(String namaToko) { this.namaToko = namaToko; }
    public String getAlamatToko() { return alamatToko; }
    public void setAlamatToko(String alamatToko) { this.alamatToko = alamatToko; }
    public String getPemilikToko() { return pemilikToko; }
    public void setPemilikToko(String pemilikToko) { this.pemilikToko = pemilikToko; }
}