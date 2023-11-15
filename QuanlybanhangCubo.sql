USE
[QLBH_AO_THUN_NAM]
CREATE 
DATABASE [QLBH_AO_THUN_NAM]
GO

USE [QLBH_AO_THUN_NAM]
GO


-- NSX
CREATE TABLE NSX(
MaNsx INT
	IDENTITY(1,1) PRIMARY KEY,
Ten NVARCHAR(30)
)
GO

-- MauSac
CREATE TABLE MauSac(
MaMau INT
	IDENTITY(1,1) PRIMARY KEY,
Ten NVARCHAR(30)
)
GO
-- DongSP
CREATE TABLE DongSP(
MaDongSp INT
	IDENTITY(1,1) PRIMARY KEY,
Ten NVARCHAR(30)
)
GO


-- ChatLieu
CREATE TABLE ChatLieu(
MaChatLieu INT
	IDENTITY(1,1) PRIMARY KEY,
Ten NVARCHAR(30)
)
GO

-- SanPham



CREATE TABLE SanPham(
MaSP varchar(6)  PRIMARY KEY,

MaNsx INT
REFERENCES NSX (MaNsx),
MaMauSac INT
REFERENCES MauSac (MaMau),
MaDongSP INT
REFERENCES DongSP (MaDongSp),
MaChatLieu INT
REFERENCES ChatLieu (MaChatLieu),
Size varchar(10),
MoTa NVARCHAR(50) DEFAULT NULL,
SoLuongTon INT,
GiaNhap DECIMAL(20,3) DEFAULT 0,
GiaBan DECIMAL(20,3) DEFAULT 0,
Anh VARCHAR(50)
)
GO

--KhachHang
CREATE TABLE KhachHang
(
    MaKhachHang   varchar(6) PRIMARY KEY,
    TenKhachHang   NVARCHAR(50),
    DiaChi         NVARCHAR( MAX),
    SoDienThoai    VARCHAR(15),
    Email          VARCHAR(50),
	 GioiTinh       BIT,
    TrangThai      INT
)
    GO

--NhanVien
CREATE TABLE NhanVien(
 MaNhanVien   varchar(6) PRIMARY KEY,
TenNhanVien   NVARCHAR(50),
GioiTinh NVARCHAR(10) DEFAULT NULL,
NgaySinh DATE DEFAULT NULL,
DiaChi NVARCHAR(100) DEFAULT NULL,
Sdt VARCHAR(30) DEFAULT NULL,
Email VARCHAR(50) NULL,
Taikhoan varchar(30),
MatKhau Varchar(50),
chucvu Nvarchar(20),
TrangThai INT DEFAULT 0
)

--HoaDon
CREATE TABLE HoaDon(
MaHoaDon    BIGINT
        IDENTITY(1,1) PRIMARY KEY,
IdKH varchar(6)
	REFERENCES KhachHang (MaKhachHang),
IdNV  varchar(6)
	REFERENCES NhanVien (MaNhanVien),
NgayTao DATE DEFAULT NULL,
NgayThanhToan DATE DEFAULT NULL,
TinhTrang INT DEFAULT 0,
GhiChu NVARCHAR(max) DEFAULT NULL,
Tongtien DECIMAL(20,3) null,
Chietkhau DECIMAL(20,3),
ThanhToan DECIMAL(20,3) null,
PhuongThucThanhToan  NVARCHAR(30)
)
GO

-- HoaDonChiTiet
CREATE TABLE HoaDonChiTiet(
MaHDCT    BIGINT
	 IDENTITY(1,1) PRIMARY KEY,
MaHoaDon BIGINT
REFERENCES HoaDon(MaHoaDon),
MaSP varchar(6)
REFERENCES SanPham(MaSp),
tenSp Nvarchar(50),
SoLuong INT,
DonGia DECIMAL(20,3) DEFAULT 0,
TinhTrang int Default 0 null
)

-- Khuyenmai
CREATE TABLE KhuyenMai
(
    MaPhieu         VARCHAR(10) PRIMARY KEY,
    TenPhieu        NVARCHAR(20),

    GiaTriGiamToiDa MONEY,
    HinhThucGiam    BIT, -- % hay gia tien
	MoTa			 NVARCHAR(Max),
    
	MaSanPham       varchar(6)
        REFERENCES  Sanpham(MaSp)
)
    GO