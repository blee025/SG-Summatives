/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.daos;

/**
 *
 * @author blee0
 */
public class SuperheroSightingsDaoException extends Exception {

    public SuperheroSightingsDaoException( String message ){
        super( message );
    }
    
    public SuperheroSightingsDaoException( String message, Throwable inner ){
        super( message, inner );
    }    
}
