/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.flooringmastery.dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 *
 * @author blee0
 */
public class Order {

    //material cost, laborcost, taxtotal, and total will not need setters, create method to compute these numbers 
    
    private int orderNumber;
    
    private LocalDate date;

    private String customerName;

    private String state;

    private BigDecimal taxRate;

    private String productType;

    private BigDecimal area;

    private BigDecimal costPerSquareFoot;

    private BigDecimal laborCostPerSquareFoot;
    
    private BigDecimal materialCost;
    
    private BigDecimal laborCost;
    
    private BigDecimal tax;
    
    private BigDecimal total;
    
    

    /**
     * @return the orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the taxRate
     */
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    /**
     * @param taxRate the taxRate to set
     */
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    /**
     * @return the productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     * @param productType the productType to set
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     * @return the area
     */
    public BigDecimal getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(BigDecimal area) {
        this.area = area;
    }

    /**
     * @return the costPerSquareFoot
     */
    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    /**
     * @param costPerSquareFoot the costPerSquareFoot to set
     */
    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }


    /**
     * @return the laborCostPerSquareFoot
     */
    public BigDecimal getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    /**
     * @param laborCostPerSquareFoot the laborCostPerSquareFoot to set
     */
    public void setLaborCostPerSquareFoot(BigDecimal laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    /**
     * @return the materialCost
     */
    public BigDecimal getMaterialCost() {
        BigDecimal materialCostTotalPreScale = costPerSquareFoot.multiply(area);
        BigDecimal materialCostTotal = materialCostTotalPreScale.setScale(2, RoundingMode.HALF_UP);
        
        return materialCostTotal;
    }

    /**
     * @param materialCost the materialCost to set
     */
//    public void setMaterialCost(BigDecimal materialCost) {
//        this.materialCost = materialCost;
//    }

    /**
     * @return the tax
     */
    public BigDecimal getTax() {
        BigDecimal preTaxTotal = getMaterialCost().add(getLaborCost());
        BigDecimal actualTaxPercent = taxRate.divide(new BigDecimal("100.00"));
        BigDecimal taxTotalPreScale= preTaxTotal.multiply(actualTaxPercent);
        BigDecimal taxTotal = taxTotalPreScale.setScale(2, RoundingMode.HALF_UP);
        
        return taxTotal;
    }

    /**
     * @param tax the tax to set
     */
//    public void setTax(BigDecimal tax) {
//        this.tax = tax;
//    }

    /**
     * @return the total
     */
    public BigDecimal getTotal() {
        BigDecimal totalOfAllPreScale = getMaterialCost().add(getLaborCost().add(getTax()));
        BigDecimal totalOfAll = totalOfAllPreScale.setScale(2, RoundingMode.HALF_UP);
        
        return totalOfAll;
    }

    /**
     * @param total the total to set
     */
//    public void setTotal(BigDecimal total) {
//        this.total = total;
//    }

    /**
     * @return the laborCost
     */
    public BigDecimal getLaborCost() {
        BigDecimal laborCostTotalPreScale = laborCostPerSquareFoot.multiply(area);
        BigDecimal laborCostTotal = laborCostTotalPreScale.setScale(2, RoundingMode.HALF_UP);
        
        return laborCostTotal;
    }

    /**
     * @param laborCost the laborCost to set
     */
//    public BigDecimal setLaborCost() {
//        this.laborCost = laborCost;
//    }
//    

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    //need to correctly set date up 

}
