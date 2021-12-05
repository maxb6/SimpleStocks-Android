package com.example.simplestocks.Model;

import android.graphics.Bitmap;

public class Article {
    private String articleName;
    private String articleSource;
    private String articleText;
    private Bitmap articleImage;


    public Article(String articleName, String articleSource, String articleText, Bitmap articleImage) {
        this.articleName = articleName;
        this.articleSource = articleSource;
        this.articleText = articleText;
        this.articleImage = articleImage;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getArticleSource() {
        return articleSource;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public void setArticleSource(String articleSource) {
        this.articleSource = articleSource;
    }

    public String getArticleText() {
        return articleText;
    }

    public void setArticleText(String articleText) {
        this.articleText = articleText;
    }

    public Bitmap getArticleImage() {
        return articleImage;
    }

    public void setArticleImage(Bitmap articleImage) {
        this.articleImage = articleImage;
    }
}
