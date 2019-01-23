package com.robert.bethub.Model;

public class membershipData {


    String content;
    int image;
    String title;
    int imageArray;
    public membershipData(String content, int image, String title) {
        this.content = content;
        this.title = title;
        this.image=image;
    }

    public String getContent() {
        return content;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setImageNumber(int numberImage){

        this.imageArray = numberImage;
    }

    public int getImageNumber(){

        return imageArray;
    }

}
