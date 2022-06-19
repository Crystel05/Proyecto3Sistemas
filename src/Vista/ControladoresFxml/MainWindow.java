package Vista.ControladoresFxml;

import Controlador.Controller;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import n_ary_tree.Folder;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow implements Initializable {

    private final Controller controller = Controller.getInstance();

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

    @FXML
    private RadioButton directorioRB;

    @FXML
    private RadioButton archivoRB;

    @FXML
    private Label errorCopy;

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
    public void radioButtonSelectedFile(ActionEvent event){
        directorioRB.setSelected(false);
    }

    @FXML public void radioButtonSelectedDirectory(ActionEvent event){
        archivoRB.setSelected(false);
    }
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
    public void file_dirToCopy(MouseEvent event){
        if (archivoRB.isSelected()){
            openFile(event);
        }
        if (directorioRB.isSelected()){
            openDirectory(event, false);
        }
    }

    @FXML
    public void destinyPath(MouseEvent event){
        openDirectory(event, true);
    }

    public void openFile(MouseEvent event) {
        if (copiesTypesCB.getSelectionModel().getSelectedItem() != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Archivo a copiar");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            if (copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_REAL.getTypeEnum())
                    || copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_VIRTUAL.getTypeEnum())) {
                fileChooser.setInitialDirectory(new File("Simulacion File System/root"));
            }
            Node source = (Node) event.getSource();
            Stage stageActual = (Stage) source.getScene().getWindow();

            File selectedFile = fileChooser.showOpenDialog(stageActual);
            String path = selectedFile.getAbsolutePath();
            fileToCopy.setText(path);
        }
    }

    public void openDirectory(MouseEvent event, boolean isDestiny) {
        if (!copiesTypesCB.getSelectionModel().getSelectedItem().isEmpty()) {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Ruta destino");
            if ((!isDestiny && copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_REAL.getTypeEnum()))
                    || copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_VIRTUAL.getTypeEnum())
                    || (isDestiny && copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.REAL_VIRTUAL.getTypeEnum()))) {
                directoryChooser.setInitialDirectory(new File("Simulacion File System/root"));
            }
            Node source = (Node) event.getSource();
            Stage stageActual = (Stage) source.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stageActual);
            String path = selectedDirectory.getAbsolutePath();
            if (isDestiny)
                destiny.setText(path);
            else
                fileToCopy.setText(path);
        }
    }

    @FXML
    public void copyFiles(ActionEvent event){
        if (!fileToCopy.getText().isEmpty() && !destiny.getText().isEmpty()) {
            boolean isDirectory = directorioRB.isSelected();
            String copyType = copiesTypesCB.getSelectionModel().getSelectedItem();
            switch (copyType) {
                case "Ruta virtual a ruta real":
                    controller.copyVirtualReal(fileToCopy.getText(), destiny.getText(), isDirectory);
                    break;
                case "Ruta real a ruta virtual":
                    controller.copyRealVirtual(fileToCopy.getText(), destiny.getText(), isDirectory);
                    break;
                case "Ruta virtual a ruta virtual":
                    controller.copyVirtualVirtual(fileToCopy.getText(), destiny.getText(), isDirectory);
                    break;
            }
        }
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

    private void fillTree() throws FileNotFoundException {
        Image image = new Image(new FileInputStream("src/Vista/Imagenes/carpetIcon.png"));
        ImageView imageV = new ImageView(image);
        imageV.setFitHeight(10);
        imageV.setFitWidth(15);
        TreeItem<String> root = new TreeItem<>(controller.getMyFileSystem().getRoot().getValue().getName(), imageV);
        if (!controller.getMyFileSystem().getRoot().getChildren().isEmpty())
            fillTreeAux(controller.getMyFileSystem().getRoot(), root);
        treeView.setRoot(root);
    }

    private void fillTreeAux(n_ary_tree.Node item, TreeItem<String> root) throws FileNotFoundException {
        for (n_ary_tree.Node node : item.getChildren()){
            String path = "";
            int height = 0;
            int width = 0;
            if (node.getValue().getClass().equals(Folder.class)){
                path = "src/Vista/Imagenes/carpetIcon.png";
                height = 10;
                width = 15;
            }
            if (node.getValue().getClass().equals(n_ary_tree.Filee.class)){
                path = "src/Vista/Imagenes/fileIcon.png";
                height = 17;
                width = 13;
            }
            Image image = new Image(new FileInputStream(path));
            ImageView imageV = new ImageView(image);
            imageV.setFitHeight(height);
            imageV.setFitWidth(width);
            TreeItem<String> dir_fil = new TreeItem<>(node.getValue().getName(), imageV);
            root.getChildren().add(dir_fil);
            if (!node.getChildren().isEmpty()){
                fillTreeAux(node, dir_fil);
            }
        }
    }

    @FXML
    public void selectItem(){
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            if (!item.isExpanded() && !item.isLeaf()){
                item.setExpanded(true);
            }
            ArrayList<String> path = new ArrayList<>();
            path.add(item.getValue());
            getPath(item, path);
            Collections.reverse(path);
            System.out.println(path);

            if (controller.getMyFileSystem().getNode(path).getValue().getClass().equals(n_ary_tree.Filee.class)){
                n_ary_tree.Filee file = (n_ary_tree.Filee) controller.getMyFileSystem().getNode(path).getValue();
                seePane(paneFilePreview);
                filePreviewTA.setText(controller.getContent(file));
            }
        }
    }

    private void getPath(TreeItem<String> item, List<String> path) {
        if(item.getParent() != null){
            path.add(item.getParent().getValue());
            getPath(item.getParent(), path);
        }
    }

    private String getContent(String fileName){
        return "Esto es una prueba";
    }

    //Remove functionalities

    @FXML
    public void remove(MouseEvent event){
        String fileName = treeView.getSelectionModel().getSelectedItem().toString();
//        if (controller.deleteFile(fileName)){
//            //escribir o ventana de confirmación
//        }else{
//            //mandar error
//        }
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
        try {
            fillTree();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
