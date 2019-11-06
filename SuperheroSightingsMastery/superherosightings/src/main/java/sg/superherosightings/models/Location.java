/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.models;

import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author blee0
 */
public class Location {

    private int id;
    
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 30, message = "Name must be less than 30 characters.")
    private String name;
    
    @NotBlank(message = "Address must not be empty.")
    @Size(max = 60, message = "Address must be less than 60 characters.")
    private String address;
    
    @NotNull(message = "Latitude name must not be empty.")
    @DecimalMin(value = "-90.000001", inclusive = false)
    @DecimalMax(value = "90.000001", inclusive = false)
    @Digits(integer=9, fraction=6, message = "Latitude must be 9 digits with 6 decimal places.")
    private BigDecimal latitude;
    
    @NotNull(message = "Longitude name must not be empty.")
    @DecimalMin(value = "-180.000001", inclusive = false)
    @DecimalMax(value = "180.000001", inclusive = false)
    @Digits(integer=9, fraction=6, message = "Longitude must be 9 digits with 6 decimal places.")
    private BigDecimal longitude;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

}
