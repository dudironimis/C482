package com.example.c482;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A class to control the main form.
 * Could improve by adding buttons that allows copying parts/products
 * @author Chris Vachon
 */
public class MainController implements Initializable{

    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partID;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, Integer> partInv;
    @FXML
    private TableColumn<Part, Double> partCost;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productID;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, Integer> productInv;
    @FXML
    private TableColumn<Product, Double> productCost;
    @FXML
    private TextField searchBarParts;
    private static Part selectedPart;
    private static Product selectedProduct;


    /**
     * Returns the part selected in the parts table
     * @return selected part
     */
    public static Part getPart(){
        return selectedPart;
    }

    /**
     * Returns the product selected in the products table
     * @return selected product
     */
    public static Product getProduct(){
        return selectedProduct;
    }

    /**
     * Opens the Add Part Form to allow users to add a part
     * (Could not get it to open. It was due to a misnamed variable in the InHouse code.)
     */
    @FXML
    public void onAddPartButtonClick() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add Part.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Opens up the Modify Part Form for the selected part.
     * If no part is selected, there is an error.
     */
    @FXML
    void onModifyPartButtonClick() {
        selectedPart = partTable.getSelectionModel().getSelectedItem();

        if(selectedPart == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("No Items Selected");
            error.showAndWait();
        }
        else{
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modify Part.fxml"));
                Parent parent = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Deletes the part the user selects from the table.
     */
    @FXML
    public void onDeletePartButtonClick() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Delete " + partTable.getSelectionModel().getSelectedItem().getName() + "?");
        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deletePart(partTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Opens the Add Product Form to allow users to add a product
     */
    @FXML
    public void onAddProductButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Add Product.fxml"));
        Parent parent = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    /**
     * Opens up the Modify Product Form for the selected part.
     * If no part is selected, there is an error.
     */
    @FXML
    void onModifyProductButtonClick() {
        selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if(selectedProduct == null) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("No Items Selected");
            error.showAndWait();
        }
        else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Modify Product.fxml"));
                Parent parent = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Deletes the product the user selects from the table.
     */
    @FXML
    public void onDeleteProductButtonClick() {
        boolean deletable = true;
        for (Part part: Inventory.getAllParts()) {
            if(productTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().contains(part)){
                deletable = false;
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText(productTable.getSelectionModel().getSelectedItem().getName() + " cannot be deleted while " + part.getName() + " is in inventory");
                error.showAndWait();
                break;
            }

        }
        if(deletable) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setHeaderText("Delete " + productTable.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(productTable.getSelectionModel().getSelectedItem());
            }
        }
    }

    /**
     * Searches for parts in the table, specified by the users text in the searchbar. It will find full or partial matches.
     * Displays an error if there aren't any items.
     * (Had an runtime error whenever I searched for an id that wasn't found. I was trying to parse searchBarParts.getText()
     * into an integer. Casting the id to a string fixed it.)
     * @param e Listens for enter key to be pressed to begin search.
     */
    @FXML
    public void searchParts(KeyEvent e){
        if(e.getCode().equals(KeyCode.ENTER)){
            ObservableList<Part> all = Inventory.getAllParts();
            ObservableList<Part> foundParts = FXCollections.observableArrayList();

            for(Part parts : all) {
                if (parts.getName().contains(searchBarParts.getText()) || String.valueOf(parts.getId()).contains(searchBarParts.getText())) {
                    foundParts.add(parts);
                }
            }
            if(foundParts.size() == 0){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Items Not Found");
                error.showAndWait();
            }

            partTable.setItems(foundParts);
        }

        if(searchBarParts.getText().isEmpty()){
            partTable.setItems(Inventory.getAllParts());
        }
    }

    /**
     * Searches for parts in the table, specified by the users text in the searchbar. It will find full or partial matches.
     * Displays an error if there aren't any items.
     * @param e Listens for enter key to be pressed to begin search.
     */
    @FXML
    public void searchProducts(KeyEvent e){
        if(e.getCode().equals(KeyCode.ENTER)){
            ObservableList<Product> all = Inventory.getAllProducts();
            ObservableList<Product> foundProducts = FXCollections.observableArrayList();

            for(Product products : all) {
                if (products.getName().contains(searchBarParts.getText()) || String.valueOf(products.getId()).contains(searchBarParts.getText())) {
                    foundProducts.add(products);
                }
            }
            if(foundProducts.size() == 0){
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Items Not Found");
                error.showAndWait();
            }

            productTable.setItems(foundProducts);
        }

        if(searchBarParts.getText().isEmpty()){
            productTable.setItems(Inventory.getAllProducts());
        }
    }

    /**
     * Closes the program.
     */
    @FXML
    public void exitProgram(){
        Platform.exit();
    }

    /**
     * Populates the product and parts tables.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        productID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        productInv.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        partTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());
    }
}