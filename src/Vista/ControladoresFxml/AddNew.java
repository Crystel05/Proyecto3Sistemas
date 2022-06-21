package Vista.ControladoresFxml;

import Controlador.Controller;
import Vista.DragWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import n_ary_tree.Folder;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AddNew implements Initializable, DragWindow {

    @FXML
    private Pane paneFile;

    @FXML
    private Pane paneDir;

    @FXML
    private TextArea fileData;

    @FXML
    private TextField fileName;

    @FXML
    private TextField directoryName;

    @FXML
    private Label errorAddFile;

    @FXML
    private Label errorAddDir;

    private final Controller controller = Controller.getInstance();

    @FXML
    public void createFile(ActionEvent event) throws FileNotFoundException {
        if (!fileName.getText().isEmpty() && !fileData.getText().isEmpty()) {
            if (controller.getCurrentItem() != null){
                ArrayList<String> path = controller.getPath(controller.getCurrentItem());
                n_ary_tree.Node node = controller.getMyFileSystem().getNode(path);
                if (node.getValue().getClass().equals(Folder.class)){
                    controller.insertFile(fileName.getText(), fileData.getText(), path);
                    controller.fillTree();
                    Node source = (Node) event.getSource();
                    Stage stageActual = (Stage) source.getScene().getWindow();
                    stageActual.close();
                }else {
                    errorAddFile.setText("Escoja un directorio válido");
                    errorAddFile.setVisible(true);
                }
            }else{
                errorAddFile.setText("Escoja un directorio");
                errorAddFile.setVisible(true);
            }

        }else{
            errorAddFile.setText("Indique el nombre y el contenido del archivo");
            errorAddFile.setVisible(true);
        }
    }
    @FXML
    public void createDirectory(ActionEvent event) throws FileNotFoundException {
        if (!directoryName.getText().isEmpty()) {
            System.out.println(controller.getCurrentItem() != null);
            if (controller.getCurrentItem() != null) {
                ArrayList<String> path = controller.getPath(controller.getCurrentItem());
                n_ary_tree.Node node = controller.getMyFileSystem().getNode(path);
                if (node.getValue().getClass().equals(Folder.class)) {
                    controller.insertDirectory(directoryName.getText(), path);
                    Node source = (Node) event.getSource();
                    Stage stageActual = (Stage) source.getScene().getWindow();
                    stageActual.close();
                    controller.fillTree();
                }else{
                    errorAddDir.setText("Escojoa un directorio válido");
                    errorAddDir.setVisible(true);
                }
            }else{
                errorAddDir.setText("Escoja un directorio");
                errorAddDir.setVisible(true);
            }
        }else{
            errorAddDir.setText("Indique el nombre del directorio");
            errorAddDir.setVisible(true);
        }
    }

    @FXML
    public void close(MouseEvent event){
        Node source = (Node) event.getSource();
        Stage stageActual = (Stage) source.getScene().getWindow();
        stageActual.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            onDraggedScene(paneFile);
        }catch (NullPointerException ignore){
            onDraggedScene(paneDir);
        }
    }
}
