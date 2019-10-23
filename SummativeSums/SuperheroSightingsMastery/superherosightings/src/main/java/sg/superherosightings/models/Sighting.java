/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.models;

import java.time.LocalDate;

/**
 *
 * @author blee0
 */
public class Sighting {
    
    private int id;
    private LocalDate date;
    private Location location;
    private Supe supe;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Supe getSupe() {
        return supe;
    }

    public void setSupe(Supe supe) {
        this.supe = supe;
    }
    
}
