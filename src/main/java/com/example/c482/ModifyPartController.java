package com.example.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * A class to control the modify part form
 * @author Chris Vachon
 */
public class ModifyPartController implements Initializable {

    @FXML
    private TextField partIDm, partNamem, partInvm, partCostm, partMaxm, partRadiom, partMinm;
    @FXML
    private Label radioChangeModifyPart;
    @FXML
    private RadioButton inHouseModifyPart, outsourcedModifyPart;
    @FXML
    private Button cancelModifyPartButton;

    /**
     * Closes Modify Part Form.
     */
    @FXML
    public void cancelModifyPart() {
        Stage stage = (Stage) cancelModifyPartButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Saves the part changes. Then closes the form
     * Displays an error if the fields aren't all filled out, or filled out incorrectly
     */
    @FXML
    public void saveModifyPart() {
        InHouse partI;
        Outsourced partO;

        int index = Inventory.getAllParts().indexOf(MainController.getPart());
        try {
            if (partNamem.getText().isEmpty() || partCostm.getText().isEmpty() || partInvm.getText().isEmpty() || partMaxm.getText().isEmpty() || partMinm.getText().isEmpty() || partRadiom.getText().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Fill in all fields");
                error.showAndWait();
            } else if (Integer.parseInt(partMaxm.getText()) <= Integer.parseInt(partMinm.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Max must be greater than min");
                error.showAndWait();
            } else if (Integer.parseInt(partMinm.getText()) > Integer.parseInt(partInvm.getText()) || Integer.parseInt(partMaxm.getText()) < Integer.parseInt(partInvm.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Inv must be between min and Max");
                error.showAndWait();
            } else {

                if (inHouseModifyPart.isSelected()) {
                    partI = new InHouse(Integer.parseInt(partIDm.getText()), partNamem.getText(), Double.parseDouble(partCostm.getText()), Integer.parseInt(partInvm.getText()), Integer.parseInt(partMinm.getText()), Integer.parseInt(partMaxm.getText()));
                    partI.setMachineId(Integer.parseInt(partRadiom.getText()));

                    Inventory.updatePart(index, partI);
                } else {
                    partO = new Outsourced(Integer.parseInt(partIDm.getText()), partNamem.getText(), Double.parseDouble(partCostm.getText()), Integer.parseInt(partInvm.getText()), Integer.parseInt(partMinm.getText()), Integer.parseInt(partMaxm.getText()));
                    partO.setCompanyName(partRadiom.getText());

                    Inventory.updatePart(index, partO);
                }

                cancelModifyPart();
            }
        }
        catch (Exception e){
            String inOutString = "";
            if (inHouseModifyPart.isSelected()){
                inOutString = "Machine ID, ";
            }
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Please ensure " + inOutString + "Max, Min, Inv, and Cost are all Numeric.");
            error.showAndWait();
        }
    }

    /**
     * Changes the text on a label for the form.
     */
    @FXML
    public void radioModifyPart() {
        if (inHouseModifyPart.isSelected()) {
            radioChangeModifyPart.setText("Machine ID");
        } else {
            radioChangeModifyPart.setText("Company Name");
        }
    }

    /**
     * Initializes Modify Part controller and populates text fields.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Part selected = MainController.getPart();

        partIDm.setText(String.valueOf(selected.getId()));
        partNamem.setText(String.valueOf(selected.getName()));
        partInvm.setText(String.valueOf(selected.getStock()));
        partCostm.setText(String.valueOf(selected.getPrice()));
        partMaxm.setText(String.valueOf(selected.getMax()));
        partMinm.setText(String.valueOf(selected.getMin()));

        if (selected instanceof InHouse) {
            partRadiom.setText(String.valueOf(((InHouse) selected).getMachineId()));
        } else if (selected instanceof Outsourced) {
            partRadiom.setText(String.valueOf(((Outsourced) selected).getCompanyName()));
            inHouseModifyPart.setSelected(false);
            outsourcedModifyPart.setSelected(true);
            radioChangeModifyPart.setText("Company Name");
        }
    }
}
