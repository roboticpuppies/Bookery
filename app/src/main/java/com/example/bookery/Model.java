package com.example.bookery;

public class Model {
    public String judul, register, pengarang, penerbit, tahun, key;

    public String getKey() {return key;}
    public void setKey(String key) {this.key = key;}

    public String getJudul() {return judul;}
    public void setJudul(String judul) {this.judul = judul;}

    public String getRegister() {return register;}
    public void setRegister(String register) {this.register = register;}

    public String getPengarang() {return pengarang;}
    public void setPengarang(String pengarang) {this.pengarang = pengarang;}

    public String getPenerbit() {return penerbit;}
    public void setPenerbit(String penerbit) {this.penerbit = penerbit;}

    public String getTahun() {return tahun;}
    public void setTahun(String tahun) {this.tahun = tahun;}

    public Model(){

    }
    public Model(String getJudul, String getRegister, String getPengarang, String getPenerbit, String getTahun) {
        this.judul = getJudul;
        this.register = getRegister;
        this.pengarang = getPengarang;
        this.penerbit = getPenerbit;
        this.tahun = getTahun;
    }
}
