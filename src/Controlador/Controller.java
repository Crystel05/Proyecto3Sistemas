package Controlador;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import n_ary_tree.*;
import Modelo.Disk;
import java.io.IOException;
import java.util.Scanner;

public class Controller {

    private final Disk disk = Disk.getInstance();
    private CopyController copyController = new CopyController();
    private Tree myFileSystem;
    private static Controller controller;
    private TreeItem<String> currentItem;
    private TreeView<String> treeView;

    public static Controller getInstance(){
        if (controller == null){
            controller = new Controller();
        }
        return controller;
    }
    public Disk getDisk() {
        return disk;
    }

    public Tree getMyFileSystem() {
        return myFileSystem;
    }

    public void createVirtualDisk(int sectors, int sectorSize) {
        disk.createVirtualDisk(sectors, sectorSize);
        myFileSystem = new Tree();
        prueba();
    }

    public TreeItem<String> getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(TreeItem<String> currentItem) {
        this.currentItem = currentItem;
    }

    public TreeView<String> getTreeView() {
        return treeView;
    }

    public void setTreeView(TreeView<String> treeView) {
        this.treeView = treeView;
    }

    //Tree view functionalities

    public void fillTree() throws FileNotFoundException {
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
            if (node.getValue().getClass().equals(n_ary_tree.File.class)){
                path = "src/Vista/Imagenes/fileIcon.png";
                height = 17;
                width = 13;
            }
            Image image = new Image(new FileInputStream(path));
            ImageView imageV = new ImageView(image);
            imageV.setFitHeight(height);
            imageV.setFitWidth(width);
            String name = node.getValue().getName();
            if (node.getValue().getClass().equals(File.class)){
                name = name + ".txt";
            }
            TreeItem<String> dir_fil = new TreeItem<>(name, imageV);
            root.getChildren().add(dir_fil);
            if (!node.getChildren().isEmpty()){
                fillTreeAux(node, dir_fil);
            }
        }
    }

    public ArrayList<String> getPath(TreeItem<String> item){
        ArrayList<String> path = new ArrayList<>();
        path.add(item.getValue());
        getPathAux(item, path);
        Collections.reverse(path);
        return path;
    }
    private void getPathAux(TreeItem<String> item, List<String> path) {
        if(item.getParent() != null){
            path.add(item.getParent().getValue());
            getPathAux(item.getParent(), path);
        }
    }

    //create files

    public void insertFile(String name, String content, ArrayList<String> locationPath) {
        Node location = myFileSystem.getNode(locationPath);
        File newNodeElement = new File(name, content);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String dateToStr = dateFormat.format(date);
        newNodeElement.setCreationDate(dateToStr);
        newNodeElement.setModificationDate(dateToStr);
        myFileSystem.insert(newNodeElement, location);
    }

    public void insertDirectory(String name, ArrayList<String> locationPath){
        Node location = myFileSystem.getNode(locationPath);
        Folder newNodeElement = new Folder(name);
        myFileSystem.insert(newNodeElement, location);
    }
    
    private void prueba(){
        Folder f = new Folder("Carpeta1");
        myFileSystem.insert(f, myFileSystem.getRoot());
        Folder f1 = new Folder("Carpeta2");
        myFileSystem.insert(f1, myFileSystem.getRoot());
        Folder f2 = new Folder("Carpeta3");
        myFileSystem.insert(f2, myFileSystem.getRoot());
        int i = 0;
        for (Node n: myFileSystem.getRoot().getChildren()) {
            Folder f1_sub1 = new Folder("Carpeta1");
            myFileSystem.insert(f1_sub1, n);
            i++;
            File file = new File("File " + i, "Esto es una prueba de contenido" + i+i+i+i+i+i);
            myFileSystem.insert(file, n.getChildren().get(0));
            System.out.println(n.getChildren().get(0).getChildren().size());
        }
    }
    
    public boolean delete(ArrayList filePath) throws IOException{  ///filepath a partir de root   --->  {root, carpeta, nombre file o directorio }
        //mapeo para obtener el nodo a base del nombre o la ruta
        Node node = myFileSystem.getNode(filePath);
        
        if (node != null){
            myFileSystem.remove(myFileSystem.pathListToStr(filePath));
            return true;
        }
        return false;
    }
    
    public boolean moveTo(ArrayList nodePath, ArrayList destinyPath){
        Node ToMove = myFileSystem.getNode(nodePath);
        Node destiny = myFileSystem.getNode(destinyPath);
        boolean ret = myFileSystem.moveTo(ToMove, destiny);
        return ret;
    }
    public boolean moveOverwriting(ArrayList nodePath, ArrayList destinyPath) throws IOException{
        Node ToMove = myFileSystem.getNode(nodePath);
        Node destiny = myFileSystem.getNode(destinyPath);
        boolean ret = myFileSystem.moveOverwriting(ToMove, destiny);
        return ret;
    }
    public ArrayList getDirectories(){ //Lista nodos que son directorios
        return myFileSystem.getFolders();
    }
    
    public ArrayList getEverythingNodes(){ // Todos los nodos
        return myFileSystem.getNodes();
    }
    
    public ArrayList find(String name){//todos los nodos que tienen un element con el nombre recibido
        return myFileSystem.find(name);
    }

    public String getContent(File file) {
        String content = "";
        
        try {
            java.io.File diskFile = new java.io.File(disk.getPathActualDisk());
            Scanner myReader = new Scanner(diskFile);
            ArrayList<Integer> sectors = file.getSectors();
            int maxLine = Collections.max(sectors);
            int lineNumber = 0;
            
            while (myReader.hasNextLine() && (maxLine >= lineNumber)) {
                if (sectors.contains(lineNumber)){
                    String line = myReader.nextLine();
                    line = line.replace("0", "");
                    content = content + line;
                } else {
                    myReader.nextLine();
                }

                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return content;
    }
    
    public ArrayList<String> getProperties(File file) {
        ArrayList<String> properties = new ArrayList<>();
        
        properties.add(file.getName());
        properties.add(".txt");
        properties.add(file.getCreationDate());
        properties.add(file.getModificationDate());
        properties.add(String.valueOf(file.getSize()));
        
        return properties;
    }

    //Copy
    public void copyVirtualVirtual(String pathOrigin, String pathDestiny, boolean isDirectory) throws IOException {
        copyRealVirtual(pathOrigin, pathDestiny, isDirectory);
    }

    public void copyVirtualReal(String pathOrigin, String pathDestiny, boolean isDirectory) throws IOException {
        copyController.copy(pathOrigin, pathDestiny, isDirectory);
    }

    public void copyRealVirtual(String pathOrigin, String pathDestiny, boolean isDirectory) throws IOException {
        String[] nameL = pathOrigin.split("/");
        String[] name = nameL[nameL.length - 1].split("\\.");
        String n = name[0];

        if (!isDirectory) {
            byte[] encoded = Files.readAllBytes(Paths.get(pathOrigin));
            String content = new String(encoded, StandardCharsets.UTF_8);
            insertFile(n, content, myFileSystem.pathStrToList(pathDestiny));
        }else{
            insertDirectory(n, myFileSystem.pathStrToList(pathDestiny));
        }
    }


}
