package Vista.ControladoresFxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    @FXML
    private TreeView<String> treeView;

    @FXML
    public void selectItem(){
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        System.out.println(item.getValue());
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
