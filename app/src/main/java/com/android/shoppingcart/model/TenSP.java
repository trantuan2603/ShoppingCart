package com.android.shoppingcart.model;

import java.io.Serializable;

/**
 * Created by TRANTUAN on 05-Jan-18.
 */

public class TenSP implements Serializable {
    int id;
    String tensanpham;
    double giasanpham;
    String hinhsanpham;
    String motasanpham;
    int idloaisanpham;


    public TenSP() {
    }

    public TenSP(int id, String tensanpham, double giasanpham, String hinhsanpham, String motasanpham, int idloaisanpham) {
        this.id = id;
        this.tensanpham = tensanpham;
        this.giasanpham = giasanpham;
        this.hinhsanpham = hinhsanpham;
        this.motasanpham = motasanpham;
        this.idloaisanpham = idloaisanpham;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensanpham() {
        return tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        this.tensanpham = tensanpham;
    }

    public double getGiasanpham() {
        return giasanpham;
    }

    public void setGiasanpham(double giasanpham) {
        this.giasanpham = giasanpham;
    }

    public String getHinhsanpham() {
        return hinhsanpham;
    }

    public void setHinhsanpham(String hinhsanpham) {
        this.hinhsanpham = hinhsanpham;
    }

    public String getMotasanpham() {
        return motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        this.motasanpham = motasanpham;
    }

    public int getIdloaisanpham() {
        return idloaisanpham;
    }

    public void setIdloaisanpham(int idloaisanpham) {
        this.idloaisanpham = idloaisanpham;
    }
}
