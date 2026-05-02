/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package View;

/**
 *
 * @author bests
 */
import javax.swing.UIManager;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // Mengatur tampilan agar mengikuti desain sistem operasi (Windows/Mac/Linux)
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Gagal mengatur Look and Feel: " + e.getMessage());
        }

        // Menjalankan Frame Utama (FrameToko)
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Pastikan nama class Frame daftar toko Anda adalah FrameToko
                new FrameToko().setVisible(true);
            }
        });
    }
    
}
