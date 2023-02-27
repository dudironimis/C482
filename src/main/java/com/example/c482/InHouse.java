package com.example.c482;
/**
 * A concrete class that extends a part and adds machineId
 * @author Chris Vachon
 */
public class InHouse extends Part {

    private int machineId;
    /**
     * Constructor
     * @param id ID number used to identify the part
     * @param name Part name
     * @param price Price of the part
     * @param stock The amount of this part found in inventory.
     * @param min Minimum amount of stock allowed for this part.
     * @param max Maximum amount of stock allowed for this part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Getter method
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }
    /**
     * Setter method
     * @param machineId ID number for the machine used to create the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }


}
