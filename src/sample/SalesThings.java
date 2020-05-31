package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class SalesThings {
    private SimpleIntegerProperty SProdID;
    private SimpleStringProperty SProdName;
    private SimpleIntegerProperty SProdQty;

    public SalesThings(int SProdID, String SProdName, int SProdQty) {
        this.SProdID = new SimpleIntegerProperty(SProdID);
        this.SProdName = new SimpleStringProperty(SProdName);
        this.SProdQty = new SimpleIntegerProperty(SProdQty);

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
}
