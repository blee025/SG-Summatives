/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.superherosightings.services;

/**
 *
 * @author blee0
 */
public class SuperheroSightingsServiceException extends Exception {
    
        public SuperheroSightingsServiceException( String message ){
        super( message );
    }
    
    public SuperheroSightingsServiceException( String message, Throwable inner ){
        super( message, inner );
    }    
    
    
}
