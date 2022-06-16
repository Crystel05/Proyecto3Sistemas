package Vista.ControladoresFxml;

import com.sun.glass.ui.Clipboard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
    private Pane movePane;

    @FXML
    private Pane renamePane;

    @FXML
    private Pane copyPane;

    @FXML
    private TreeView<String> treeView;

    @FXML
    private TextField diskSize;

    @FXML
    private TextField sectorSize;

    @FXML
    private Button guardarCambios;

    private List<Pane> panesList = new ArrayList<>();

    @FXML
    public void move(MouseEvent event){
       seePane(movePane);
    }

    @FXML
    public void confirmMove(ActionEvent event){
        renamePane.setVisible(true);
    }

    @FXML
    public void cancel(ActionEvent event){
        renamePane.setVisible(false);
    }
    @FXML
    void editFile(MouseEvent event){
        filePreviewTA.setEditable(true);
        guardarCambios.setVisible(true);
    }

    @FXML
    void save(ActionEvent event){
        guardarCambios.setVisible(false);
        filePreviewTA.setEditable(false);
    }

    @FXML
    public void remove(MouseEvent event){
        treeView.getSelectionModel().getSelectedItem();
    }

    @FXML
    public void copy(MouseEvent event){
        seePane(copyPane);
    }

    @FXML
    void search(MouseEvent event) {
        seePane(paneFileList);
        List<String> foundFiles = new ArrayList<>();
        foundFiles.add("HOLA");
        foundFiles.add("HOLA");
        foundFiles.add("HOLA");
        foundFiles.add("HOLA");
        fillSearch(foundFiles);
    }

    private void fillSearch(List<String> foundFiles) {
        for (String fileName : foundFiles){
            String item = "-> " + fileName;
            //item = file.getName() + "\t\t" file.getUbicacion()
            filesListSearch.getItems().add(item);
        }
    }

    @FXML
    public void seeProperties(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLS/FileInfo.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void selectItem(){
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            if (item.getChildren().isEmpty()){
                seePane(paneFilePreview);
                filePreviewTA.setText(getContent(item.getValue()));
                fileName.setText(item.getValue());
            }
        }
    }

    @FXML
    public void createFile(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLS/AddFile.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    public void createDirectory(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXMLS/AddDirectory.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
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
        fillPanesList();
        llenarArbol();
    }

    private void fillPanesList() {
        panesList.add(placeHolder);
        panesList.add(paneFileList);
        panesList.add(paneFilePreview);
        panesList.add(movePane);
        panesList.add(copyPane);
    }

    private void seePane(Pane pane){
        for (Pane p : panesList){
            if (!p.equals(pane)){
                p.setVisible(false);
            }
        }
        pane.setVisible(true);
    }
}
