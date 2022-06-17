package Vista.ControladoresFxml;

import Modelo.CopyTypesEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    //File variables

    @FXML
    private Pane paneFilePreview;

    @FXML
    private TextArea filePreviewTA;

    @FXML
    private Text fileName;

    @FXML
    private Button guardarCambios;

    //Copy variables

    @FXML
    private Pane copyPane;

    @FXML
    private ComboBox<String> copiesTypesCB;

    @FXML
    private TextField fileToCopy;

    @FXML
    private TextField destiny;

    //Move variables

    @FXML
    private Pane movePane;

    @FXML
    private Pane renamePane;

    @FXML
    private TextField newName;

    //Find files variables

    @FXML
    private TextField buscarTF;

    @FXML
    private ListView<String> filesListSearch;

    @FXML
    private Pane paneFileList;

    //Tree view

    @FXML
    private TreeView<String> treeView;

    //Others

    @FXML
    private Pane placeHolder;

    private List<Pane> panesList = new ArrayList<>();


    //File functionalities

    @FXML
    void save(ActionEvent event) {
        guardarCambios.setVisible(false);
        filePreviewTA.setEditable(false);
    }

    @FXML
    void editFile(MouseEvent event) {
        filePreviewTA.setEditable(true);
        guardarCambios.setVisible(true);
    }

    //Copy functionalities

    @FXML
    public void copy(MouseEvent event) {
        seePane(copyPane);
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add(CopyTypesEnum.REAL_VIRTUAL.getTypeEnum());
        types.add(CopyTypesEnum.VIRTUAL_REAL.getTypeEnum());
        types.add(CopyTypesEnum.VIRTUAL_VIRTUAL.getTypeEnum());
        copiesTypesCB.setItems(types);
    }

    @FXML
    public void openFile(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Archivo a copiar");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        Node source = (Node) event.getSource();
        Stage stageActual = (Stage) source.getScene().getWindow();

        File selectedFile = fileChooser.showOpenDialog(stageActual);

        if (copiesTypesCB.getSelectionModel().getSelectedItem() != null) {
            if (copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_REAL.getTypeEnum())
                    || copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_VIRTUAL)) {
                fileChooser.setInitialDirectory(new File("../../Modelo")); //revisar
            } else {
                copiesTypesCB.getSelectionModel().getSelectedItem();
            }
        }

    }

    @FXML
    public void openDirectory(MouseEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Ruta destino");
        Node source = (Node) event.getSource();
        Stage stageActual = (Stage) source.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stageActual);
    }

    //Move functionalities

    @FXML
    public void move(MouseEvent event) {
        seePane(movePane);
    }

    @FXML
    public void confirmMove(ActionEvent event) {
        renamePane.setVisible(true);
    }

    @FXML
    public void replace(ActionEvent event) {
        //remplazar
    }

    //Search functionalities

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
        for (String fileName : foundFiles) {
            String item = "-> " + fileName;
            //item = file.getName() + "\t\t" file.getUbicacion()
            filesListSearch.getItems().add(item);
        }
    }


    //File info functionalities

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

    //Tree view functionalities

    private void fillTree(){
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

    private String getContent(String fileName){
        return "Esto es una prueba";
    }

    //Remove functionalities

    @FXML
    public void remove(MouseEvent event){
        treeView.getSelectionModel().getSelectedItem();
    }

    //Create files/directories

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

    //Others

    @FXML
    public void cancel(ActionEvent event){
        renamePane.setVisible(false);
    }

    @FXML
    private void close(MouseEvent event){
        System.exit(1);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillPanesList();
        fillTree();
    }
}
