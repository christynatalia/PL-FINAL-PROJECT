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
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class InventoryData implements Initializable {

    Connect connectt = new Connect();
    public Button btnadd, BtnBack, BtnQuit1, BtnAddQty, Btndelete;
    public TextField tfnameid , tfname , tfqty , tfprice ;
    public TableView tvResult;
    ObservableList<Things> data = FXCollections.observableArrayList();



    public void addData() {
        int nameid = Integer.parseInt(tfnameid.getText());
        String name = tfname.getText();
        int qty = Integer.parseInt(tfqty.getText());
        int price = Integer.parseInt(tfprice.getText());

        String sql = "INSERT INTO things_table(Nameid, Name, Quantity, Price)" + "VALUES(?,?,?,?)";
        PreparedStatement prepstat = connectt.getPrepstat(sql);
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

    public void autoErase(){
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
        st.show();

    }


    public void ExitButton(){
        Stage stage = (Stage) BtnQuit1.getScene().getWindow();
        stage.close();
    }


    public void UpdateData(){
        int nameid = Integer.parseInt(tfnameid.getText());
        String name = tfname.getText();
        int qty = Integer.parseInt(tfqty.getText());
        int price = Integer.parseInt(tfprice.getText());

        String sql = "UPDATE things_table SET Price=? WHERE Nameid=?" ;
        PreparedStatement prepstatt = connectt.getPrepstat(sql);
        try {

            prepstatt.setInt(1,price);
            prepstatt.setInt(2, nameid);
            prepstatt.executeUpdate();
            refreshTable();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void refreshTable(){
        data.clear();
        String sql = "SELECT * FROM things_table";
        PreparedStatement prepstatt = connectt.getPrepstat(sql);
        try {

            ResultSet rs = prepstatt.executeQuery();
            while (rs.next()){
                data.add(new Things(rs.getInt("Nameid"),rs.getString("Name"),rs.getInt("Quantity"),
                        rs.getInt("Price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuantity() {
        int nameid = Integer.parseInt(tfnameid.getText());
        String name = tfname.getText();
        int qty = Integer.parseInt(tfqty.getText());
        int updateQty = 0;



        String sql = "SELECT * FROM things_table WHERE Nameid=? AND Name=?";
        PreparedStatement prepstatt = connectt.getPrepstat(sql);
        try
        {
            prepstatt.setInt(1, nameid);
            prepstatt.setString(2,name);
            ResultSet rs = prepstatt.executeQuery();

            //to check if the data is exist or not.
            if(rs.next())
            {
                updateQty = qty + rs.getInt("Quantity");
                String mysql = "UPDATE things_table SET Quantity=? WHERE Nameid=?";
                PreparedStatement prepstat = connectt.getPrepstat(mysql);
                try {
                    prepstat.setInt(1,updateQty);
                    prepstat.setInt(2,nameid);
                    prepstat.executeUpdate();
                    refreshTable();
                } catch (SQLException e) {
                    e.getMessage();
                }
            }

            else
            {
                Alert al = new Alert(Alert.AlertType.WARNING);
                al.setTitle("WARNING!");
                al.setContentText("THERE IS NO DATA FOR THOSE NAMEID!!!");
                al.show();
                autoErase();
            }
        }
        catch (SQLException e) {
            e.getMessage();

        }


    }

    public void DeleteData(){
        int nameid = Integer.parseInt(tfnameid.getText());
        String sql = "DELETE FROM things_table WHERE Nameid=?";
        PreparedStatement prepstat = connectt.getPrepstat(sql);
        try{
            prepstat.setInt(1,nameid);
            prepstat.executeUpdate();
            refreshTable();
        }
        catch(SQLException e){
            e.getMessage();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        refreshTable();
        TableColumn NameIDCol = new TableColumn("Nameid");
        NameIDCol.setMinWidth(50);
        NameIDCol.setCellValueFactory(
                new PropertyValueFactory<Things, Integer>("Nameid"));

        TableColumn NameCol = new TableColumn("Name");
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


        tvResult.getColumns().setAll(NameIDCol,NameCol,QtyCol,PriceCol);
        tvResult.setItems(data);
    }
}