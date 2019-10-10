/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.daos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import sg.flooringmastery.dtos.Tax;

/**
 *
 * @author blee0
 */
public class FlooringTaxInMemoDao implements FlooringTaxDao {
    
    List<Tax> allTaxes = new ArrayList<>();
    
    public FlooringTaxInMemoDao () {
        
        Tax t1 = new Tax();
        
        t1.setState("OH");
        t1.setTaxRate(new BigDecimal("6.25"));
        
        allTaxes.add(t1);
        
        Tax t2 = new Tax();
        
        t2.setState("PA");
        t2.setTaxRate(new BigDecimal("6.75"));
        
        allTaxes.add(t2);
        
        Tax t3 = new Tax();
        
        t3.setState("MI");
        t3.setTaxRate(new BigDecimal("5.75"));
        
        allTaxes.add(t3);
        
        Tax t4 = new Tax();
        
        t4.setState("IN");
        t4.setTaxRate(new BigDecimal("6.00"));
        
        allTaxes.add(t4);
        
        Tax t5 = new Tax();
        
        t5.setState("WA");
        t5.setTaxRate(new BigDecimal("6.50"));
        
        allTaxes.add(t5);
        
        Tax t6 = new Tax();
        
        t6.setState("VA");
        t6.setTaxRate(new BigDecimal("4.30"));
        
        allTaxes.add(t6);
           
    }

    @Override
    public List<Tax> getTaxes() {
        return allTaxes;
    }
    
}


