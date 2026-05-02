/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

/**
 *
 * @author bests
 */

import Model.Produk;
import Model.Kategori;
import DAO.ProdukDAO;
import DAO.KategoriDAO;
import java.util.List;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;

public class DialogEditProduk extends javax.swing.JDialog {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DialogEditProduk.class.getName());

    /**
     * Creates new form DialogEditProduk
     */
    
    private int idToko;
    private Produk produk; // Objek produk yang sedang diedit
    private ProdukDAO pDao = new ProdukDAO();
    private KategoriDAO kDao = new KategoriDAO();
    
    private final String PLACEHOLDER_NAMA = "Masukkan nama produk";
    private final String PLACEHOLDER_HARGA = "Masukkan harga produk";
    
    public DialogEditProduk(java.awt.Frame parent, boolean modal, int idToko, Produk p) {
        super(parent, modal);
        this.idToko = idToko;
        this.produk = p;
        initComponents();
        loadKategori();   // Isi ComboBox
        
        setupToggleSelection(); // Fitur klik toggle JList
        setupPlaceholders();
        isiDataField();
        this.setLocationRelativeTo(parent);
    }

       // --- FITUR 1: TOGGLE SELECTION UNTUK JLIST ---
    private void setupToggleSelection() {
        // Gunakan DefaultListSelectionModel agar JList berperilaku normal
        ListKategori.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                // Logika Toggle: Jika sudah terpilih maka lepas, jika belum maka pilih
                if (index0 == index1) {
                    if (isSelectedIndex(index0)) {
                        removeSelectionInterval(index0, index0);
                    } else {
                        addSelectionInterval(index0, index0);
                    }
                } else {
                    super.setSelectionInterval(index0, index1);
                }
            }
        });

        // Pastikan Selection Mode adalah MULTIPLE_INTERVAL
        ListKategori.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    // --- FITUR 2: TOGGLE PLACEHOLDER ---
    private void setupPlaceholders() {
        txtNama.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtNama.getText().equals(PLACEHOLDER_NAMA)) {
                    txtNama.setText("");
                    txtNama.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtNama.getText().isEmpty()) {
                    txtNama.setText(PLACEHOLDER_NAMA);
                    txtNama.setForeground(Color.GRAY);
                }
            }
        });

        txtHarga.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtHarga.getText().equals(PLACEHOLDER_HARGA)) {
                    txtHarga.setText("");
                    txtHarga.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (txtHarga.getText().isEmpty()) {
                    txtHarga.setText(PLACEHOLDER_HARGA);
                    txtHarga.setForeground(Color.GRAY);
                }
            }
        });
    }
    
    private void loadKategori() {
        javax.swing.DefaultListModel<String> model = new javax.swing.DefaultListModel<>();
        try {
            List<Model.Kategori> semuaKategori = kDao.getAllKategori(this.idToko);
            List<Model.Kategori> katTerpilih = produk.getDaftarKategori();

            List<String> terpilih = new java.util.ArrayList<>();
            List<String> sisa = new java.util.ArrayList<>();

            for (Model.Kategori k : semuaKategori) {
                boolean isMatch = false;
                for (Model.Kategori kp : katTerpilih) {
                    if (kp.getIdKategori() == k.getIdKategori()) {
                        isMatch = true;
                        break;
                    }
                }
                if (isMatch) terpilih.add(k.getNamaKategori());
                else sisa.add(k.getNamaKategori());
            }

            for (String nama : terpilih) model.addElement(nama);
            for (String nama : sisa) model.addElement(nama);
            ListKategori.setModel(model);
        } catch (Exception e) {}
    }
    
    private void isiDataField() {
        if (produk == null) return;

        // Isi data teks
        txtId.setText(String.valueOf(produk.getIdProduk()));
        txtNama.setText(produk.getNamaProduk());
        txtNama.setForeground(Color.BLACK);
        txtHarga.setText(String.valueOf(produk.getHarga()));
        txtHarga.setForeground(Color.BLACK);

        // Proses Highlight (Biru)
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.ListModel<String> model = ListKategori.getModel();
            java.util.List<Integer> indices = new java.util.ArrayList<>();

            for (int i = 0; i < model.getSize(); i++) {
                String namaDiList = model.getElementAt(i).trim();
                for (Model.Kategori k : produk.getDaftarKategori()) {
                    if (k.getNamaKategori().trim().equalsIgnoreCase(namaDiList)) {
                        indices.add(i);
                    }
                }
            }

            if (!indices.isEmpty()) {
                int[] target = indices.stream().mapToInt(Integer::intValue).toArray();
                ListKategori.setSelectedIndices(target);
            }
        });
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblHeader = new javax.swing.JLabel();
        btnExit = new javax.swing.JButton();
        lblNama = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        lblKategori = new javax.swing.JLabel();
        lblHarga = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        btnBatalTambah = new javax.swing.JButton();
        btnEditProduk = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListKategori = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblHeader.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        lblHeader.setText("Edit Produk");

        btnExit.setFont(new java.awt.Font("Segoe UI Variable", 0, 18)); // NOI18N
        btnExit.setText("X");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblNama.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lblNama.setText("Nama Produk");

        txtNama.setText("Nama produk");

        lblKategori.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lblKategori.setText("Kategori (pilih 1 atau lebih)");

        lblHarga.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lblHarga.setText("Harga (Rp.)");

        txtHarga.setText("Harga produk");
        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });
        txtHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHargaKeyTyped(evt);
            }
        });

        btnBatalTambah.setText("BATAL");
        btnBatalTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalTambahActionPerformed(evt);
            }
        });

        btnEditProduk.setText("EDIT PRODUK");
        btnEditProduk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditProdukActionPerformed(evt);
            }
        });

        lblId.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        lblId.setText("ID Produk");

        txtId.setEditable(false);
        txtId.setText("(ID Produk)");
        txtId.setFocusable(false);
        txtId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdActionPerformed(evt);
            }
        });

        ListKategori.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(ListKategori);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBatalTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(btnEditProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblId)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblKategori)
                            .addComponent(lblHarga)
                            .addComponent(txtHarga)
                            .addComponent(lblNama)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(lblHeader)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExit)
                    .addGap(19, 19, 19)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(55, Short.MAX_VALUE)
                .addComponent(lblId)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNama)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblKategori)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(lblHarga)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditProduk, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBatalTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(17, 17, 17)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnExit)
                        .addComponent(lblHeader))
                    .addContainerGap(441, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnExitActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        btnEditProdukActionPerformed(evt);
    }//GEN-LAST:event_txtHargaActionPerformed

    private void txtHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHargaKeyTyped
        char c = evt.getKeyChar();
        // Hanya izinkan angka, titik (untuk double), dan tombol backspace
        if (!(Character.isDigit(c) || c == java.awt.event.KeyEvent.VK_BACK_SPACE || c == '.')) {
            evt.consume(); // Abaikan input jika bukan angka/titik
        }

        // Cegah titik ganda untuk tipe data double
        if (c == '.' && txtHarga.getText().contains(".")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtHargaKeyTyped

    private void btnBatalTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalTambahActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBatalTambahActionPerformed

    private void btnEditProdukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditProdukActionPerformed
        try {
        int id = Integer.parseInt(txtId.getText());
        String nama = txtNama.getText().trim();
        double harga = Double.parseDouble(txtHarga.getText().trim());
        List<String> kategoriTerpilih = ListKategori.getSelectedValuesList();

        this.produk.setNamaProduk(nama);
        this.produk.setHarga(harga);
        this.produk.getDaftarKategori().clear(); // Reset list kategori di objek

        for (String namaKat : kategoriTerpilih) {
            int idKat = kDao.getIdByName(namaKat, this.idToko);
            this.produk.getDaftarKategori().add(new Kategori(idKat, namaKat, this.idToko));
        }

        // Panggil DAO Update (Pastikan pDao.updateProduk menghandle perubahan relasi di database)
        pDao.updateProduk(id, nama, harga, this.produk.getDaftarKategori());

        JOptionPane.showMessageDialog(this, "Data Produk Berhasil Diperbarui!");
        this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Update: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEditProdukActionPerformed

    private void txtIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                DialogEditProduk dialog = new DialogEditProduk(new javax.swing.JFrame(), true, 0, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListKategori;
    private javax.swing.JButton btnBatalTambah;
    private javax.swing.JButton btnEditProduk;
    private javax.swing.JButton btnExit;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHarga;
    private javax.swing.JLabel lblHeader;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblKategori;
    private javax.swing.JLabel lblNama;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
