package sample;

import Connectivity.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InventoryData implements Initializable {

    Connect connectt = new Connect();
    public Button btnadd, BtnBack, BtnQuit1, BtnAddQty, Btndelete,loadData1;
    public TextField tfnameid, tfname, tfqty, tfprice;
    public TableView tvResult;
    public ChoiceBox<String> choicebox1;
    public Label labelcount;
    ObservableList typelist = FXCollections.observableArrayList("Inventory", "Sales");
    ObservableList<Things> data = FXCollections.observableArrayList();
    ObservableList<SalesThings>SalesData = FXCollections.observableArrayList();

    public void addData() {
        int nameid = Integer.parseInt(tfnameid.getText());
        String name = tfname.getText();
        int qty = Integer.parseInt(tfqty.getText());
        int price = Integer.parseInt(tfprice.getText());

        //check if the Nameid already exist in database or not
        String mysql = "SELECT * FROM things_table WHERE Nameid=?";
        PreparedStatement pr1 = connectt.Prepstatement(mysql);
        try{
            pr1.setInt(1,nameid);
            ResultSet rs1 = pr1.executeQuery();
            if(rs1.next())
            {
                //if already exist, it will return an error messages.
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setTitle("WARNING!");
                al.setContentText("Data already exist!");
                al.show();
            }
            else
            {
                //else, it will create the new data.
                String sql = "INSERT INTO things_table(Nameid, Name, Quantity, Price)" + "VALUES(?,?,?,?)";
                PreparedStatement prepstat = connectt.Prepstatement(sql);
                try {
                    prepstat.setInt(1, nameid);
                    prepstat.setString(2, name);
                    prepstat.setInt(3, qty);
                    prepstat.setInt(4, price);
                    prepstat.executeUpdate();
                    refreshTable();
                    autoErase();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch(SQLException es)
        {
            System.out.println(es.getMessage());
        }
    }

    public void autoErase() {
        tfname.setText("");
        tfnameid.setText("");
        tfqty.setText("");
        tfprice.setText("");
    }


    public void BackButton() throws IOException {
        Stage stage = (Stage) BtnBack.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root1 = fxmlLoader.<Parent>load();
        Stage st = new Stage();
        st.setScene(new Scene(root1));
        st.setTitle("Inventory Database Program");
        st.show();

    }


    public void ExitButton() {
        Alert al = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit this program? ):");
        Optional<ButtonType> rs = al.showAndWait();
        ButtonType button = rs.orElse(ButtonType.OK);
        if (button == ButtonType.OK) {
            Stage stage = (Stage) BtnQuit1.getScene().getWindow();
            stage.close();
        }
        else
        {
            System.out.println("cancelled");
        }
    }


    public void UpdateData() {
        int nameid = Integer.parseInt(tfnameid.getText());
        int price = Integer.parseInt(tfprice.getText());

        String mysql = "SELECT * FROM things_table WHERE Nameid=?";
        PreparedStatement p1 = connectt.Prepstatement(mysql);
        try {
            p1.setInt(1,nameid);
            ResultSet rs = p1.executeQuery();
            if (rs.next()) {
                String sql = "UPDATE things_table SET Price=? WHERE Nameid=?";
                PreparedStatement prepstatt = connectt.Prepstatement(sql);
                try {

                    prepstatt.setInt(1, price);
                    prepstatt.setInt(2, nameid);
                    prepstatt.executeUpdate();
                    refreshTable();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
            else
            {
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setTitle("WARNING!");
                al.setContentText("Data doesn't exist!");
                al.show();
            }
        }
        catch(SQLException es)
        {
            System.out.println(es.getMessage());
        }
    }


    public void refreshTable() {
        data.clear();
        String sql = "SELECT * FROM things_table";
        PreparedStatement prepstatt = connectt.Prepstatement(sql);
        try {

            ResultSet rs = prepstatt.executeQuery();
            while (rs.next()) {
                data.add(new Things(rs.getInt("Nameid"), rs.getString("Name"), rs.getInt("Quantity"),
                        rs.getInt("Price")));
            }
            sumdata();
            autoErase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void RefreshSalesTable(){
        SalesData.clear();
        String sql = "SElect * FROM Sales_table";
        PreparedStatement prepstat = connectt.Prepstatement(sql);
        try{
            ResultSet rs = prepstat.executeQuery();
            while(rs.next()){
                SalesData.add(new SalesThings(rs.getInt("SProdID"),rs.getString("SProdName"),rs.getInt("SProdQty"),rs.getInt("Price")));
            }
            sumdata();
            autoErase();
        }
        catch(SQLException es)
        {
            System.out.println(es.getMessage());
        }
    }

    public void addQuantity() {
        int nameid = Integer.parseInt(tfnameid.getText());
        String name = tfname.getText();
        int qty = Integer.parseInt(tfqty.getText());
        int updateQty = 0;

        if (qty > 0) {
            String sql = "SELECT * FROM things_table WHERE Nameid=? AND Name=?";
            PreparedStatement prepstatt = connectt.Prepstatement(sql);
            try {
                prepstatt.setInt(1, nameid);
                prepstatt.setString(2, name);
                ResultSet rs = prepstatt.executeQuery();

                //to check if the data is exist or not.
                if (rs.next()) {
                    updateQty = qty + rs.getInt("Quantity");
                    String mysql = "UPDATE things_table SET Quantity=? WHERE Nameid=?";
                    PreparedStatement prepstat = connectt.Prepstatement(mysql);
                    try {
                        prepstat.setInt(1, updateQty);
                        prepstat.setInt(2, nameid);
                        prepstat.executeUpdate();
                        refreshTable();
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                } else {
                    alertwarning();
                }
            } catch (SQLException e) {
                e.getMessage();

            }
        } else {
            Alert al = new Alert(Alert.AlertType.WARNING);
            al.setTitle("WARNING!");
            al.setContentText("Quantity must be bigger than 0");
            al.show();
            autoErase();
        }
    }

    public void alertwarning(){
        Alert al = new Alert(Alert.AlertType.WARNING);
        al.setTitle("WARNING!");
        al.setContentText("THERE IS NO DATA FOR THOSE NAMEID!!!");
        al.show();
        autoErase();

    }


    public void DeleteData() {
        int nameid = Integer.parseInt(tfnameid.getText());
        String name = tfname.getText();
        Alert al = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + name);
        Optional<ButtonType> rs = al.showAndWait();
        ButtonType button = rs.orElse(ButtonType.OK);
        if (button == ButtonType.OK) {
            String sql = "SELECT * FROM things_table WHERE Nameid=? AND Name=?";
            PreparedStatement pr1 = connectt.Prepstatement(sql);
            try {
                pr1.setInt(1, nameid);
                pr1.setString(2, name);
                ResultSet rs1 = pr1.executeQuery();
                if (rs1.next()) {
                    String sql1 = "DELETE FROM things_table WHERE Nameid=?";
                    PreparedStatement prepstat = connectt.Prepstatement(sql1);
                    try {
                        prepstat.setInt(1, nameid);
                        prepstat.executeUpdate();
                        refreshTable();
                    } catch (SQLException e) {
                        e.getMessage();
                    }
                } else {
                    System.out.println("There is no data");
                    alertwarning();

                }
            } catch (SQLException eq) {
                System.out.println(eq.getMessage());
            }
        }
    }



    public void sumdata(){
        int sumdata1 = 0;
        String choiceboxValue = choicebox1.getValue();
        if (choiceboxValue == "Inventory") {
            String sql1 = "SELECT * FROM things_table";
            PreparedStatement prepstat = connectt.Prepstatement(sql1);
            try {
                ResultSet rs = prepstat.executeQuery();
                while (rs.next()) {
                    sumdata1 = sumdata1 + rs.getInt("Quantity");
                }
            } catch (SQLException es) {
                System.out.println(es.getMessage());
            }
            labelcount.setText(String.valueOf(sumdata1));
        }
        else if (choiceboxValue == "Sales")
        {
            String sql1 = "SELECT * FROM Sales_table";
            PreparedStatement prepstat = connectt.Prepstatement(sql1);
            try {
                ResultSet rs = prepstat.executeQuery();
                while (rs.next()) {
                    sumdata1 = sumdata1 + rs.getInt("SProdQty");
                }
            } catch (SQLException es) {
                System.out.println(es.getMessage());
            }
            labelcount.setText(String.valueOf(sumdata1));

        }
        }


    public void LoadBtn(){
        int sumdata1 = 0;
        String choiceboxValue = choicebox1.getValue();
        if (choiceboxValue == "Inventory") {
            refreshTable();
            TableColumn NameIDCol = new TableColumn("Product ID");
            NameIDCol.setMinWidth(50);
            NameIDCol.setCellValueFactory(
                    new PropertyValueFactory<Things, Integer>("Nameid"));

            TableColumn NameCol = new TableColumn("Product Name");
            NameCol.setMinWidth(100);
            NameCol.setCellValueFactory(
                    new PropertyValueFactory<Things, String>("Name"));

            TableColumn QtyCol = new TableColumn("Quantity");
            QtyCol.setMinWidth(100);
            QtyCol.setCellValueFactory(
                    new PropertyValueFactory<Things, Integer>("Quantity"));

            TableColumn PriceCol = new TableColumn("Price");
            PriceCol.setMinWidth(50);
            PriceCol.setCellValueFactory(
                    new PropertyValueFactory<Things, Integer>("Price"));


            tvResult.getColumns().setAll(NameIDCol, NameCol, QtyCol, PriceCol);
            tvResult.setItems(data);

            String sql1 = "SELECT * FROM things_table";
            PreparedStatement prepstat = connectt.Prepstatement(sql1);
            try {
                ResultSet rs = prepstat.executeQuery();
                while (rs.next()) {
                    sumdata1 = sumdata1 + rs.getInt("Quantity");
                }
            } catch (SQLException es) {
                System.out.println(es.getMessage());
            }
            labelcount.setText(String.valueOf(sumdata1));



        } else if (choiceboxValue=="Sales"){
            RefreshSalesTable();
            TableColumn NameIDCol = new TableColumn("Product ID");
            NameIDCol.setMinWidth(50);
            NameIDCol.setCellValueFactory(
                    new PropertyValueFactory<Things, Integer>("SProdID"));

            TableColumn NameCol = new TableColumn("Product Name");
            NameCol.setMinWidth(100);
            NameCol.setCellValueFactory(
                    new PropertyValueFactory<Things, String>("SProdName"));

            TableColumn QtyCol = new TableColumn("Quantity");
            QtyCol.setMinWidth(100);
            QtyCol.setCellValueFactory(
                    new PropertyValueFactory<Things, Integer>("SProdQty"));

            TableColumn PriceCol = new TableColumn("Price");
            PriceCol.setMinWidth(50);
            PriceCol.setCellValueFactory(
                    new PropertyValueFactory<Things, Integer>("Price"));

            tvResult.getColumns().setAll(NameIDCol, NameCol, QtyCol,PriceCol);
            tvResult.setItems(SalesData);
            String sql1 = "SELECT * FROM Sales_table";
            PreparedStatement prepstat = connectt.Prepstatement(sql1);
            try {
                ResultSet rs = prepstat.executeQuery();
                while (rs.next()) {
                    sumdata1 = sumdata1 + rs.getInt("SProdQty");
                }
            } catch (SQLException es) {
                System.out.println(es.getMessage());
            }
            labelcount.setText(String.valueOf(sumdata1));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choicebox1.setItems(typelist);
    }
}