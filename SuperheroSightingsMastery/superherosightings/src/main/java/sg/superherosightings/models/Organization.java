/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.models;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 *
 * @author blee0
 */
public class Organization {

    private int id;
    
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 30, message = "Name must be less than 30 characters.")
    private String name;
    
    @NotBlank(message = "Description must not be empty.")
    @Size(max = 150, message = "Description must be less than 150 characters.")
    private String description;
    
    @NotBlank(message = "Address must not be empty.")
    @Size(max = 60, message = "Address must be less than 60 characters.")
    private String address;
    
    private List<Supe> supes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Supe> getSupes() {
        return supes;
    }

    public void setSupes(List<Supe> supes) {
        this.supes = supes;
    }

}
