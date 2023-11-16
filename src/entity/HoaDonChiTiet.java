/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author thong
 */
public class HoaDonChiTiet {

    private Long maHDCTTT;
    private HoaDon maHDCT;
    private ChiTietSanPham maCTSPCT;
    private Integer soLuong;
    private Double donGia;
    private String tenSP;

    public HoaDonChiTiet(Long maHDCTTT, HoaDon maHDCT, ChiTietSanPham maCTSPCT, Integer soLuong, Double donGia) {
        this.maHDCTTT = maHDCTTT;
        this.maHDCT = maHDCT;
        this.maCTSPCT = maCTSPCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

   

    public HoaDonChiTiet(ChiTietSanPham maCTSPCT, Integer soLuong, Double donGia, String tenSP) {
        this.maCTSPCT = maCTSPCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tenSP = tenSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public HoaDonChiTiet() {
    }

    public HoaDon getMaHDCT() {
        return maHDCT;
    }

    public void setMaHDCT(HoaDon maHDCT) {
        this.maHDCT = maHDCT;
    }

    public ChiTietSanPham getMaCTSPCT() {
        return maCTSPCT;
    }

    public void setMaCTSPCT(ChiTietSanPham maCTSPCT) {
        this.maCTSPCT = maCTSPCT;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Double getDonGia() {
        return donGia;
    }

    public void setDonGia(Double donGia) {
        this.donGia = donGia;
    }

}
