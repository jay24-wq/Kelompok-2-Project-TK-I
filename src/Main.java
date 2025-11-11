import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static Toko tokoDao = new Toko();
    private static int idTokoAktif = 0; 

    private static void tungguKonfirmasi(Scanner scanner) {
        System.out.println("\n-------------------------------------------");
        System.out.print("Operasi selesai. Tekan [ENTER] untuk kembali ke menu...");
        try {
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
        } catch (Exception e) {}
    }
    
    private static void displayDaftarToko(List<Toko> daftarToko) {
        System.out.println("\n--- DAFTAR TOKO TERSEDIA ---");
        if (daftarToko.isEmpty()) {
            System.out.println("Belum ada data Toko.");
            return;
        }
        System.out.printf("%-5s | %-25s | %-20s | %s\n", "ID", "Nama Toko", "Pemilik", "Alamat");
        System.out.println("---------------------------------------------------------------------------------");
        for (Toko t : daftarToko) {
            System.out.printf("%-5d | %-25s | %-20s | %s\n", t.getIdToko(), t.getNamaToko(), t.getPemilikToko(), t.getAlamatToko());
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    private static void displayProdukTable(List<Produk> produkLengkap) {
        System.out.println("\n--- DATA PRODUK LENGKAP TOKO ID " + idTokoAktif + " ---");
        System.out.printf("%-5s | %-25s | %-15s | %s\n", "ID", "Nama Produk", "Harga", "Kategori");
        System.out.println("--------------------------------------------------------------------------");

        if (produkLengkap.isEmpty()) {
            System.out.println("Belum ada data produk.");
        } else {
            for (Produk p : produkLengkap) {
                String listKategori = p.getDaftarKategori().stream()
                                     .map(Kategori::getNamaKategori)
                                     .collect(Collectors.joining(", "));
                
                if (listKategori.isEmpty()) listKategori = "-";

                String hargaFormatted = String.format("Rp %,.2f", p.getHarga());

                System.out.printf("%-5d | %-25s | %-15s | %s\n", 
                                   p.getIdProduk(), p.getNamaProduk(), 
                                   hargaFormatted, listKategori);
            }
        }
        System.out.println("--------------------------------------------------------------------------");
    }
    
    private static void displayKategoriList(List<Kategori> daftarK) {
        System.out.println("\n--- DAFTAR KATEGORI TOKO ID " + idTokoAktif + " ---");
        if (daftarK.isEmpty()) {
            System.out.println("Belum ada kategori.");
        } else {
            System.out.printf("%-5s | %s\n", "ID", "Nama Kategori");
            System.out.println("---------------------------");
            for (Kategori k : daftarK) {
                System.out.printf("%-5d | %s\n", k.getIdKategori(), k.getNamaKategori());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Selamat Datang di SimpleStore Management Console");
        
        while (true) { 
            
            if (idTokoAktif == 0) {
                System.out.println("\n===== PILIH TOKO =====");
                System.out.println("1. Tambah Toko Baru ");
                System.out.println("2. Edit/Update Toko ");
                System.out.println("3. Hapus Toko ");
                System.out.println("---------------------------------------");
                System.out.println("4. [LANJUT] Pilih Toko untuk dikelola");
                System.out.println("5. Keluar Program");
                System.out.print("Pilih menu (1-5): ");

                int pilihanAdmin = 0;
                try {
                    if (scanner.hasNextInt()) { pilihanAdmin = scanner.nextInt(); } else { System.out.println("\nInput harus berupa angka!"); scanner.next(); tungguKonfirmasi(scanner); continue; }
                    scanner.nextLine(); 

                    switch (pilihanAdmin) {
                        case 1: 
                            try {
                                System.out.println("\n--- TAMBAH TOKO BARU (C) ---");
                                System.out.print("Nama Toko: ");
                                String namaToko = scanner.nextLine();
                                System.out.print("Alamat Toko: ");
                                String alamatToko = scanner.nextLine();
                                System.out.print("Pemilik Toko: ");
                                String pemilikToko = scanner.nextLine();
                                
                                tokoDao.tambahToko(namaToko, alamatToko, pemilikToko); 
                                System.out.println("\nToko baru berhasil ditambahkan!");
                            } catch (SQLException e) {
                                System.err.println("!!! GAGAL MENAMBAH TOKO: " + e.getMessage());
                            }
                            tungguKonfirmasi(scanner);
                            break;
                            
                        case 2: 
                            try {
                                displayDaftarToko(tokoDao.getAllToko());
                                System.out.println("\n--- EDIT TOKO (U) ---");
                                System.out.print("Masukkan ID Toko yang akan diedit: ");
                                int idEdit = scanner.nextInt();
                                scanner.nextLine();
                                
                                Toko tokoLama = tokoDao.getTokoById(idEdit);
                                if (tokoLama == null) { System.out.println("Toko tidak ditemukan!"); tungguKonfirmasi(scanner); break; }
                                
                                System.out.println("Mengedit Toko: " + tokoLama.getNamaToko());
                                System.out.print("Nama Baru (" + tokoLama.getNamaToko() + "): ");
                                String namaBaru = scanner.nextLine();
                                System.out.print("Alamat Baru (" + tokoLama.getAlamatToko() + "): ");
                                String alamatBaru = scanner.nextLine();
                                System.out.print("Pemilik Baru (" + tokoLama.getPemilikToko() + "): ");
                                String pemilikBaru = scanner.nextLine();
                                
                                Toko tokoBaru = new Toko(idEdit, namaBaru, alamatBaru, pemilikBaru);
                                tokoDao.updateToko(tokoBaru); 
                                System.out.println("\nToko ID " + idEdit + " berhasil diperbarui!");
                            } catch (Exception e) {
                                System.err.println("!!! GAGAL MENGEDIT TOKO: " + e.getMessage());
                            }
                            tungguKonfirmasi(scanner);
                            break;
                            
                        case 3: 
                            try {
                                displayDaftarToko(tokoDao.getAllToko());
                                System.out.println("\n--- HAPUS TOKO (D) ---");
                                System.out.print("Masukkan ID Toko yang akan dihapus (PERINGATAN: Semua data terkait akan hilang): ");
                                int idHapus = scanner.nextInt();
                                scanner.nextLine();
                                
                                tokoDao.deleteToko(idHapus); 
                                System.out.println("\nToko ID " + idHapus + " berhasil dihapus beserta semua datanya!");
                            } catch (Exception e) {
                                System.err.println("!!! GAGAL MENGHAPUS TOKO: " + e.getMessage());
                            }
                            tungguKonfirmasi(scanner);
                            break;
                            
                        case 4:
                            displayDaftarToko(tokoDao.getAllToko());
                            if (tokoDao.getAllToko().isEmpty()) { System.out.println("Tidak ada toko untuk dipilih."); break; }
                            
                            System.out.print("Masukkan ID Toko yang akan dikelola: ");
                            int idBaru = scanner.nextInt();
                            scanner.nextLine();
                            
                            Toko toko = tokoDao.getTokoById(idBaru);
                            if (toko != null) {
                                idTokoAktif = idBaru; 
                                System.out.println("\nBerhasil mengelola Toko: " + toko.getNamaToko());
                            } else {
                                System.out.println("!!! ID Toko tidak valid.");
                            }
                            break;
                            
                        case 5:
                            System.out.println("Program dihentikan.");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Pilihan tidak valid.");
                            tungguKonfirmasi(scanner);
                    }
                } catch (Exception e) {
                     System.err.println("!!! ERROR LEVEL 1: " + e.getMessage());
                     tungguKonfirmasi(scanner);
                }
            } 
            
            else { 
                System.out.println("\n===== OPERASIONAL (TOKO ID: " + idTokoAktif + ") =====");
                System.out.println("1. [Produk] Tambah");
                System.out.println("2. [Produk] Edit/Update");
                System.out.println("3. [Produk] Hapus");
                System.out.println("----------------------");
                System.out.println("4. [Kategori] Tambah");
                System.out.println("5. [Kategori] Edit/Update");
                System.out.println("6. [Kategori] Hapus");
                System.out.println("----------------------");
                System.out.println("7. [RELASI] Atur Kategori Produk (M:N) "); 
                System.out.println("8. [VIEW] Tampilkan Semua Data"); 
                System.out.println("9. Keluar Manajemen Toko");
                System.out.print("Pilih menu (1-9): ");

                int pilihan = 0;
                try {
                    if (scanner.hasNextInt()) { pilihan = scanner.nextInt(); } else { scanner.next(); tungguKonfirmasi(scanner); continue; }
                    scanner.nextLine(); 

                    switch (pilihan) {
                        case 1: 
                            System.out.print("Nama Produk: ");
                            String namaP = scanner.nextLine();
                            System.out.print("Harga Produk: ");
                            double hargaP = scanner.nextDouble();
                            scanner.nextLine();
                            tokoDao.tambahProduk(new Produk(idTokoAktif, namaP, hargaP));
                            System.out.println("\nProduk berhasil ditambahkan!");
                            tungguKonfirmasi(scanner);
                            break;
                            
                        case 2: 
                            displayProdukTable(tokoDao.getAllProduk(idTokoAktif));
                            System.out.print("Masukkan ID Produk yang akan diedit: ");
                            int idEditP = scanner.nextInt();
                            scanner.nextLine();
                            Produk pLama = tokoDao.getProdukById(idEditP);
                            if (pLama == null || pLama.getIdTokoFk() != idTokoAktif) { System.out.println("Produk tidak ditemukan di toko ini!"); break; }
                            
                            System.out.println("\n--- EDIT Produk: " + pLama.getNamaProduk() + " ---");
                            System.out.print("Nama Baru (" + pLama.getNamaProduk() + "): ");
                            String namaPBaru = scanner.nextLine();
                            System.out.print("Harga Baru (" + pLama.getHarga() + "): ");
                            double hargaPBaru = scanner.nextDouble();
                            scanner.nextLine();
                            tokoDao.updateProduk(idEditP, namaPBaru, hargaPBaru);
                            System.out.println("\n Produk ID " + idEditP + " berhasil diperbarui!");
                            tungguKonfirmasi(scanner);
                            break;

                        case 3: 
                            displayProdukTable(tokoDao.getAllProduk(idTokoAktif));
                            System.out.print("Masukkan ID Produk yang akan dihapus: ");
                            int idHapusP = scanner.nextInt();
                            scanner.nextLine();
                            tokoDao.deleteProduk(idHapusP);
                            System.out.println("\n Produk ID " + idHapusP + " berhasil dihapus!");
                            tungguKonfirmasi(scanner);
                            break;
                        
                        case 4: 
                            System.out.print("Nama Kategori: ");
                            String namaK = scanner.nextLine();
                            tokoDao.tambahKategori(idTokoAktif, namaK); 
                            System.out.println("\nKategori berhasil ditambahkan!");
                            tungguKonfirmasi(scanner);
                            break;
                            
                        case 5: 
                            displayKategoriList(tokoDao.getAllKategori(idTokoAktif));
                            System.out.print("Masukkan ID Kategori yang akan diedit: ");
                            int idEditK = scanner.nextInt();
                            scanner.nextLine();
                            Kategori kLama = tokoDao.getKategoriById(idEditK);
                            if (kLama == null) { System.out.println("Kategori tidak ditemukan!"); break; }
                            
                            System.out.println("\n--- EDIT Kategori: " + kLama.getNamaKategori() + " ---");
                            System.out.print("Nama Baru (" + kLama.getNamaKategori() + "): ");
                            String namaKBaru = scanner.nextLine();
                            tokoDao.updateKategori(idEditK, namaKBaru);
                            System.out.println("\nKategori ID " + idEditK + " berhasil diperbarui!");
                            tungguKonfirmasi(scanner);
                            break;
                            
                        case 6: 
                            displayKategoriList(tokoDao.getAllKategori(idTokoAktif));
                            System.out.print("Masukkan ID Kategori yang akan dihapus: ");
                            int idHapusK = scanner.nextInt();
                            scanner.nextLine();
                            tokoDao.deleteKategori(idHapusK);
                            System.out.println("\nKategori ID " + idHapusK + " berhasil dihapus!");
                            tungguKonfirmasi(scanner);
                            break;
                            
                        case 7:
                            try {
                                displayProdukTable(tokoDao.loadProdukLengkap(idTokoAktif));
                                displayKategoriList(tokoDao.getAllKategori(idTokoAktif));

                                System.out.print("\nMasukkan ID Produk yang akan diatur: ");
                                int idP_relasi = scanner.nextInt();
                                scanner.nextLine(); 
                                
                                Produk pRelasi = tokoDao.getProdukById(idP_relasi);
                                if (pRelasi == null || pRelasi.getIdTokoFk() != idTokoAktif) {
                                    System.out.println("!!! ID Produk tidak valid atau tidak dimiliki toko ini.");
                                    tungguKonfirmasi(scanner);
                                    break;
                                }

                                System.out.print("Masukkan ID Kategori BARU (pisahkan koma, cth: 1,3. Kosongkan untuk menghapus relasi): ");
                                String kategoriIdsStr = scanner.nextLine();
                                
                                List<Integer> idKategoriList = new ArrayList<>();
                                if (!kategoriIdsStr.trim().isEmpty()) {
                                    String[] ids = kategoriIdsStr.split(",");
                                    for (String idStr : ids) {
                                        idKategoriList.add(Integer.parseInt(idStr.trim()));
                                    }
                                }
                                
                                tokoDao.setRelasiProdukKategori(idP_relasi, idKategoriList);
                                System.out.println("\nRelasi kategori untuk Produk ID " + idP_relasi + " berhasil diperbarui!");
                                
                            } catch (SQLException e) {
                                System.err.println("!!! GAGAL MEMPROSES RELASI (Pastikan semua ID Kategori valid): " + e.getMessage());
                            } catch (Exception e) {
                                System.err.println("!!! Input tidak valid atau ID Produk/Kategori salah.");
                            }
                            tungguKonfirmasi(scanner);
                            break;

                        case 8: 
                            displayProdukTable(tokoDao.loadProdukLengkap(idTokoAktif));
                            displayKategoriList(tokoDao.getAllKategori(idTokoAktif));
                            tungguKonfirmasi(scanner);
                            break;
                        
                        case 9:
                            idTokoAktif = 0; 
                            System.out.println("\nKembali ke Menu Administrasi Toko (Level 1)...");
                            break; 
                            
                        default:
                            System.out.println("Pilihan tidak valid.");
                            tungguKonfirmasi(scanner);
                    }
                
                } catch (Exception e) {
                    System.err.println("!!! ERROR LEVEL 2: " + e.getMessage());
                    tungguKonfirmasi(scanner);
                }
            }
        }
    }
}
