package Vista.ControladoresFxml;

import Controlador.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Create {

    @FXML
    private TextField sectorsAmount;

    @FXML
    private TextField sectorSize;

    @FXML
    private Label error;
    
    private final Controller controller = Controller.getInstance();

    @FXML
    void create(ActionEvent event) throws IOException {
        if (!sectorsAmount.getText().isEmpty() || !sectorSize.getText().isEmpty()) {
            controller.createVirtualDisk(Integer.parseInt(sectorsAmount.getText()), Integer.parseInt(sectorSize.getText()));

            Node source = (Node) event.getSource();
            Stage stageActual = (Stage) source.getScene().getWindow();
            stageActual.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLS/MainWindow.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            error.setVisible(true);
        }
    }
}
