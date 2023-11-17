/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view;

import Utilities.DBConnection;
import entity.ChatLieu;
import entity.SanPham;
import entity.DongSP;
import entity.KichThuoc;
import entity.MauSac;
import entity.NhaSanXuat;
import entity.SanPham;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.RootPaneUI;
import javax.swing.table.DefaultTableModel;
import repository.ChiTietSanPhamRepository;
import service.ChiTietSanPhamSer;
import service.SanPhamSer;
import service.serviceImpl.ChatLieuImpl;
import service.serviceImpl.ChiTietSanPhamSerImpl;
import service.serviceImpl.DongSPImpl;
import service.serviceImpl.MauSacImpl;
import service.serviceImpl.NSXImpl;
import service.serviceImpl.SanPhamSerImpl;
import service.serviceImpl.SizeImpl;

/**
 *
 * @author thong
 */
public class ChiTietSanPhamView extends javax.swing.JPanel {

    private DefaultComboBoxModel<ChatLieu> dtCL = new DefaultComboBoxModel();
    private DefaultComboBoxModel<MauSac> dtMS;
    private DefaultComboBoxModel<NhaSanXuat> dtNS = new DefaultComboBoxModel();
    private DefaultComboBoxModel<KichThuoc> dtKT = new DefaultComboBoxModel();
    private DefaultComboBoxModel<DongSP> dtDSP = new DefaultComboBoxModel();
    private DefaultComboBoxModel<SanPham> dtSP = new DefaultComboBoxModel();

    private DefaultTableModel dtm = new DefaultTableModel();
    private List<SanPham> listSanPhamCT = new ArrayList<>();
    private ChiTietSanPhamRepository SanPhamService = new ChiTietSanPhamRepository();
    private ChiTietSanPhamSer ctspsi = new ChiTietSanPhamSerImpl();
    private SanPhamSer spser = new SanPhamSerImpl();

    private MauSacImpl msImpl = new MauSacImpl();
    private ChatLieuImpl clImpl = new ChatLieuImpl();
    private SizeImpl sizeImpl = new SizeImpl();
    private NSXImpl nsxImpl = new NSXImpl();
    private DongSPImpl dspImpl = new DongSPImpl();

    List<SanPham> listSP = ctspsi.getSP();
    List<ChatLieu> ListCL = ctspsi.getAllCL();
    List<MauSac> ListMS = ctspsi.getAllMs();
    List<KichThuoc> ListKT = ctspsi.getAllKT();
    List<NhaSanXuat> ListNSX = ctspsi.getAllNSX();
    List<DongSP> ListDSP = ctspsi.getAllDSP();

    public ChiTietSanPhamView() {
        initComponents();
        tbCTSP.setModel(dtm);
        listSanPhamCT = ctspsi.getAll();
        String header[] = {"Ma CTSP", "Sản Phẩm", "NSX", "Màu Sắc", "Chất Liệu ", "Kích Thước", "Dòng Sản Phẩm", "Mô Tả", "Số Lượng Tồn", "Giá Nhập", "Giá Bán"};
        dtm.setColumnIdentifiers(header);
        dtCL = new DefaultComboBoxModel(ListCL.toArray());
        cbbChatLieu.setModel(dtCL);
        dtDSP = new DefaultComboBoxModel(ListDSP.toArray());
        cbbLoai.setModel(dtDSP);
        dtMS = new DefaultComboBoxModel(ListMS.toArray());
        cbbMau.setModel(dtMS);
        dtKT = new DefaultComboBoxModel(ListKT.toArray());
        cbbSzie.setModel(dtKT);
        dtNS = new DefaultComboBoxModel(ListNSX.toArray());
        cbbHang.setModel(dtNS);
        dtSP = new DefaultComboBoxModel(listSP.toArray());
        CBBsp.setModel(dtSP);
        loadData();
        fill(1);
        fillsp("");
    }

    public SanPham ShowSp(String sp) {
        for (int i = 0; i < listSP.size(); i++) {
            SanPham sps = (SanPham) CBBsp.getItemAt(i);
            if (sp.equalsIgnoreCase(sps.getTenSP())) {
                return sps;
            }
        }
        return null;
    }

