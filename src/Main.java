// File: Main.java (Revisi untuk menampilkan data sebelum Edit)
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // Helper method untuk memuat data produk lengkap (logika OOP)
    private static List<Produk> loadProdukLengkap(Toko dao) throws SQLException {
        List<Produk> daftarProduk = dao.getAllProduk();
        List<Kategori> daftarKategori = dao.getAllKategori();
        Map<Integer, Kategori> mapKategori = new HashMap<>();
        
        for (Kategori k : daftarKategori) {
            mapKategori.put(k.getIdKategori(), k);
        }

        Map<Integer, List<Integer>> relasiMap = dao.getAllRelasiProdukKategori();
        
        for (Produk p : daftarProduk) {
            int idProduk = p.getIdProduk();
            
            if (relasiMap.containsKey(idProduk)) {
                List<Kategori> kategoriTerhubung = new ArrayList<>();
                List<Integer> listIdKategori = relasiMap.get(idProduk);
                
                for (int idKategori : listIdKategori) {
                    if (mapKategori.containsKey(idKategori)) {
                        kategoriTerhubung.add(mapKategori.get(idKategori));
                    }
                }
                p.setDaftarKategori(kategoriTerhubung);
            }
        }
        return daftarProduk;
    }

    // Helper method untuk menampilkan data produk (format output sederhana)
    private static void displayProdukTable(Toko dao) throws SQLException {
        List<Produk> produkLengkap = loadProdukLengkap(dao);
        
        System.out.println("\n--- DATA PRODUK LENGKAP ---");
        
        if (produkLengkap.isEmpty()) {
            System.out.println("Belum ada data produk.");
        } else {
            for (Produk p : produkLengkap) {
                StringBuilder sb = new StringBuilder();
                if (p.getDaftarKategori().isEmpty()) {
                    sb.append("-");
                } else {
                    for (int i = 0; i < p.getDaftarKategori().size(); i++) {
                        sb.append(p.getDaftarKategori().get(i).getNamaKategori());
                        if (i < p.getDaftarKategori().size() - 1) {
                            sb.append(", ");
                        }
                    }
                }
                String listKategori = sb.toString();
                String hargaFormatted = String.format("Rp %,.2f", p.getHarga());

                System.out.println("ID :" + p.getIdProduk() + 
                                   " | Nama :" + p.getNamaProduk() + 
                                   " | Harga :" + hargaFormatted + 
                                   " | Kategori :" + listKategori);
            }
        }
        System.out.println("---------------------------");
    }
    
    // Helper method untuk menampilkan data kategori (tidak berubah)
    private static void displayKategoriList(Toko dao) throws SQLException {
        System.out.println("\n--- DAFTAR KATEGORI (Master Data) ---");
        List<Kategori> daftarK = dao.getAllKategori();
        if (daftarK.isEmpty()) {
            System.out.println("Belum ada kategori.");
        } else {
            // Menggunakan method toString() Kategori untuk tampilan yang rapi (ID: X, Nama: Y)
            daftarK.forEach(System.out::println); 
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Toko dao = new Toko(); 

        System.out.println("Selamat Datang di SimpleStore (Backend Test Console)");
        
        while (true) {
            System.out.println("\n===== MENU UTAMA =====");
            System.out.println("1. [Produk] Tambah");
            System.out.println("2. [Produk] Edit");
            System.out.println("3. [Produk] Hapus");
            System.out.println("----------------------");
            System.out.println("4. [Kategori] Tambah");
            System.out.println("5. [Kategori] Edit");
            System.out.println("6. [Kategori] Hapus");
            System.out.println("----------------------");
            System.out.println("7. [Relasi M:N] Atur Kategori Produk");
            System.out.println("8. [VIEW] Tampilkan Semua Data"); 
            System.out.println("9. Keluar");
            System.out.print("Pilih menu (1-9): ");

            int pilihan = 0;
            try {
                pilihan = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Input harus angka! Silakan coba lagi.");
                scanner.next(); 
                continue; 
            }
            scanner.nextLine(); 

            try {
                switch (pilihan) {
                    case 1 -> {
                        // Tambah Produk
                        System.out.print("Nama Produk: ");
                        String namaP = scanner.nextLine();
                        System.out.print("Harga Produk: ");
                        double hargaP = scanner.nextDouble();
                        scanner.nextLine(); 
                        dao.tambahProduk(new Produk(namaP, hargaP));
                    }
                    
                    case 2 -> {
                        // Edit Produk
                        displayProdukTable(dao); // <<<<<< MENAMPILKAN DATA PRODUK
                        System.out.print("Masukkan ID Produk yang akan diedit: ");
                        int idP_edit = scanner.nextInt();
                        scanner.nextLine();
                        Produk p_old = dao.getProdukById(idP_edit);
                        if (p_old == null) { System.out.println("Produk tidak ditemukan!"); break; }

                        System.out.println("Produk Lama: ID " + p_old.getIdProduk() + ", Nama: " + p_old.getNamaProduk() + ", Harga: " + p_old.getHarga());
                        System.out.print("Masukkan Nama Baru (" + p_old.getNamaProduk() + "): ");
                        String namaP_new = scanner.nextLine();
                        System.out.print("Masukkan Harga Baru (" + p_old.getHarga() + "): ");
                        double hargaP_new = scanner.nextDouble();
                        scanner.nextLine();
                        
                        p_old.setNamaProduk(namaP_new);
                        p_old.setHarga(hargaP_new);
                        dao.updateProduk(p_old);
                    }

                    case 3 -> {
                        // Hapus Produk
                        displayProdukTable(dao); // <<<<<< MENAMPILKAN DATA PRODUK
                        System.out.print("Masukkan ID Produk yang akan dihapus: ");
                        int idP_hapus = scanner.nextInt();
                        scanner.nextLine();
                        dao.deleteProduk(idP_hapus);
                    }
                    
                    case 4 -> {
                        // Tambah Kategori
                        System.out.print("Nama Kategori: ");
                        String namaK = scanner.nextLine();
                        dao.tambahKategori(namaK);
                    }
                    
                    case 5 -> {
                        // Edit Kategori
                        displayKategoriList(dao); // <<<<<< MENAMPILKAN DATA KATEGORI
                        System.out.print("Masukkan ID Kategori yang akan diedit: ");
                        int idK_edit = scanner.nextInt();
                        scanner.nextLine();
                        
                        // Opsional: Cek apakah Kategori ada untuk menampilkan nama lamanya
                        // Kategori k_old = dao.getKategoriById(idK_edit); // Metode ini belum dibuat, jadi kita skip, fokus ke input ID

                        System.out.print("Masukkan Nama Kategori Baru: ");
                        String namaK_new = scanner.nextLine();
                        
                        Kategori k_new = new Kategori(idK_edit, namaK_new);
                        dao.updateKategori(k_new);
                    }

                    case 6 -> {
                        // Hapus Kategori
                        displayKategoriList(dao); // <<<<<< MENAMPILKAN DATA KATEGORI
                        System.out.print("Masukkan ID Kategori yang akan dihapus: ");
                        int idK_hapus = scanner.nextInt();
                        scanner.nextLine();
                        dao.deleteKategori(idK_hapus);
                    }
                    
                    case 7 -> {
                        // Atur Relasi (Logika M:N)
                        displayProdukTable(dao);
                        displayKategoriList(dao); 
                        
                        System.out.print("\nMasukkan ID Produk yang akan diatur: ");
                        int idP_relasi = scanner.nextInt();
                        scanner.nextLine();
                        
                        System.out.print("Masukkan ID Kategori BARU (pisahkan koma, cth: 1,3. Kosongkan untuk menghapus relasi): ");
                        String kategoriIdsStr = scanner.nextLine();
                        
                        List<Integer> idKategoriList = new ArrayList<>();
                        if (!kategoriIdsStr.trim().isEmpty()) {
                            String[] ids = kategoriIdsStr.split(",");
                            for (String idStr : ids) {
                                idKategoriList.add(Integer.valueOf(idStr.trim()));
                            }
                        }
                        dao.setRelasiProdukKategori(idP_relasi, idKategoriList);
                    }
                    
                    case 8 -> {
                        // Tampilkan Semua Data
                        displayProdukTable(dao);
                        displayKategoriList(dao);
                    }
                    
                    case 9 -> {
                        System.out.println("Program dihentikan.");
                        scanner.close();
                        return;
                    }

                    default -> System.out.println("Pilihan tidak valid.");
                }
            
            } catch (SQLException e) {
                System.err.println("\n!!! TERJADI KESALAHAN DATABASE !!!");
                System.err.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("\n!!! INPUT SALAH atau DATA TIDAK DITEMUKAN !!!");
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
