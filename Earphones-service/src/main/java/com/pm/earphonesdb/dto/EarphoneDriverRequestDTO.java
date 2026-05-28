package com.pm.earphonesdb.dto;

public class EarphoneDriverRequestDTO {
    private long driverTypeId;

    private int quantity;

    public long getDriverTypeId() {
        return driverTypeId;
    }

    public void setDriverTypeId(long driverTypeId) {
        this.driverTypeId = driverTypeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
