package Vista.ControladoresFxml;

import Controlador.Controller;
import Modelo.CopyTypesEnum;
import Modelo.MoveTypes;
import Vista.DragWindow;
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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindow implements Initializable, DragWindow {

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

    @FXML
    private RadioButton directorioRBMove;

    @FXML
    private RadioButton archivoRBMove;

    @FXML
    private TextField moveOriginTF;

    @FXML
    private TextField destMoveTF;

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

    @FXML
    private Pane panePrincipal;

    private List<Pane> panesList = new ArrayList<>();


    //File functionalities

    @FXML
    void save(ActionEvent event) {
        guardarCambios.setVisible(false);
        filePreviewTA.setEditable(false);
        n_ary_tree.File file = (n_ary_tree.File) controller.getMyFileSystem().getNode(controller.getPath(controller.getCurrentItem())).getValue();
        controller.editFile(file, filePreviewTA.getText());
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
        directorioRBMove.setSelected(false);
    }

    @FXML public void radioButtonSelectedDirectory(ActionEvent event){
        archivoRB.setSelected(false);
        archivoRBMove.setSelected(false);
    }

    @FXML
    public void copy(MouseEvent event) {
        seePane(copyPane);
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add(CopyTypesEnum.REAL_VIRTUAL.getTypeEnum());
        types.add(CopyTypesEnum.VIRTUAL_REAL.getTypeEnum());
        types.add(CopyTypesEnum.VIRTUAL_VIRTUAL.getTypeEnum());
        copiesTypesCB.setItems(types);
        fileToCopy.setText("");
        destiny.setText("");
    }

    @FXML
    public void file_dirToCopy(MouseEvent event){
        System.out.println(archivoRBMove.isSelected());
        if (archivoRB.isSelected() ){
            openFile(event);
        }
        if (directorioRB.isSelected()){
            openDirectory(event, false);
        }

        if (directorioRBMove.isSelected()){
            openDirectoryMove(event, false);
        }

        if (archivoRBMove.isSelected()){
            openFileMove(event);
        }
    }

    @FXML
    public void destinyPath(MouseEvent event){
        openDirectory(event, true);
    }

    @FXML
    public void destinyDir(MouseEvent event){
        openDirectoryMove(event, true);
    }

    private void openFile(MouseEvent event) {
        try {
            if (copiesTypesCB.getSelectionModel().getSelectedItem() != null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Archivo a copiar");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                if (copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_REAL.getTypeEnum())
                        || copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_VIRTUAL.getTypeEnum())) {
                    fileChooser.setInitialDirectory(new File("Simulacion File System/My File System"));
                }
                Node source = (Node) event.getSource();
                Stage stageActual = (Stage) source.getScene().getWindow();

                File selectedFile = fileChooser.showOpenDialog(stageActual);
                String path = selectedFile.getAbsolutePath().replace("\\", "/");
                fileToCopy.setText(path);
            }
        }catch (NullPointerException ignore){}

    }

    private void openFileMove(MouseEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Archivo a mover");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text Files", "*.txt"));
            fileChooser.setInitialDirectory(new File("Simulacion File System/My File System"));
            Node source = (Node) event.getSource();
            Stage stageActual = (Stage) source.getScene().getWindow();

            File selectedFile = fileChooser.showOpenDialog(stageActual);
            String path = selectedFile.getAbsolutePath().replace("\\", "/");
            moveOriginTF.setText(path);
        }catch (NullPointerException ignore){}
    }

    private void openDirectory(MouseEvent event, boolean isDestiny) {
        try {
            if (!copiesTypesCB.getSelectionModel().getSelectedItem().isEmpty()) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Ruta destino");
                if ((!isDestiny && copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_REAL.getTypeEnum()))
                        || copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.VIRTUAL_VIRTUAL.getTypeEnum())
                        || (isDestiny && copiesTypesCB.getSelectionModel().getSelectedItem().equals(CopyTypesEnum.REAL_VIRTUAL.getTypeEnum()))) {
                    directoryChooser.setInitialDirectory(new File("Simulacion File System/My File System"));
                }
                Node source = (Node) event.getSource();
                Stage stageActual = (Stage) source.getScene().getWindow();
                File selectedDirectory = directoryChooser.showDialog(stageActual);
                String path = selectedDirectory.getAbsolutePath().replace("\\", "/");
                System.out.println(path);
                if (isDestiny)
                    destiny.setText(path);
                else
                    fileToCopy.setText(path);
            }
        }catch (NullPointerException ignored){}
    }

    private void openDirectoryMove(MouseEvent event, boolean isDestiny){
        try {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setTitle("Ruta origen");
            directoryChooser.setInitialDirectory(new File("Simulacion File System/My File System"));
            Node source = (Node) event.getSource();
            Stage stageActual = (Stage) source.getScene().getWindow();
            File selectedDirectory = directoryChooser.showDialog(stageActual);
            String path = selectedDirectory.getAbsolutePath().replace("\\", "/");
            if (!isDestiny)
                moveOriginTF.setText(path);
            else
                destMoveTF.setText(path);
        }catch (NullPointerException ignore){}
    }

    @FXML
    public void copyFiles(ActionEvent event) throws IOException {
        if (!fileToCopy.getText().isEmpty() && !destiny.getText().isEmpty()) {
            boolean isDirectory = directorioRB.isSelected();
            String copyType = copiesTypesCB.getSelectionModel().getSelectedItem();
            switch (copyType) {
                case "Ruta virtual a ruta real":
                    controller.copyVirtualReal(fileToCopy.getText(), destiny.getText(), isDirectory);
                    break;
                case "Ruta real a ruta virtual":
                    controller.copyRealVirtual(fileToCopy.getText(), destiny.getText(), isDirectory, null);
                    controller.fillTree();
                    break;
                case "Ruta virtual a ruta virtual":
                    controller.copyVirtualVirtual(fileToCopy.getText(), destiny.getText(), isDirectory);
                    controller.fillTree();
                    break;
            }
        }
    }

    //Move functionalities

    @FXML
    public void move(MouseEvent event) {
        seePane(movePane);
        moveOriginTF.setText("");
        destMoveTF.setText("");
        renamePane.setVisible(false);
    }

    @FXML
    public void confirmMove(ActionEvent event) throws IOException {
        if (!destMoveTF.getText().isEmpty() && !moveOriginTF.getText().isEmpty()) {
            if (controller.verifyEquals(destMoveTF.getText(), moveOriginTF.getText())) {
                renamePane.setVisible(true);
            }else{
                controller.move(moveOriginTF.getText(), destMoveTF.getText(), MoveTypes.NORMAL, null);
            }
        }
    }



    @FXML
    public void moveRename(ActionEvent event) throws IOException {
        if (!newName.getText().isEmpty()){
            controller.move(moveOriginTF.getText(), destMoveTF.getText(), MoveTypes.RENAME, newName.getText());
        }
    }

    @FXML
    public void replace(ActionEvent event) throws IOException {
        if (!moveOriginTF.getText().isEmpty() && !destMoveTF.getText().isEmpty()){
            controller.move(moveOriginTF.getText(), destMoveTF.getText(), MoveTypes.OVERWRITE, null);
            renamePane.setVisible(false);
        }
    }

    //Search functionalities

    @FXML
    void search(MouseEvent event) {
        filesListSearch.getItems().clear();
        List<n_ary_tree.Node> foundNodes = controller.find(buscarTF.getText());
        seePane(paneFileList);

        fillSearch(foundNodes);
    }

    private void fillSearch(List<n_ary_tree.Node> foundFiles) {
        for (n_ary_tree.Node node : foundFiles) {
            String item = node.getValue().getName() + "->" + controller.getMyFileSystem().pathListToStr(controller.getMyFileSystem().getPath(node));
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


    @FXML
    public void selectItem(){
        TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();

        if (item != null) {
            if (!item.isExpanded() && !item.isLeaf()){
                item.setExpanded(true);
            }
            ArrayList<String> path = controller.getPath(item);
            if (controller.getMyFileSystem().getNode(path).getValue().getClass().equals(n_ary_tree.File.class)) {
                n_ary_tree.File file = (n_ary_tree.File) controller.getMyFileSystem().getNode(path).getValue();
                seePane(paneFilePreview);
                fileName.setText(file.getName());
                filePreviewTA.setText(controller.getContent(file));

            }
            controller.setCurrentItem(item);
        }
    }

    //Remove functionalities

    @FXML
    public void remove(MouseEvent event) throws IOException {
        controller.delete(controller.getPath(treeView.getSelectionModel().getSelectedItem()));
        controller.fillTree();
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
        controller.deleteSimulation();
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
        onDraggedScene(panePrincipal);
        controller.setTreeView(treeView);
        fillPanesList();
        try {
            controller.fillTree();
            controller.setCurrentItem(treeView.getRoot());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
