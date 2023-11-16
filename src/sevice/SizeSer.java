/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import entity.KichThuoc;
import java.util.List;

/**
 *
 * @author admin
 */
public interface SizeSer {
    List<KichThuoc> getAll();

    KichThuoc getOneSize(int ma);

    String add(KichThuoc kt);

    String update(KichThuoc kt, int ma);

    String delete(int ma);
}
