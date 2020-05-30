package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Menu {
    public Button BtnAddData,BtnDelete;

    public void ButtonAddData(){
        Stage stage = (Stage) BtnAddData.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("InventoryData.fxml"));
            Parent root1 = fxmlLoader.<Parent>load();
            Stage st = new Stage();
            st.setScene(new Scene(root1));
            st.show();

        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    public void BtnSaddData(){
        Stage stage = (Stage) BtnAddData.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeleteData.fxml"));
            Parent root1 = fxmlLoader.<Parent>load();
            Stage st = new Stage();
            st.setScene(new Scene(root1));
            st.show();

        }

        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}
