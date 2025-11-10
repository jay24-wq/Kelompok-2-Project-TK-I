import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static void tungguKonfirmasi(Scanner scanner) {
        System.out.println("\n-------------------------------------------");
        System.out.print("Operasi selesai. Tekan [ENTER] untuk kembali ke menu utama...");
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private static List<Produk> loadProdukLengkap(Toko toko) throws SQLException {
        List<Produk> daftarProduk = toko.getAllProduk();
        List<Kategori> daftarKategori = toko.getAllKategori();
        Map<Integer, Kategori> mapKategori = new HashMap<>();
        
        for (Kategori k : daftarKategori) {
            mapKategori.put(k.getIdKategori(), k);
        }

        Map<Integer, List<Integer>> relasiMap = toko.getAllRelasiProdukKategori();
        
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

    private static void displayProdukTable(Toko toko) throws SQLException {
        List<Produk> produkLengkap = loadProdukLengkap(toko);
        
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
    
    private static void displayKategoriList(Toko toko) throws SQLException {
        System.out.println("\n--- DAFTAR KATEGORI (Master Data) ---");
        List<Kategori> daftarK = toko.getAllKategori();
        if (daftarK.isEmpty()) {
            System.out.println("Belum ada kategori.");
        } else {
            daftarK.forEach(System.out::println); 
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Toko toko = new Toko(); 

        System.out.println("Selamat Datang di SimpleStore");
        
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
            System.out.println("7. Atur Kategori Produk");
            System.out.println("8. Tampilkan Semua Data"); 
            System.out.println("9. Keluar");
            System.out.print("Pilih menu (1-9): ");

            int pilihan = 0;
            try {
                if (scanner.hasNextInt()) {
                    pilihan = scanner.nextInt();
                } else {
                    System.out.println("\nInput harus berupa angka!");
                    scanner.next(); 
                    tungguKonfirmasi(scanner);
                    continue;
                }
                scanner.nextLine(); 

                switch (pilihan) {
                    case 1:
                        System.out.print("Nama Produk: ");
                        String namaP = scanner.nextLine();
                        System.out.print("Harga Produk: ");
                        double hargaP = scanner.nextDouble();
                        scanner.nextLine();
                        toko.tambahProduk(new Produk(namaP, hargaP));
                        System.out.println("\nProduk berhasil ditambahkan!");
                        tungguKonfirmasi(scanner);
                        break;
                    
                    case 2:
                        displayProdukTable(toko); 
                        System.out.print("Masukkan ID Produk yang akan diedit: ");
                        int idP_edit = scanner.nextInt();
                        scanner.nextLine();
                        Produk p_old = toko.getProdukById(idP_edit);
                        if (p_old == null) { System.out.println("Produk tidak ditemukan!"); break; }

                        System.out.println("\n--- EDIT Produk: " + p_old.getNamaProduk() + " ---");
                        System.out.print("Masukkan Nama Baru (" + p_old.getNamaProduk() + "): ");
                        String namaP_new = scanner.nextLine();
                        System.out.print("Masukkan Harga Baru (" + p_old.getHarga() + "): ");
                        double hargaP_new = scanner.nextDouble();
                        scanner.nextLine();
                        
                        p_old.setNamaProduk(namaP_new);
                        p_old.setHarga(hargaP_new);
                        toko.updateProduk(p_old);
                        System.out.println("\nProduk berhasil diperbarui!");
                        tungguKonfirmasi(scanner);
                        break;

                    case 3:
                        displayProdukTable(toko);
                        System.out.print("Masukkan ID Produk yang akan dihapus: ");
                        int idP_hapus = scanner.nextInt();
                        scanner.nextLine();
                        toko.deleteProduk(idP_hapus);
                        System.out.println("\nProduk berhasil dihapus!");
                        tungguKonfirmasi(scanner);
                        break;
                    
                    case 4:
                        System.out.print("Nama Kategori: ");
                        String namaK = scanner.nextLine();
                        toko.tambahKategori(namaK);
                        System.out.println("\nKategori berhasil ditambahkan!");
                        tungguKonfirmasi(scanner);
                        break;
                    
                    case 5:
                        displayKategoriList(toko); 
                        System.out.print("Masukkan ID Kategori yang akan diedit: ");
                        int idK_edit = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Masukkan Nama Kategori Baru: ");
                        String namaK_new = scanner.nextLine();
                        
                        Kategori k_new = new Kategori(idK_edit, namaK_new);
                        toko.updateKategori(k_new);
                        System.out.println("\nKategori berhasil diperbarui!");
                        tungguKonfirmasi(scanner);
                        break;

                    case 6:
                        displayKategoriList(toko); 
                        System.out.print("Masukkan ID Kategori yang akan dihapus: ");
                        int idK_hapus = scanner.nextInt();
                        scanner.nextLine();
                        toko.deleteKategori(idK_hapus);
                        System.out.println("\nKategori berhasil dihapus!");
                        tungguKonfirmasi(scanner);
                        break;
                    
                    case 7:
                        displayProdukTable(toko); 
                        displayKategoriList(toko); 
                        
                        System.out.print("\nMasukkan ID Produk yang akan diatur: ");
                        int idP_relasi = scanner.nextInt();
                        scanner.nextLine(); 

                        System.out.print("Masukkan ID Kategori BARU (pisahkan koma, cth: 1,3. Kosongkan untuk menghapus relasi): ");
                        String kategoriIdsStr = scanner.nextLine();
                        
                        List<Integer> idKategoriList = new ArrayList<>();
                        if (!kategoriIdsStr.trim().isEmpty()) {
                            String[] ids = kategoriIdsStr.split(",");
                            for (String idStr : ids) {
                                idKategoriList.add(Integer.parseInt(idStr.trim()));
                            }
                        }
                        toko.setRelasiProdukKategori(idP_relasi, idKategoriList);
                        System.out.println("\nRelasi kategori untuk Produk ID " + idP_relasi + " berhasil diperbarui!");
                        tungguKonfirmasi(scanner);
                        break;
                    
                    case 8:
                        displayProdukTable(toko);
                        displayKategoriList(toko);
                        tungguKonfirmasi(scanner);
                        break;
                    
                    case 9:
                        System.out.println("Program dihentikan.");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Pilihan tidak valid.");
                        tungguKonfirmasi(scanner);
                }
            
            } catch (SQLException e) {
                System.err.println("\n!!! TERJADI KESALAHAN DATABASE !!!");
                System.err.println("Error SQL: " + e.getMessage());
                tungguKonfirmasi(scanner); 
            } catch (Exception e) {
                System.err.println("\n!!! INPUT SALAH atau DATA TIDAK DITEMUKAN !!!");
                System.err.println("Error: " + e.getMessage());
                tungguKonfirmasi(scanner); 
            }
        }
    }
}
