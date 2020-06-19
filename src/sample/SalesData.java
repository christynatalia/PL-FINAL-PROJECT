package sample;

import Connectivity.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalesData implements Initializable {
    Connect connectt = new Connect();
    public Button BtnSAdd, BtnSBack, BtnSExit, putinv, Btnload;
    public TextField TFDProdID, TFDProdName, TFDQty;
    public ChoiceBox<String> SalesChoiceTable;
    public TableView tvResult;
    ObservableList typelist = FXCollections.observableArrayList("Inventory", "Sales");
    ObservableList<Things> data = FXCollections.observableArrayList();
    ObservableList<SalesThings> SalesData = FXCollections.observableArrayList();

    public void AddToSales() {
        //In this part, we're going to move some qty to sales from inventory
        int prodnameid = Integer.parseInt(TFDProdID.getText());
        String prodname = TFDProdName.getText();
        int prodqty = Integer.parseInt(TFDQty.getText());
        int updateQty = 0;
        int SUpdateQty = 0;

        //check whether the data is exist or not in things_table
        String mysql = "SELECT * FROM things_table WHERE Nameid=? AND Name=?";
        PreparedStatement prepstat1 = connectt.Prepstatement(mysql);
        try {
            prepstat1.setInt(1, prodnameid);
            prepstat1.setString(2, prodname);
            ResultSet rs1 = prepstat1.executeQuery();
            //check the validity and if the data exist on inventory, it will check on sales table
            if (rs1.next() && rs1.getInt("Quantity") > prodqty) {
                System.out.println("Data valid ( exist in things_table)");
                String mysql2 = "SELECT * FROM Sales_table WHERE SProdID=? AND SProdName=?";
                PreparedStatement prepstat2 = connectt.Prepstatement(mysql2);
                try {
                    prepstat2.setInt(1, prodnameid);
                    prepstat2.setString(2, prodname);
                    ResultSet rs2 = prepstat2.executeQuery();
                    if (rs2.next()) {
                        if (rs1.getInt("Quantity") >= prodqty) {
                            //calculate the qty for both database
                            updateQty = rs1.getInt("Quantity") - prodqty;
                            SUpdateQty = rs2.getInt("SProdQty") + prodqty;
                            System.out.println(updateQty);
                            //update id
                            String mysql3 = "UPDATE things_table SET Quantity=? WHERE Nameid=? AND Name=?";
                            PreparedStatement prepstat3 = connectt.Prepstatement(mysql3);
                            try {
                                prepstat3.setInt(1, updateQty);
                                prepstat3.setInt(2, prodnameid);
                                prepstat3.setString(3, prodname);
                                prepstat3.executeUpdate();
                                //update it
                                String mysql4 = "UPDATE Sales_table SET SProdQty=? WHERE SProdID=? AND SProdName=?";
                                PreparedStatement prepstat4 = connectt.Prepstatement(mysql4);
                                try {
                                    prepstat4.setInt(1, SUpdateQty);
                                    prepstat4.setInt(2, prodnameid);
                                    prepstat4.setString(3, prodname);
                                    prepstat4.executeUpdate();
                                    setPricethings();
                                    RefreshSalesTable();
                                    refreshTable();
                                    autoErase();
                                    AlertSuccess();
                                } catch (SQLException e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        } else {
                            //if the data doesn't valid, alert message will be appear
                            AlertWarning();
                            autoErase();
                        }
                    }
                    else {
                        //if the data doesn't exist in sales data, it will create the new one
                        //For sales data, i've decided to not add new data for it.
                        NewSalesData();
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                AlertWarning();
                autoErase();
            }
        }
        catch(SQLException e )
        {
            System.out.println(e.getMessage());
        }

    }





    public void NewSalesData()
    {
        //this is the function to create the new sales data
        int prodnameid = Integer.parseInt(TFDProdID.getText());
        String prodname = TFDProdName.getText();
        int prodqty = Integer.parseInt(TFDQty.getText());

        String mysql1 = "INSERT INTO Sales_table(SProdID, SProdName, SprodQty)" + "VALUES(?,?,?)";
        PreparedStatement prepstat = connectt.Prepstatement(mysql1);
        try
        {
            prepstat.setInt(1,prodnameid);
            prepstat.setString(2,prodname);
            prepstat.setInt(3,prodqty);
            prepstat.executeUpdate();
            setPricethings();
            RefreshSalesTable();
            autoErase();
            AlertSuccess();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }



    public void BackButton() throws IOException {
        // to go back to the menu and close this page
        Stage stage = (Stage) BtnSBack.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root1 = fxmlLoader.<Parent>load();
        Stage st = new Stage();
        st.setScene(new Scene(root1));
        st.setTitle("Inventory Database Program");
        st.show();

    }

    public void setPricethings(){
        //inherit the price from the inventory data.
        int prodnameid = Integer.parseInt(TFDProdID.getText());
        String prodname = TFDProdName.getText();
        int pricecopy = 0;
        String mysql = "SELECT * FROM things_table WHERE Nameid =? AND Name=?";
        PreparedStatement pr1 = connectt.Prepstatement(mysql);
        autoErase();
        try{
            pr1.setInt(1,prodnameid);
            pr1.setString(2,prodname);
            ResultSet rs = pr1.executeQuery();
            rs.next();
            pricecopy = rs.getInt("Price");
            String mysql2 = "UPDATE sales_table SET Price=? WHERE SProdID=? AND SProdName=?";
            PreparedStatement pr2 = connectt.Prepstatement(mysql2);
            try{
                pr2.setInt(1,pricecopy);
                pr2.setInt(2,prodnameid);
                pr2.setString(3,prodname);
                pr2.executeUpdate();
                RefreshSalesTable();
            }
            catch(SQLException e)
            {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void AlertWarning(){
        //alert if the data doesn't exist in the database
        Alert al = new Alert(Alert.AlertType.WARNING);
        al.setTitle("Error");
        al.setContentText("Data doesn't exist in the database.");
        al.show();
    }

    public void AlertSuccess(){
        //alert if the data doesn't exist in the database
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.setTitle("Success");
        al.setContentText("Data successfully changed");
        al.show();
    }
    public void autoErase() {
        //erase the textfield value everytime the user clicked on the button.
        TFDProdID.setText("");
        TFDProdName.setText("");
        TFDQty.setText("");
    }

    public void PutInventory(){
        //this function is created in case you make a mistake and want to put back some item to the inventory.
        int prodnameid = Integer.parseInt(TFDProdID.getText());
        String prodname = TFDProdName.getText();
        int prodqty = Integer.parseInt(TFDQty.getText());
        int updateqtySales = 0;


        String mysql1 = "SELECT * FROM Sales_table WHERE SProdID=? AND SProdName=?";
        //check the data does it exist in Sales_table or not
        PreparedStatement pr1 = connectt.Prepstatement(mysql1);
        try{
            autoErase();
            pr1.setInt(1,prodnameid);
            pr1.setString(2,prodname);
            ResultSet rs = pr1.executeQuery();
            if(rs.next()&& prodqty <= rs.getInt("SProdQty")){
                //if the data valid in sales table:
                updateqtySales = rs.getInt("SprodQty") - prodqty;
                String mysql2 = "UPDATE Sales_table SET SProdQty=? WHERE SprodID=? AND SProdName=?";
                PreparedStatement pr2 = connectt.Prepstatement(mysql2);
                try{
                    pr2.setInt(1,updateqtySales);
                    pr2.setInt(2,prodnameid);
                    pr2.setString(3,prodname);
                    pr2.executeUpdate();
                    RefreshSalesTable();
                    refreshTable();
                    //CheckInventory();
                    AlertSuccess();
                    autoErase();
                }
                catch(SQLException ess)
                {
                    System.out.println(ess.getMessage());
                }

            }
            else
            {
                //
                AlertWarning();
            }
        }
        catch(SQLException es)
        {
            System.out.println(es.getMessage());
        }

    }

    /*public void CheckInventory(){
        //this function to check whether the data exist or not in inventory
        int prodnameid = Integer.parseInt(TFDProdID.getText());
        String prodname = TFDProdName.getText();
        int prodqty = Integer.parseInt(TFDQty.getText());
        int updateqtyInv = 0;

        String mysql1 = "SELECT * FROM things_table WHERE Nameid=? AND Name=?";
        PreparedStatement pr1 = connectt.Prepstatement(mysql1);
        try{
            pr1.setInt(1,prodnameid);
            pr1.setString(2,prodname);
            ResultSet rs = pr1.executeQuery();
            rs.next();
            updateqtyInv = rs.getInt("Quantity") + prodqty;
            String mysql2 = "UPDATE things_table SET Quantity=? WHERE Nameid=? AND Name=?";
            PreparedStatement pr2 = connectt.Prepstatement(mysql2);
            try{
                pr2.setInt(1,updateqtyInv);
                pr2.setInt(2,prodnameid);
                pr2.setString(3,prodname);
                pr2.executeUpdate();
                refreshTable();
            }
            catch(SQLException es)
            {
                System.out.println(es.getMessage());
            }
        }
        catch(SQLException ES){
            System.out.println(ES.getMessage());
        }

    }
    */



    public void ExitButton() {
        // to exit the program
        Alert al = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to exit this program? ):");
        Optional<ButtonType> rs = al.showAndWait();
        ButtonType button = rs.orElse(ButtonType.OK);
        if (button == ButtonType.OK) {
            Stage stage = (Stage) BtnSExit.getScene().getWindow();
            stage.close();
        }
        else
        {
            System.out.println("cancelled");
        }
    }

    public void refreshTable() {
        //to refresh inventory table
        data.clear();
        String sql = "SELECT * FROM things_table";
        PreparedStatement prepstatt = connectt.Prepstatement(sql);
        try {

            ResultSet rs = prepstatt.executeQuery();
            while (rs.next()) {
                data.add(new Things(rs.getInt("Nameid"), rs.getString("Name"), rs.getInt("Quantity"),
                        rs.getInt("Price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void RefreshSalesTable(){
        //to refresh sales table everytime you update it
        SalesData.clear();
        String sql = "SElect * FROM Sales_table";
        PreparedStatement prepstat = connectt.Prepstatement(sql);
        try{
            ResultSet rs = prepstat.executeQuery();
            while(rs.next()){
                SalesData.add(new SalesThings(rs.getInt("SProdID"),rs.getString("SProdName"),rs.getInt("SProdQty"),rs.getInt("Price")));
            }
        }
        catch(SQLException es)
        {
            System.out.println(es.getMessage());
        }
    }

    public void LoadBtn(){
        //to load the table view with user's choice
        String choiceboxValue = SalesChoiceTable.getValue();
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

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SalesChoiceTable.setItems(typelist);

    }
}
