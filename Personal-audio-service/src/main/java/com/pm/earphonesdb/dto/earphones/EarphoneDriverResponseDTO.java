package com.pm.earphonesdb.dto.earphones;

public class EarphoneDriverResponseDTO {

    private String driverTypeName;
    private int quantity;

    public String getDriverTypeName() {
        return driverTypeName;
    }

    public void setDriverTypeName(String driverTypeName) {
        this.driverTypeName = driverTypeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
