package com.example.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * A class to control the add part form
 * @author Chris Vachon
 */
public class AddPartController {

    private static int partID = 0;

    @FXML
    private Label radioChangeAddPart;
    @FXML
    private RadioButton inHouseAddPart, outsourcedAddPart;
    @FXML
    private Button cancelAddPartButton;
    @FXML
    private TextField addName, addInv, addPrice, addMax, addMin, addRadio;


    /**
     * Saves the part and updates part ID. Then closes the form
     * Displays an error if the fields aren't all filled out, or filled out incorrectly
     */
    @FXML
    public void saveAddPart(){
        InHouse partI;
        Outsourced partO;
        try {
            if (addName.getText().isEmpty() || addPrice.getText().isEmpty() || addInv.getText().isEmpty() || addMax.getText().isEmpty() || addMin.getText().isEmpty() || addRadio.getText().isEmpty()) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Fill in all fields");
                error.showAndWait();
            } else if (Integer.parseInt(addMax.getText()) <= Integer.parseInt(addMin.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Max must be greater than min");
                error.showAndWait();
            } else if (Integer.parseInt(addMin.getText()) > Integer.parseInt(addInv.getText()) || Integer.parseInt(addMax.getText()) < Integer.parseInt(addInv.getText())) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Inv must be between min and Max");
                error.showAndWait();
            } else {
                if (inHouseAddPart.isSelected()) {
                    partI = new InHouse(partID, addName.getText(), Double.parseDouble(addPrice.getText()), Integer.parseInt(addInv.getText()), Integer.parseInt(addMin.getText()), Integer.parseInt(addMax.getText()));
                    partI.setMachineId(Integer.parseInt(addRadio.getText()));

                    Inventory.addPart(partI);
                } else {
                    partO = new Outsourced(partID, addName.getText(), Double.parseDouble(addPrice.getText()), Integer.parseInt(addInv.getText()), Integer.parseInt(addMin.getText()), Integer.parseInt(addMax.getText()));
                    partO.setCompanyName(addRadio.getText());

                    Inventory.addPart(partO);
                }

                partID++;
                cancelAddPart();
            }
        }
        catch (Exception e){
            String inOutString = "";
            if (inHouseAddPart.isSelected()){
                inOutString = "Machine ID, ";
            }
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Please ensure " + inOutString + "Max, Min, Inv, and Cost are all Numeric.");
            error.showAndWait();
        }

    }
    /**
     * Closes Add Part Form.
     */
    @FXML
    public void cancelAddPart(){
        Stage stage = (Stage) cancelAddPartButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Changes the text on a label for the form.
     */
    @FXML
    public void radioAddPart(){
        if(inHouseAddPart.isSelected()){
            radioChangeAddPart.setText("Machine ID");
        }
        else{
            radioChangeAddPart.setText("Company Name");
        }
    }
}
