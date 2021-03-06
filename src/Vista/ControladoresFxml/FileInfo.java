package Vista.ControladoresFxml;

import Controlador.Controller;
import Vista.DragWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import n_ary_tree.File;

public class FileInfo implements Initializable, DragWindow {

    @FXML
    private Pane pane;

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
    private Label fileNameTitle;
    private final Controller controller = Controller.getInstance();

    @FXML
    public void close(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stageActual = (Stage) source.getScene().getWindow();
        stageActual.close();
    }

    private void fillSpaces(){

        File file = (File) controller.getMyFileSystem().getNode(controller.getPath(controller.getCurrentItem())).getValue();

        ArrayList<String> properties = controller.getProperties(file);
        fileNameTitle.setText(properties.get(0));
        fileExt.setText(properties.get(1));
        creationDate.setText(properties.get(2));
        modificationDate.setText(properties.get(3));
        fileName.setText(properties.get(0));
        size.setText(properties.get(4));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillSpaces();
        onDraggedScene(pane);
    }
}
