package com.example.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A class that identifies a product and the parts that make it up
 * @author Chris Vachon
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     * Setter method
     * @param id The id of the product
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Setter method
     * @param name The name of the product
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Setter method
     * @param price The price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Setter method
     * @param stock The amount of product in stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Setter method
     * @param min The minimum amount of product allowed in stock
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Setter method
     * @param max The maximum amount of product allowed in stock
     */public void setMax(int max) {
        this.max = max;
    }

    /**
     * Getter method
     * @return product id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter method
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method
     * @return product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Getter method
     * @return Amount of product in stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Getter method
     * @return Minimum amount of product allowed in stock
     */
    public int getMin() {
        return min;
    }

    /**
     * Getter method
     * @return Maximum amount of product allowed in stock
     */
    public int getMax() {
        return max;
    }

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    /**
     * Deletes the selected part from the list of parts associsted with this item
     * @param selectedAssociatedPart the part to be deleted from the ObservableList
     * @return true if the part was removed, else false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){return associatedParts.remove(selectedAssociatedPart);}

    /**
     * Getter method
     * @return The ObservableList of all parts associated with this product
     */
    public ObservableList<Part> getAllAssociatedParts(){return associatedParts;}
}
