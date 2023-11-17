/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import Utilities.DBConnection;
import entity.ChatLieu;
import entity.SanPham;
import entity.DongSP;
import entity.KichThuoc;
import entity.MauSac;
import entity.NhaSanXuat;
import entity.SanPham;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class ChiTietSanPhamRepository {

    public List<SanPham> getAll() {
        String query = "SELECT MaSP\n"
                + "                 , Ten\n"
                + "             ,NSX.Ten\n"
                + "         ,ms.Ten\n"
                + "            ,dsp.Ten\n"
                + "                   ,cl.Ten\n"
                + "                 ,kt.Ten\n"
                + "                    ,MoTa\n"
                + "        ,SoLuongTon\n"
                + "                ,GiaNhap\n"
                + "               ,GiaBan\n"
                + "		,Anh\n"
                + "  from SanPham as ctsp join \n "
                + "join ChatLieu as cl on ctsp.MaChatLieu = cl.Ma\n"
                + "							join MauSac as ms on ctsp.MaMauSac = ms.MaMau\n"
                + "							join DongSP as dsp on ctsp.MaDongSP = dsp.MaDongSp\n"
                + "							join NSX as nsx on ctsp.MaNsx = nsx.MaNSX\n";						
        try (Connection cnn = DBConnection.getConnection(); PreparedStatement ps = cnn.prepareStatement(query)) {
            List<SanPham> listCTSP = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
               
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString(3));
                MauSac mauSac = new MauSac(rs.getString(4));
                DongSP dongSP = new DongSP(rs.getString(5));
                ChatLieu chatLieu = new ChatLieu(rs.getString(6));
                KichThuoc kichThuoc = new KichThuoc(rs.getString(7));
                listCTSP.add(new SanPham(rs.getString(1), sanPham, nhaSanXuat,
                        mauSac, dongSP, chatLieu, kichThuoc, rs.getString(8), rs.getInt(9),
                        rs.getDouble(10), rs.getDouble(11), rs.getString(12)));
            }
            return listCTSP;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public SanPham getOne(String id) {
        String query = "SELECT [MaCTSP]"
                + "      ,[MaSP]"
                + "      ,[MaNsx]"
                + "      ,[MaMauSac]"
                + "      ,[MaDongSP]"
                + "      ,[MaDChatLieu]"
                + "      ,[MaSize]"
                + "      ,[MoTa]"
                + "      ,[SoLuongTon]"
                + "      ,[GiaNhap]"
                + "      ,[GiaBan]"
                + "      ,[Anh]"
                + "  FROM [dbo].[ChiTietSP] WHERE MaCTSP = ?";
        try (Connection cnn = DBConnection.getConnection(); PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SanPham sanPham = new SanPham(rs.getString(2), rs.getString(3));
                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString(4));
                MauSac mauSac = new MauSac(rs.getString(5));
                DongSP dongSP = new DongSP(rs.getString(6));
                ChatLieu chatLieu = new ChatLieu(rs.getString(7));
                KichThuoc kichThuoc = new KichThuoc(rs.getString(8));
                return new SanPham(rs.getString(1), sanPham, nhaSanXuat,
                        mauSac, dongSP, chatLieu, kichThuoc, rs.getString(9), rs.getInt(10),
                        rs.getDouble(11), rs.getDouble(12), rs.getString(13));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public void add(SanPham ctsp) {
        // int check = 0;
        String query = "INSERT INTO [dbo].[ChiTietSP]"
                + "           ([MaCTSP]"
                + "           ,[MaSP]"
                + "           ,[MaNsx]"
                + "           ,[MaMauSac]"
                + "           ,[MaDongSP]"
                + "           ,[MaChatLieu]"
                + "           ,[MaSize]"
                + "           ,[MoTa]"
                + "           ,[SoLuongTon]"
                + "           ,[GiaNhap]"
                + "           ,[GiaBan])"
                + "     VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection cnn = DBConnection.getConnection(); PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setObject(1, "");
            ps.setObject(2, ctsp.getSanPham().getMaSP());
            ps.setObject(3, ctsp.getNsx().getNhaSanXuat());
            ps.setObject(4, ctsp.getMauSac().getMaMS());
            ps.setObject(5, ctsp.getDongSP().getMaDongSP());
            ps.setObject(6, ctsp.getChatLieu().getMaCL());
            ps.setObject(7, ctsp.getKichThuoc().getMaKT());
            ps.setObject(8, ctsp.getMoTa());
            ps.setObject(9, ctsp.getSoLuongTon());
            ps.setObject(10, ctsp.getGiaNhap());
            ps.setObject(11, ctsp.getGiaBan());
            ps.executeUpdate();
            //check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    public boolean update(SanPham ctsp, String maCTSP) {
        int check = 0;
        String query = "UPDATE [dbo].[ChiTietSP]\n"
                + "   SET [MaSP] = ?\n"
                + "      ,[MaNsx] = ?\n"
                + "      ,[MaMauSac] = ? \n"
                + "      ,[MaDongSP] = ?\n"
                + "      ,[MaChatLieu] = ?\n"
                + "      ,[MaSize] = ?\n"
                + "      ,[MoTa] = ?\n"
                + "      ,[SoLuongTon] = ? \n"
                + "      ,[GiaNhap] = ?\n"
                + "      ,[GiaBan] = ?\n"
                + " WHERE MaCTSP = ?";
        try (Connection cnn = DBConnection.getConnection(); PreparedStatement ps = cnn.prepareStatement(query)) {

            ps.setObject(1, ctsp.getSanPham().getMaSP());
            ps.setObject(2, ctsp.getNsx().getNhaSanXuat());
            ps.setObject(3, ctsp.getMauSac().getMaMS());
            ps.setObject(4, ctsp.getDongSP().getMaDongSP());
            ps.setObject(5, ctsp.getChatLieu().getMaCL());
            ps.setObject(6, ctsp.getKichThuoc().getMaKT());
            ps.setObject(7, ctsp.getMoTa());
            ps.setObject(8, ctsp.getSoLuongTon());
            ps.setObject(9, ctsp.getGiaNhap());
            ps.setObject(10, ctsp.getGiaBan());
            ps.setObject(11, maCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(String MaCTSP) {
        int check = 0;
        String query = "DELETE FROM [dbo].[ChiTietSP]"
                + "      WHERE MaCTSP = ?";
        try (Connection cnn = DBConnection.getConnection(); PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setObject(1, MaCTSP);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public SanPham getOneTen(String ten) {
//        String query = "SELECT Id,IdSP,IdNsx,IdMauSac,IdDongSP,NamBH,MoTa,SoLuongTon,GiaNhap,GiaBan FROM ChiTietSP where Id=?";
//        try ( Connection cnn = DBConnection.getConnection();  PreparedStatement ps = cnn.prepareStatement(query)) {
//            ps.setObject(1, ten);
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                SanPham sanPham = new SanPham(rs.getInt(2), rs.getString(3));
//                NhaSanXuat nhaSanXuat = new NhaSanXuat(rs.getString(4));
//                MauSac mauSac = new MauSac(rs.getString(5));
//                DongSP dongSP = new DongSP(rs.getString(6));
//                ChatLieu chatLieu = new ChatLieu(rs.getString(7));
//                KichThuoc kichThuoc = new KichThuoc(rs.getString(8));
//                return new ChiTietSanPham(rs.getString(1), sanPham, nhaSanXuat,
//                        mauSac, dongSP, chatLieu, kichThuoc, rs.getString(9), rs.getInt(10),
//                        rs.getDouble(11), rs.getDouble(12), rs.getString(13));
//            }
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
        return null;
    }
//    public static void main(String[] args) {
//        ChiTietSanPham ctsp = new ChiTietSanPham(70);
//        new RepositoryChiTietSanPham().updateSoLuong(ctsp, "AC1FF507-9966-4664-AD5B-4520ADCB7FB0");
//    }

    public boolean updateSoLuong(SanPham ctsp, String ma) {
        int check = 0;
        String query = "UPDATE [dbo].[ChiTietSP]"
                + "   SET"
                + "      [SoLuongTon] = ?"
                + " WHERE MaCTSP = ?";
        try (Connection cnn = DBConnection.getConnection(); PreparedStatement ps = cnn.prepareStatement(query)) {
            ps.setObject(1, ctsp.getSoLuongTon());
            ps.setObject(2, ma);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public List<SanPham> getSP() {
        List<SanPham> ListSP = new ArrayList<>();
        ListSP = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String select = "SELECT * FROM SanPham";
        try {
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ListSP.add(new SanPham(rs.getString(1), rs.getString(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPhamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListSP;
    }

    public List<MauSac> getMS() {
        List<MauSac> ListMS = new ArrayList<>();
        ListMS = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String select = "SELECT * FROM MauSac";
        try {
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ListMS.add(new MauSac(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPhamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListMS;
    }

    public List<ChatLieu> getCL() {
        List<ChatLieu> ListCL = new ArrayList<>();
        ListCL = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String select = "SELECT * FROM ChatLieu";
        try {
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ListCL.add(new ChatLieu(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPhamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListCL;
    }

    public List<KichThuoc> getSize() {
        List<KichThuoc> ListSize = new ArrayList<>();
        ListSize = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String select = "SELECT * FROM KichThuoc";
        try {
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ListSize.add(new KichThuoc(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPhamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListSize;
    }

    public List<DongSP> getDSP() {
        List<DongSP> ListDSP = new ArrayList<>();
        ListDSP = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String select = "SELECT * FROM DongSP";
        try {
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ListDSP.add(new DongSP(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPhamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListDSP;
    }

    public List<NhaSanXuat> getNSX() {
        List<NhaSanXuat> ListNSX = new ArrayList<>();
        ListNSX = new ArrayList<>();
        Connection conn = DBConnection.getConnection();
        String select = "SELECT * FROM NSX";
        try {
            PreparedStatement ps = conn.prepareStatement(select);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ListNSX.add(new NhaSanXuat(rs.getInt(1), rs.getString(2)));
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChiTietSanPhamRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ListNSX;
    }

//    public List<ChiTietSanPham> timKiemTheoTenHoacMa(String txt) {
//        List<ChiTietSanPham> listctsp = new ArrayList<>();
//        Connection conn = DBConnection.getConnection();
//        String query = "\"SELECT [MaCTSP]\\n\"\n" +
//"                + \"      ,[MaSP]\"\n" +
//"                + \"      ,[MaNsx]\"\n" +
//"                + \"      ,[MaMauSac]\"\n" +
//"                + \"      ,[MaDongSP]\"\n" +
//"                + \"      ,[MaChatLieu]\"\n" +
//"                + \"      ,[MaSize]\"\n" +
//"                + \"      ,[MoTa]\"\n" +
//"                + \"      ,[SoLuongTon]\"\n" +
//"                + \"      ,[GiaNhap]\"\n" +
//"                + \"      ,[GiaBan]\"\n" +
//"                + \"  FROM ChiTietSP ctsp join SanPham sp On ctsp.MaSP = sp.Ma\"\n" +
//"                + \"  join NSX nsx On ctsp.MaNsx = nsx.Ma\"\n" +
//"                + \"  join MauSac ms On ctsp.MaMauSac = ms.Ma\"\n" +
//"                + \"  join ChatLieu cl On ctsp.MaChatLieu = cl.Ma\"\n" +
//"                + \"  join DongSP dsp On ctsp.MaDongSP = dsp.Ma\"\n" +
//"                + \"  join KichThuoc size On ctsp.MaSize = size.Ma\"\n" +
//"                + \"  WHERE ctsp.MaCTSP like ? OR ctsp.MaSP like ? OR ctsp.MaNsx\"\n" +
//"                + \"  like ? OR ctsp.MaChatLieu like ? OR ctsp.MaMauSac like ? OR ctsp.MaDongSP like ? OR ctsp.MaSize like ?\"";
//        try {
//            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setObject(1, "%" + txt + "%");
//            ps.setObject(2, "%" + txt + "%");
//            ps.setObject(3, "%" + txt + "%");
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                DangNhap dn = new DangNhap(rs.getString(8));
//                ChucVu cv = new ChucVu(rs.getInt(10), rs.getString(11));
//
//                listNV.add(new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), dn, cv, rs.getInt(12)));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//        return listNV;
//    }
//    public static void main(String[] args) {
//        ChiTietSanPham ctsp = new ChiTietSanPham("","", "", "", "", "", "", "", Integer.valueOf(22), Double.valueOf(2000), Double.valueOf(3000), "");
//        boolean testdelete = new ChiTietSanPhamRepository().add(ctsp);
//        System.out.println(testdelete);
//    }

}
