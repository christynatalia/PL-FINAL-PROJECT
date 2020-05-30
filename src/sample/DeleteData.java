package sample;

import Connectivity.Connect;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteData {
    Connect connectt = new Connect();
    public Button BtnSAdd,BtnSBack, BtnSExit;
    public TextField TFDProdID, TFDProdName, TFDQty;

    public void CheckData(){
        int prodnameid = Integer.parseInt(TFDProdID.getText());
        String prodname = TFDProdName.getText();
        int prodqty = Integer.parseInt(TFDQty.getText());
        int updateQty = 0;
        int SUpdateQty = 0;


        String mysql = "SELECT * FROM things_table WHERE Nameid=? AND Name=?";
        PreparedStatement prepstat1 = connectt.getPrepstat(mysql);
        try
        {
            prepstat1.setInt(1,prodnameid);
            prepstat1.setString(2,prodname);
            ResultSet rs1 = prepstat1.executeQuery();
            if (rs1.next())
            {
                System.out.println("Data valid ( exist in things_table)");
                String mysql2 = "SELECT * FROM Sales_table WHERE SProdID=? AND SProdName=?";
                PreparedStatement prepstat2 = connectt.getPrepstat(mysql2);
                try
                {
                    prepstat2.setInt(1,prodnameid);
                    prepstat2.setString(2,prodname);
                    ResultSet rs2 = prepstat2.executeQuery();
                    if (rs2.next())
                    {
                        if (rs1.getInt("Quantity") >= prodqty)
                        {
                            updateQty = rs1.getInt("Quantity") - prodqty;
                            SUpdateQty = rs2.getInt("SProdQty") + prodqty;
                            System.out.println(updateQty);
                            String mysql3 = "UPDATE things_table SET Quantity=? WHERE Nameid=? AND Name=?";
                            PreparedStatement prepstat3 = connectt.getPrepstat(mysql3);
                            try
                            {
                                prepstat3.setInt(1, updateQty);
                                prepstat3.setInt(2, prodnameid);
                                prepstat3.setString(3, prodname);
                                prepstat3.executeUpdate();
                                String mysql4 = "UPDATE Sales_table SET SProdQty=? WHERE SProdID=? AND SProdName=?";
                                PreparedStatement prepstat4 = connectt.getPrepstat(mysql4);
                                try
                                {
                                    prepstat4.setInt(1,SUpdateQty);
                                    prepstat4.setInt(2,prodnameid);
                                    prepstat4.setString(3,prodname);
                                    prepstat4.executeUpdate();
                                }
                                catch(SQLException e)
                                {
                                    System.out.println(e.getMessage());
                                }
                            }
                            catch (SQLException e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                        else
                        {
                            System.out.println("Inventory tidak ada");
                        }
                    }

                    else
                    {
                        AddSalesData();
                    }
                }
                catch(SQLException e)
                {
                    System.out.println(e.getMessage());
                }
            }

            else
            {
                System.out.println("Data doesn't exists");
            }
        }
        catch(SQLException e )
        {
            System.out.println(e.getMessage());
        }

    }


    public void AddSalesData()
    {
        int prodnameid = Integer.parseInt(TFDProdID.getText());
        String prodname = TFDProdName.getText();
        int prodqty = Integer.parseInt(TFDQty.getText());

        String mysql1 = "INSERT INTO Sales_table(SProdID, SProdName, SprodQty)" + "VALUES(?,?,?)";
        PreparedStatement prepstat = connectt.getPrepstat(mysql1);
        try
        {
            prepstat.setInt(1,prodnameid);
            prepstat.setString(1,prodname);
            prepstat.setInt(3,prodqty);
            prepstat.executeUpdate();
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }


    public void BackButton() throws IOException {
        Stage stage = (Stage) BtnSBack.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root1 = fxmlLoader.<Parent>load();
        Stage st = new Stage();
        st.setScene(new Scene(root1));
        st.show();

    }


    public void ExitButton(){
        Stage stage = (Stage) BtnSExit.getScene().getWindow();
        stage.close();
    }
}
