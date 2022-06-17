package Vista.ControladoresFxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FileInfo implements Initializable {
    @FXML
    private Label fileExt;

    @FXML
    private Label creationDate;

    @FXML
    private Label modificationDate;

    @FXML
    private Label fileName;

    @FXML
    private Label size;

    @FXML
    public void close(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stageActual = (Stage) source.getScene().getWindow();
        stageActual.close();
    }

    private void fillSpaces(){
        //llamar función que devuelve los datos
        fileExt.setText(""); //cambiar por los datos
        creationDate.setText("");
        modificationDate.setText("");
        fileName.setText("");
        size.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillSpaces();
    }
}
