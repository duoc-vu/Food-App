package com.example.btl_last;

import java.io.Serializable;

public class monAn implements Serializable {
    private int ID;
    private String Name ;
    private String chuThich;

    private String diaChi;
    private byte[] hinh;


    public monAn(int ID, String name, String chuThich, String diaChi, byte[] hinh) {
        this.ID = ID;
        Name = name;
        this.chuThich = chuThich;
        this.diaChi = diaChi;
        this.hinh = hinh;
    }



    public byte[] getHinh() {
        return hinh;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setHinh(byte[] hinh) {
        this.hinh = hinh;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getChuThich() {
        return chuThich;
    }

    public void setChuThich(String chuThich) {
        this.chuThich = chuThich;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String chuThich) {
        this.diaChi = diaChi;
    }
}
