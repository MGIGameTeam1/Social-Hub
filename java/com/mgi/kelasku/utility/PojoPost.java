package com.mgi.kelasku.utility;

/**
 * Created by Luc1_ on 12/13/2016.
 */

public class PojoPost {

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    String tanggal;
    String post;

    public PojoPost(String tanggal, String post) {
        this.tanggal = tanggal;
        this.post = post;
    }





}
