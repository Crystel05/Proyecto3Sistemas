package Vista.ControladoresFxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    @FXML
    private TextField buscarTF;

    @FXML
    private TextArea filePreviewTA;

    @FXML
    private Text fileName;

    @FXML
    private ListView<String> filesListSearch;

    @FXML
    private Pane paneFilePreview;

    @FXML
    private Pane paneFileList;

    @FXML
    private Pane placeHolder;

    @FXML
    private TreeView<String> treeView;

    @FXML
    void search(MouseEvent event) {
        placeHolder.setVisible(false);
        paneFilePreview.setVisible(false);
        paneFileList.setVisible(true);
    }

    @FXML
    public void selectItem(){
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            if (item.getChildren().isEmpty()){
                placeHolder.setVisible(false);
                paneFileList.setVisible(false);
                paneFilePreview.setVisible(true);
                filePreviewTA.setText(getContent(item.getValue()));
                fileName.setText(item.getValue());
            }
        }
    }

    private String getContent(String fileName){
        return "Esto es una prueba";
    }

    @FXML
    private void close(MouseEvent event){
        System.exit(1);
    }

    private void llenarArbol(){
        TreeItem<String> root = new TreeItem<>("My File System");
        for (int i = 0; i < 10; i++){ //cambiar esto después por los datos reales
            TreeItem<String> directory = new TreeItem<>("Directory " + i);
            for (int j = 0; j < 3; j++){
                TreeItem<String> file = new TreeItem<>("File " + j);
                directory.getChildren().add(file);
            }
            root.getChildren().add(directory);
        }
        treeView.setRoot(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        llenarArbol();
    }

}
