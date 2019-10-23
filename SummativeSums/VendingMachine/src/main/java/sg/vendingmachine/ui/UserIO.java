/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.vendingmachine.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author blee0
 */
public interface UserIO {
    
    BigDecimal readBigDecimal(String prompt);
   
    BigDecimal readBigDecimal(String prompt,  BigDecimal min,  BigDecimal max);
   
    void print(String msg);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);

    String readString(String prompt);
    
    public LocalDate readDate(String please_Enter_Release_Date, int i, int j); ///may need to update/fix parameters
}