    public ChatLieu ShowCL(String cl) {
        for (int i = 0; i < ListCL.size(); i++) {
            ChatLieu cls = (ChatLieu) cbbChatLieu.getItemAt(i);
            if (cl.equalsIgnoreCase(cls.getTenCL())) {
                return cls;
            }
        }
        return null;
    }

    public MauSac ShowMauSac(String m) {
        for (int i = 0; i < ListMS.size(); i++) {
            MauSac ms = (MauSac) cbbMau.getItemAt(i);
            if (m.equalsIgnoreCase(ms.getTenMS())) {
                return ms;
            }
        }
        return null;
    }

    public NhaSanXuat ShowNSX(String nsx) {
        for (int i = 0; i < ListNSX.size(); i++) {
            NhaSanXuat Nsx = (NhaSanXuat) cbbHang.getItemAt(i);
            if (nsx.equalsIgnoreCase(Nsx.getTenNhaSanXuat())) {
                return Nsx;
            }
        }
        return null;
    }

    public DongSP ShowDSP(String dsp) {
        for (int i = 0; i < ListDSP.size(); i++) {
            DongSP Dsp = (DongSP) cbbLoai.getItemAt(i);
            if (dsp.equalsIgnoreCase(Dsp.getTenDongSP())) {
                return Dsp;
            }
        }
        return null;
    }

    public KichThuoc ShowSize(String size) {
        for (int i = 0; i < ListKT.size(); i++) {
            KichThuoc kt = (KichThuoc) cbbSzie.getItemAt(i);
            if (size.equalsIgnoreCase(kt.getTenKT())) {
                return kt;
            }
        }
        return null;
    }

    private void loadData() {
        List<SanPham> ctsps = SanPhamService.getAll();
        dtm = (DefaultTableModel) tbCTSP.getModel();
        dtm.setRowCount(0);
        for (SanPham x : ctsps) {
            Object row[] = new Object[]{
                x.getMaCTSP(), x.getSanPham().getTenSP(), x.getNsx().getTenNhaSanXuat(), x.getMauSac().getTenMS(), x.getChatLieu().getTenCL(), x.getKichThuoc().getTenKT(), x.getDongSP().getTenDongSP(), x.getMoTa(), x.getSoLuongTon(), x.getGiaNhap(), x.getGiaBan()
            };
            dtm.addRow(row);
        }
    }

    public SanPham fillsp(String sp) {
        for (int i = 0; i < listSP.size(); i++) {
            SanPham spp = (SanPham) CBBsp.getItemAt(i);
            if (sp.equalsIgnoreCase(spp.getTenSP())) {
                return spp;
            }
        }
        return null;
    }

    public ChatLieu fillcl(String cl) {
        for (int i = 0; i < ListCL.size(); i++) {
            ChatLieu clsp = (ChatLieu) cbbChatLieu.getItemAt(i);
            if (cl.equalsIgnoreCase(clsp.getTenCL())) {
                return clsp;
            }
        }
        return null;
    }

    public MauSac fillms(String ms) {
        for (int i = 0; i < ListMS.size(); i++) {
            MauSac mssp = (MauSac) cbbMau.getItemAt(i);
            if (ms.equalsIgnoreCase(mssp.getTenMS())) {
                return mssp;
            }
        }
        return null;
    }

    public DongSP filldsp(String dsp) {
        for (int i = 0; i < ListDSP.size(); i++) {
            DongSP dspp = (DongSP) cbbLoai.getItemAt(i);
            if (dsp.equalsIgnoreCase(dspp.getTenDongSP())) {
                return dspp;
            }
        }
        return null;
    }

    public NhaSanXuat fillnsx(String nsx) {
        for (int i = 0; i < ListNSX.size(); i++) {
            NhaSanXuat nsxs = (NhaSanXuat) cbbHang.getItemAt(i);
            if (nsx.equalsIgnoreCase(nsxs.getTenNhaSanXuat())) {
                return nsxs;
            }
        }
        return null;
    }

    public KichThuoc fillsize(String size) {
        for (int i = 0; i < ListKT.size(); i++) {
            KichThuoc ktsp = (KichThuoc) cbbSzie.getItemAt(i);
            if (size.equalsIgnoreCase(ktsp.getTenKT())) {
                return ktsp;
            }
        }
        return null;
    }

