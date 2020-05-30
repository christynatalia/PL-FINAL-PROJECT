package sample;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Login {
    public Button BtnLogin;
    public TextField TFUsername ;
    public PasswordField TFPassword;

    public void Login(){
        if (TFUsername.getText().equals("admin") && TFPassword.getText().equals("admin"))
        {
            Stage stage = (Stage) BtnLogin.getScene().getWindow();
            stage.close();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                Parent root1 = fxmlLoader.<Parent>load();
                Stage st = new Stage();
                st.setScene(new Scene(root1));
                st.show();


            }
            catch (Exception e)
            {
                System.out.println("Can't open");
            }
            System.out.println("Success");
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
