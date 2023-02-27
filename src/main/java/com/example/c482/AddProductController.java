package com.example.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * A class to control the add product form
 * @author Chris Vachon
 */
public class AddProductController implements Initializable {

    private static int productID = 0;
    private ObservableList<Part> removeParts = FXCollections.observableArrayList();

    @FXML
    private TableView<Part> addPartsTable;
    @FXML
    private TableColumn<Part, Integer> addPartId;
    @FXML
    private TableColumn<Part, String> addPartName;
    @FXML
    private TableColumn<Part, Integer> addPartInv;
    @FXML
    private TableColumn<Part, Double> addPartPrice;
    @FXML
    private TableView<Part> removePartsTable;
    @FXML
    private TableColumn<Part, Integer> removePartId;
    @FXML
    private TableColumn<Part, String> removePartName;
    @FXML
    private TableColumn<Part, Integer> removePartInv;
    @FXML
    private TableColumn<Part, Double> removePartPrice;
    @FXML
    private Button cancelAddProductButton;
    @FXML
    private TextField searchBarParts, addProductId, addProductName, addProductInv, addProductPrice, addProductMax, addProductMin;

    /**
     * Searches for parts in the table, specified by the users text in the searchbar. It will find full or partial matches.
     * Displays an error if there aren't any items.
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

                        addPartsTable.setItems(foundParts);
                }

                if(searchBarParts.getText().isEmpty()) {
                    addPartsTable.setItems(Inventory.getAllParts());
                }
        }
    /**
     * Adds selected part from the top table to the bottom table in the add product form,
     * after add button is pressed
     * Displays an error if the fields aren't all filled out, or filled out incorrectly
     */
    @FXML
   public void addPart(){
        //Only allows the part to be added once.
       if(!removeParts.contains(addPartsTable.getSelectionModel().getSelectedItem())) {
           removeParts.add(addPartsTable.getSelectionModel().getSelectedItem());
       }
   }
    /**
     * Removes selected part from the bottom table of the add product form,
     * after remove associated part button is pressed
     */
    @FXML
    public void removePart(){
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setHeaderText("Remove " + removePartsTable.getSelectionModel().getSelectedItem().getName() + "?");
        Optional<ButtonType> result = confirm.showAndWait();
        if(result.get() == ButtonType.OK){
            removeParts.remove(removePartsTable.getSelectionModel().getSelectedItem());
        }

    }
    /**
     * Saves all attributes assigned to the product, after save button is pressed. Then closes the add product form
     */
    @FXML
    public void saveAddProduct(){
        try {
            if (addProductName.getText().isEmpty() || addProductPrice.getText().isEmpty() || addProductInv.getText().isEmpty() || addProductMin.getText().isEmpty() || addProductMax.getText().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Fill in all fields");
                error.showAndWait();
            } else if (Integer.parseInt(addProductMax.getText()) <= Integer.parseInt(addProductMin.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Max must be greater than min");
                error.showAndWait();
            } else if (Integer.parseInt(addProductMin.getText()) > Integer.parseInt(addProductInv.getText()) || Integer.parseInt(addProductMax.getText()) < Integer.parseInt(addProductInv.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Inv must be between min and Max");
                error.showAndWait();
            } else {
                Product newProduct = new Product();
                newProduct.setId(productID);
                newProduct.setName(addProductName.getText());
                newProduct.setPrice(Double.parseDouble(addProductPrice.getText()));
                newProduct.setStock(Integer.parseInt(addProductInv.getText()));
                newProduct.setMin(Integer.parseInt(addProductMin.getText()));
                newProduct.setMax(Integer.parseInt(addProductMax.getText()));
                productID++;


                for (Part parts : removeParts) {
                    newProduct.addAssociatedPart(parts);
                }

                Inventory.addProduct(newProduct);
                cancelAddProduct();
            }
        }
        catch (Exception e){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Please ensure Max, Min, Inv, and Price are all Numeric.");
            error.showAndWait();
        }
    }

    /**
     * Closes Add Part Form.
     */
    @FXML
    public void cancelAddProduct(){
            Stage stage = (Stage) cancelAddProductButton.getScene().getWindow();
            stage.close();
    }

    /**
     * Initializes the form by filling in the top table with all available parts.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addPartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        addPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        addPartInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        addPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        removePartId.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        removePartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        removePartInv.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        removePartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));

        addPartsTable.setItems(Inventory.getAllParts());
        removePartsTable.setItems(removeParts);
    }
}


