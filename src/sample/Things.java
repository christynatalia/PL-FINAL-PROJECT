package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Things {
    private SimpleIntegerProperty Nameid;
    private SimpleStringProperty Name;
    private SimpleIntegerProperty Quantity;
    private SimpleIntegerProperty Price;

    public Things(int Nameid, String Name, int Quantity, int Price) {
        this.Nameid = new SimpleIntegerProperty(Nameid);
        this.Name = new SimpleStringProperty(Name);
        this.Quantity = new SimpleIntegerProperty(Quantity);
        this.Price = new SimpleIntegerProperty(Price);
    }


    public int getNameid() {
        return Nameid.get();
    }

    public SimpleIntegerProperty nameidProperty() {
        return Nameid;
    }

    public void setNameid(int nameid) {
        this.Nameid.set(nameid);
    }

    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name.set(name);
    }

    public int getQuantity() {
        return Quantity.get();
    }

    public SimpleIntegerProperty quantityProperty() {
        return Quantity;
    }

    public void setQuantity(int qty) {
        this.Quantity.set(qty);
    }

    public int getPrice() {
        return Price.get();
    }

    public SimpleIntegerProperty priceProperty() {
        return Price;
    }

    public void setPrice(int price) {
        this.Price.set(price);
    }
}
