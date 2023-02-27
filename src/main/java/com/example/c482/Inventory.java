package com.example.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * A static class used to keep an inventory of all products and parts
 * @author Chris Vachon
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds a part to the list of parts
     * @param newPart The part to be added to the ObservableList allParts
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /**
     * Adds a product to the list of products
     * @param newProduct The product to be added to the ObservableList allProducts
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    /**
     * Finds a part by id
     * @param partID the id of the part to be found
     * @return The part that matches the given id, null if not found
     */
    public static Part lookupPart(int partID){
        for (Part part: allParts) {
            if(part.getId() == partID){
                return part;
            }
        }
        return null;
    }
    /**
     * Finds parts by name
     * @param partName the name of the parts to be found
     * @return ObservableList of all parts matching the given name.
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partialList = FXCollections.observableArrayList();

        for(int i = 0; i < allParts.size(); i++) {
            if(partName.equals(allParts.get(i).getName())){
                partialList.add(allParts.get(i));
            }
        }

        return partialList;
    }
    /**
     * Finds a product by id
     * @param productID the id of the product to be found
     * @return The product that matches the given id, null if not found.
     */
    public static Product lookupProduct(int productID){
        for (Product product: allProducts) {
            if(product.getId() == productID){
                return product;
            }
        }
        return null;
    }
    /**
     * Finds products by name
     * @param productName the name of the parts to be found
     * @return ObservableList of all products matching the given name.
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> newProductList = FXCollections.observableArrayList();

        for (Product product: allProducts) {
            if (product.getName().equals(productName)){
                newProductList.add(product);
            }
        }
        return newProductList;
    }
    /**
     * Replaces the part found at the index with the new part
     * (Originally used id number for index param. Had to rewrite entire method.)
     * @param index identifies the part's position in the list
     * @param selectedPart the part that replaces the part found at the index
     */
    public static void updatePart(int index, Part selectedPart){
       allParts.set(index, selectedPart);
    }
    /**
     * Replaces the product found at the index with the new product
     * (Originally used id number for index param. Had to rewrite entire method.)
     * @param index identifies the product's position in the list
     * @param selectedProduct the product that replaces the product found at the index
     */
    public static void updateProduct(int index, Product selectedProduct){

        allProducts.set(index, selectedProduct);
    }
    /**
     * Removes the selected part from the list
     * @param selectedPart the part that gets removed from the list
     */
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }
    /**
     * Removes the selected product from the list
     * @param selectedProduct the product that gets removed from the list
     */
    public static boolean deleteProduct(Product selectedProduct){
       return allProducts.remove(selectedProduct);
   }

    /**
     * Returns the entire list of parts
     * @return ObservableList of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    /**
     * Returns the entire list of products
     * @return ObservableList of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