    private void fill(int index) {
        SanPham ctsp = listSanPhamCT.get(index);
        txtMCTSP.setText(ctsp.getMaCTSP());
        txtMota.setText(ctsp.getMoTa());
        txtSoLuong.setText(String.valueOf(ctsp.getSoLuongTon()));
        txtBan.setText(String.valueOf(ctsp.getGiaBan()));
        txtNhap.setText(String.valueOf(ctsp.getGiaNhap()));

        cbbLoai.setSelectedItem(filldsp(ctsp.getDongSP().getTenDongSP()));

        cbbChatLieu.setSelectedItem(fillcl(ctsp.getChatLieu().getTenCL()));

        cbbHang.setSelectedItem(fillnsx(ctsp.getNsx().getTenNhaSanXuat()));

        cbbMau.setSelectedItem(fillms(ctsp.getMauSac().getTenMS()));

        cbbSzie.setSelectedItem(fillsize(ctsp.getKichThuoc().getTenKT()));

        CBBsp.setSelectedItem(fillsp(ctsp.getSanPham().getTenSP()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        cbbLoaiSearch = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtSanPhamSearch = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cbbHangSearch = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtBan = new javax.swing.JTextField();
        txtNhap = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        cbbLoai = new javax.swing.JComboBox();
        cbbMau = new javax.swing.JComboBox();
        cbbHang = new javax.swing.JComboBox();
        cbbSzie = new javax.swing.JComboBox();
        cbbChatLieu = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMota = new javax.swing.JTextArea();
        lbAnh = new javax.swing.JLabel();
        btnAnh = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        btnView = new javax.swing.JButton();
        txtMCTSP = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        CBBsp = new javax.swing.JComboBox();
        txtMaSanPham = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbCTSP = new javax.swing.JTable();

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel16.setText("Loại:");

        cbbLoaiSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbbLoaiSearch, 0, 140, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(cbbLoaiSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel17.setText("Sản phẩm:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSanPhamSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtSanPhamSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel18.setText("Hãng:");

        cbbHangSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(cbbHangSearch, 0, 162, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cbbHangSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(90, 90, 90)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(350, 350, 350))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setPreferredSize(new java.awt.Dimension(1235, 706));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Sản phẩm: ");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Loại: ");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Hãng:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Màu:");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Size:");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Chất Liệu:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Giá nhập:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Giá bán:");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Mô tả:");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Số lượng:");

        cbbLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbLoaiActionPerformed(evt);
            }
        });

        cbbMau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbMauActionPerformed(evt);
            }
        });

        cbbHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbHangActionPerformed(evt);
            }
        });

        cbbSzie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbSzieActionPerformed(evt);
            }
        });

        cbbChatLieu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbChatLieuActionPerformed(evt);
            }
        });

        txtMota.setColumns(20);
        txtMota.setRows(5);
        jScrollPane1.setViewportView(txtMota);

        lbAnh.setBackground(new java.awt.Color(255, 255, 255));
        lbAnh.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAnh.setText("Thêm ảnh");
        btnAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnhActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        btnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iImage/Delete.png"))); // NOI18N
        btnDelete.setText("Xóa");
        btnDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iImage/New.png"))); // NOI18N
        btnAdd.setText("  Thêm");
        btnAdd.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iImage/Change.png"))); // NOI18N
        btnUpdate.setText("  Sửa");
        btnUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnRefresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iImage/Refresh-icon.png"))); // NOI18N
        btnRefresh.setText("Refresh");
        btnRefresh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iImage/Print Sale.png"))); // NOI18N
        jButton6.setText("Xuất file");
        jButton6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRefresh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnView.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iImage/kisspng-computer-icons-plus-sign-clip-art-plus-sign-5b4bfbdff0b3a7.2950963015317063359859.jpg"))); // NOI18N
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        jLabel1.setText("Ma CTSP");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel8)))
                        .addGap(64, 64, 64)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbbSzie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbChatLieu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbbMau, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addComponent(cbbHang, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaSanPham)
                            .addComponent(txtMCTSP)
                            .addComponent(cbbLoai, 0, 194, Short.MAX_VALUE)
                            .addComponent(CBBsp, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(35, 35, 35)
                .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(503, 503, 503)
                        .addComponent(btnAnh)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBan, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(144, 144, 144)
                                .addComponent(lbAnh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CBBsp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel9)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMaSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtMCTSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(8, 8, 8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(txtNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(txtBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cbbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAnh)
                        .addGap(6, 6, 6)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(cbbHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(cbbMau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(36, 36, 36)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(cbbSzie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cbbChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(35, 35, 35))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Lọc sản phẩm");

        tbCTSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbCTSP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbCTSPMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbCTSP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnhActionPerformed
        // TODO add your handling code here:
        //         private String maCTSP;
//    private SanPham sanPham;
//    private NhaSanXuat nsx;
//    private MauSac mauSac;
//    private DongSP dongSP;
//    private ChatLieu chatLieu;
//    private KichThuoc kichThuoc;
//    private String moTa;
//    private Integer soLuongTon;
//    private Double giaNhap;
//    private Double giaBan;
//    private String anhSp;

    }//GEN-LAST:event_btnAnhActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:

        SanPham ctsp = new SanPham();
        List<ChatLieu> ListCL = clImpl.getAll();
        List<NhaSanXuat> ListNSX2 = nsxImpl.getAll();
        List<MauSac> ListMS2 = msImpl.getAll();
        List<KichThuoc> ListKT2 = sizeImpl.getAll();
        List<DongSP> ListDSP2 = dspImpl.getAll();
        List<SanPham> ListSP = spser.getAll();

        ctsp.setGiaBan(Double.valueOf(txtBan.getText()));
        ctsp.setGiaNhap(Double.valueOf(txtNhap.getText()));
        ctsp.setSoLuongTon(Integer.valueOf(txtSoLuong.getText()));
        ctsp.setMoTa(txtMota.getText());
//        ctsp.setMaCTSP(txtMCTSP.getText());
//        listSanPhamCT.set(txtMCTSP.getText());
        //ctsp.setSanPham(txtSP.getText());
//        SanPham sp = new SanPham(txtSP.getText());
//        ctsp.setSanPham(sp);
//     String sanPham = (String) jComboBox1.getSelectedItem();

        for (int i = 0; i < ListSP.size(); i++) {
            SanPham sp = (SanPham) CBBsp.getItemAt(i);
            if (CBBsp.getSelectedItem().toString().equalsIgnoreCase(sp.getTenSP())) {
                ctsp.setSanPham(sp);
            }
        }
        for (int i = 0; i < ListCL.size(); i++) {
            ChatLieu cl = (ChatLieu) cbbChatLieu.getItemAt(i);
            if (cbbChatLieu.getSelectedItem().toString().equalsIgnoreCase(cl.getTenCL())) {
                ctsp.setChatLieu(cl);
            }
        }

//         for (int i = 0; i < ListSP.size(); i++) {
//            SanPham sp = (SanPham) cboSanPham.getItemAt(i);
//            if (cboSanPham.getSelectedItem().toString().equalsIgnoreCase(sp.getTenSP())) {
//                ctsp.setSanPham(sp);
//            }
//        }
        for (int i = 0; i < ListMS2.size(); i++) {
            MauSac ms = (MauSac) cbbMau.getItemAt(i);
            if (cbbMau.getSelectedItem().toString().equalsIgnoreCase(ms.getTenMS())) {
                ctsp.setMauSac(ms);
            }
        }
        for (int i = 0; i < ListNSX2.size(); i++) {
            NhaSanXuat nsx = (NhaSanXuat) cbbHang.getItemAt(i);
            if (cbbHang.getSelectedItem().toString().equalsIgnoreCase(nsx.getTenNhaSanXuat())) {
                ctsp.setNsx(nsx);
            }
        }
        for (int i = 0; i < ListDSP2.size(); i++) {
            DongSP dsp = (DongSP) cbbLoai.getItemAt(i);
            if (cbbLoai.getSelectedItem().toString().equalsIgnoreCase(dsp.getTenDongSP())) {
                ctsp.setDongSP(dsp);
            }
        }
        for (int i = 0; i < ListKT2.size(); i++) {
            KichThuoc kt = (KichThuoc) cbbSzie.getItemAt(i);
            if (cbbSzie.getSelectedItem().toString().equalsIgnoreCase(kt.getTenKT())) {
                ctsp.setKichThuoc(kt);
            }
        }
        SanPhamService.add(ctsp);
        listSanPhamCT = SanPhamService.getAll();
//        loadData(listSanPhamCT);
        loadData();
        JOptionPane.showMessageDialog(this, "Thành Công");
        checkData();
       checkNumber("");
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        checkData();
        checkNumber("");
        int row = tbCTSP.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng");
        } else {
//            ChiTietSanPham ctsp = listSanPhamCT.get(row);

            SanPham ctsp = new SanPham();
            List<ChatLieu> ListCL = clImpl.getAll();
            List<NhaSanXuat> ListNSX2 = nsxImpl.getAll();
            List<MauSac> ListMS2 = msImpl.getAll();
            List<KichThuoc> ListKT2 = sizeImpl.getAll();
            List<DongSP> ListDSP2 = dspImpl.getAll();
            List<SanPham> ListSP = spser.getAll();

            ctsp.setGiaBan(Double.valueOf(txtBan.getText()));
            ctsp.setGiaNhap(Double.valueOf(txtNhap.getText()));
            ctsp.setSoLuongTon(Integer.valueOf(txtSoLuong.getText()));
            ctsp.setMoTa(txtMota.getText());
//        ctsp.setMaCTSP(txtMCTSP.getText());
//        listSanPhamCT.set(txtMCTSP.getText());
            //ctsp.setSanPham(txtSP.getText());
//        SanPham sp = new SanPham(txtSP.getText());
//        ctsp.setSanPham(sp);
//     String sanPham = (String) jComboBox1.getSelectedItem();

            for (int i = 0; i < ListSP.size(); i++) {
                SanPham sp = (SanPham) CBBsp.getItemAt(i);
                if (CBBsp.getSelectedItem().toString().equalsIgnoreCase(sp.getTenSP())) {
                    ctsp.setSanPham(sp);
                }
            }
            for (int i = 0; i < ListCL.size(); i++) {
                ChatLieu cl = (ChatLieu) cbbChatLieu.getItemAt(i);
                if (cbbChatLieu.getSelectedItem().toString().equalsIgnoreCase(cl.getTenCL())) {
                    ctsp.setChatLieu(cl);
                }
            }

//         for (int i = 0; i < ListSP.size(); i++) {
//            SanPham sp = (SanPham) cboSanPham.getItemAt(i);
//            if (cboSanPham.getSelectedItem().toString().equalsIgnoreCase(sp.getTenSP())) {
//                ctsp.setSanPham(sp);
//            }
//        }
            for (int i = 0; i < ListMS2.size(); i++) {
                MauSac ms = (MauSac) cbbMau.getItemAt(i);
                if (cbbMau.getSelectedItem().toString().equalsIgnoreCase(ms.getTenMS())) {
                    ctsp.setMauSac(ms);
                }
            }
            for (int i = 0; i < ListNSX2.size(); i++) {
                NhaSanXuat nsx = (NhaSanXuat) cbbHang.getItemAt(i);
                if (cbbHang.getSelectedItem().toString().equalsIgnoreCase(nsx.getTenNhaSanXuat())) {
                    ctsp.setNsx(nsx);
                }
            }
            for (int i = 0; i < ListDSP2.size(); i++) {
                DongSP dsp = (DongSP) cbbLoai.getItemAt(i);
                if (cbbLoai.getSelectedItem().toString().equalsIgnoreCase(dsp.getTenDongSP())) {
                    ctsp.setDongSP(dsp);
                }
            }
            for (int i = 0; i < ListKT2.size(); i++) {
                KichThuoc kt = (KichThuoc) cbbSzie.getItemAt(i);
                if (cbbSzie.getSelectedItem().toString().equalsIgnoreCase(kt.getTenKT())) {
                    ctsp.setKichThuoc(kt);
                }
            }
            JOptionPane.showMessageDialog(this, SanPhamService.update(ctsp, txtMCTSP.getText()));
            listSanPhamCT = SanPhamService.getAll();
//            loadData(listSanPhamCT);
            loadData();
        }


    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        // TODO add your handling code here:
        SizeView sv = new SizeView();
        sv.show();
        // sv.setVisible(true);

    }//GEN-LAST:event_btnViewActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int row = tbCTSP.getSelectedRow();
        SanPham sp = listSanPhamCT.get(row);
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng");
        } else {
            String maCTSP = sp.getMaCTSP();
            JOptionPane.showMessageDialog(this, SanPhamService.delete(maCTSP));
            listSanPhamCT = SanPhamService.getAll();
//            loadData(listSanPhamCT);
            loadData();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void cbbLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbLoaiActionPerformed

    private void tbCTSPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbCTSPMouseClicked
        // TODO add your handling code here:
        int row = tbCTSP.getSelectedRow();
        fill(row);
        //fillsp(TOOL_TIP_TEXT_KEY);
    }//GEN-LAST:event_tbCTSPMouseClicked

    private void cbbHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbHangActionPerformed
        // TODO add your handling code here:
        //cbbHang.removeAllItems();
        //loadCbb();
    }//GEN-LAST:event_cbbHangActionPerformed

    private void cbbMauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbMauActionPerformed
        // TODO add your handling code here:
        // cbbMau.removeAllItems();
        //loadCbb();
    }//GEN-LAST:event_cbbMauActionPerformed

    private void cbbSzieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbSzieActionPerformed
        // TODO add your handling code here:
        // cbbSzie.removeAllItems();
        // loadCbb();
    }//GEN-LAST:event_cbbSzieActionPerformed

    private void cbbChatLieuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbChatLieuActionPerformed
        // TODO add your handling code here:
        //  cbbChatLieu.removeAllItems();
        // loadCbb();
    }//GEN-LAST:event_cbbChatLieuActionPerformed
