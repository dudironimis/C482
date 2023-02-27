package com.example.c482;
/**
 * A concrete class that extends a part and adds the name of the company that makes the part
 * @author Chris Vachon
 */
public class Outsourced extends Part {

    private String companyName;
    /**
     * Constructor
     * @param id ID number used to identify the part
     * @param name Part name
     * @param price Price of the part
     * @param stock The amount of this part found in inventory.
     * @param min Minimum amount of stock allowed for this part.
     * @param max Maximum amount of stock allowed for this part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max) {
        super(id, name, price, stock, min, max);
    }

    /**
     * Getter method
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    /**
     * Setter method
     * @param companyName The name of the company that creates the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
