package com.kelompok8.Bookuku.model;

public class Post {

    String id;
    private String userID;
    private String username;
    private String kontak;
    private String judul;
    private String deskripsi;
    private String imagePost;
    private String status;
    private String genre;

    //Wajib kasih Constructor Kosong
    public Post() {

    }

    public Post(String id, String userID, String username, String mImagePost, String deskripsi, String judul, String status, String genre, String kontak) {

        this.id = id;
        this.username = username;
        this.kontak = kontak;
        this.imagePost = mImagePost;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.userID = userID;
        this.status = status;
        this.genre = genre;

    }


    public String getImagePost() {
        return imagePost;
    }

    public String getUserID() {
        return userID;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getKontak() { return kontak; }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public String getGenre() {
        return genre;
    }

}
