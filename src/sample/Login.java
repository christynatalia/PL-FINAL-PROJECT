package sample;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.util.Optional;

public class Login {
    public Button BtnLogin;
    public TextField TFUsername ;
    public PasswordField TFPassword;

    public void Login(){
        if (TFUsername.getText().equals("admin") && TFPassword.getText().equals("admin"))
        {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("INFORMATION");
            al.setHeaderText("Success!");
            al.setContentText("Successfully login!");
            Optional<ButtonType> rs = al.showAndWait();
            ButtonType button = rs.orElse(ButtonType.OK);
            if (button == ButtonType.OK) {
                Stage stage = (Stage) BtnLogin.getScene().getWindow();
                stage.close();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                    Parent root1 = fxmlLoader.<Parent>load();
                    Stage st = new Stage();
                    st.setScene(new Scene(root1));
                    st.setTitle("Inventory Database Program");
                    st.show();


                } catch (Exception e) {
                    System.out.println("Can't open");
                }
            }
        }
        else
        {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("WARNING!");
            a.setContentText("Incorrect Login Information");
            a.show();

        }
    }


}
