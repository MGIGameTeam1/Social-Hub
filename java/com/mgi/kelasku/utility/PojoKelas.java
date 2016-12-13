package com.mgi.kelasku.utility;

/**
 * Created by Achmad Siddik on 13/12/2016.
 */

public class PojoKelas {
    String namaGroup;
    String deskripsi;
    String admin;

    public PojoKelas(){

    }

    public PojoKelas(String NamaGroup, String Deskripsi, String Admin) {
        this.namaGroup = NamaGroup;
        this.deskripsi = Deskripsi;
        this.admin = Admin;
    }

    public String getNamaGroup() {
        return namaGroup;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getAdmin() {
        return admin;
    }
}
