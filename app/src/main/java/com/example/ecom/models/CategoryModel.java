package com.example.ecom.models;

public class CategoryModel {

     int categoryLogo;
    String categoryName;

    public CategoryModel() {
    }

    public CategoryModel(int categoryLogo, String categoryName) {
        this.categoryLogo = categoryLogo;
        this.categoryName = categoryName;
    }

    public int getCategoryLogo() {
        return categoryLogo;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