//    private void loadCbb() {
//        try {
//            Connection conn = DBConnection.getConnection();
//            String sql = "SELECT * FROM MauSac";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                cbbMau.addItem(rs.getString("Ten"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Connection conn = DBConnection.getConnection();
//            String sql = "SELECT * FROM KichThuoc";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                cbbSzie.addItem(rs.getString("Ten"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Connection conn = DBConnection.getConnection();
//            String sql = "SELECT * FROM DongSP";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                cbbLoai.addItem(rs.getString("Ten"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Connection conn = DBConnection.getConnection();
//            String sql = "SELECT * FROM ChatLieu";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                cbbChatLieu.addItem(rs.getString("Ten"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        try {
//            Connection conn = DBConnection.getConnection();
//            String sql = "SELECT * FROM NSX";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                cbbHang.addItem(rs.getString("Ten"));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public boolean checkNumber(String number) {
        String kytu = "\\d+";
        if (number.matches(kytu)) {
            if (Integer.valueOf(number) < 0) {
                JOptionPane.showMessageDialog(this, "Dự liệu không hợp lệ");
                return false;
            } else {
                return true;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
            return false;
        }
    }

    public boolean checkData() {
        if (txtSoLuong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số Lượng Tồn không được để trống!");
            return false;
        } else if (txtNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá Nhập không được để trống!");
            return false;
        } else if (txtBan.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giá Bán không được để trống!");
            return false;
        } else if (!checkNumber(txtSoLuong.getText().trim())) {
            return false;
        } else if (!checkNumber(txtNhap.getText().trim())) {
            return false;
        } else if (!checkNumber(txtBan.getText().trim())) {
            return false;
        } else {
            return true;
        }
    }

    public void clear() {
        this.txtBan.setText("");
        this.txtNhap.setText("");
        this.txtSoLuong.setText("");

        this.lbAnh.setIcon(null);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CBBsp;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAnh;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnView;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox cbbChatLieu;
    private javax.swing.JComboBox cbbHang;
    private javax.swing.JComboBox<String> cbbHangSearch;
    private javax.swing.JComboBox cbbLoai;
    private javax.swing.JComboBox<String> cbbLoaiSearch;
    private javax.swing.JComboBox cbbMau;
    private javax.swing.JComboBox cbbSzie;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbAnh;
    private javax.swing.JTable tbCTSP;
    private javax.swing.JTextField txtBan;
    private javax.swing.JTextField txtMCTSP;
    private javax.swing.JTextField txtMaSanPham;
    private javax.swing.JTextArea txtMota;
    private javax.swing.JTextField txtNhap;
    private javax.swing.JTextField txtSanPhamSearch;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
