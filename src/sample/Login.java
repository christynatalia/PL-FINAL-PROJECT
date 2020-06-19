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
            // if the username and password that user input is valid, it will show some information
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.setTitle("INFORMATION");
            al.setHeaderText("Success!");
            al.setContentText("Successfully login!");
            Optional<ButtonType> rs = al.showAndWait();
            ButtonType button = rs.orElse(ButtonType.OK);
            if (button == ButtonType.OK) {
                // if the user clicked the ok button, it will close this login scene
                Stage stage = (Stage) BtnLogin.getScene().getWindow();
                stage.close();
                try {
                    // go to another stage which is Menu.fxml
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
            // if username and password is not valid, it will show an alert message
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("WARNING!");
            a.setContentText("Incorrect Login Information");
            a.show();

        }
    }


}
