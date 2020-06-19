package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesThings implements  interfcthings{
    private SimpleIntegerProperty SProdID;
    private SimpleStringProperty SProdName;
    private SimpleIntegerProperty SProdQty;
    private SimpleIntegerProperty Price;

    public SalesThings(int SProdID, String SProdName, int SProdQty, int Price) {
        this.SProdID = new SimpleIntegerProperty(SProdID);
        this.SProdName = new SimpleStringProperty(SProdName);
        this.SProdQty = new SimpleIntegerProperty(SProdQty);
        this.Price = new SimpleIntegerProperty(Price);
    }
    public int getSProdID() {
        return SProdID.get();
    }

    public SimpleIntegerProperty SProdIDProperty() {
        return SProdID;
    }

    public void setSProdID(int sprodid) {
        this.SProdID.set(sprodid);
    }

    public String getSProdName() {
        return SProdName.get();
    }

    public SimpleStringProperty SProdNameProperty() {
        return SProdName;
    }

    public void setSProdName(String sprodname) {
        this.SProdName.set(sprodname);
    }

    public int getSProdQty() {
        return SProdQty.get();
    }

    public SimpleIntegerProperty SProdQtyProperty() {
        return SProdQty;
    }

    public void setSProdQty(int sprodqty) {
        this.SProdQty.set(sprodqty);
    }
    @Override
    public int getPrice() {
        return Price.get();
    }
    @Override
    public SimpleIntegerProperty priceProperty() {
        return Price;
    }
    @Override
    public void setPrice(int price) {
        this.Price.set(price);
    }
}