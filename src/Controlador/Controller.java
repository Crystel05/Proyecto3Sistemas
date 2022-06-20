package Controlador;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import Modelo.MoveTypes;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import n_ary_tree.*;
import Modelo.Disk;

import java.util.Scanner;

public class Controller {

    private final Disk disk = Disk.getInstance();
    private final CopyController copyController = new CopyController();
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
        System.out.println(name);
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
    
    private void prueba() {
        insertDirectory("Carpeta1", myFileSystem.getPath(myFileSystem.getRoot()));
        insertDirectory("Carpeta2", myFileSystem.getPath(myFileSystem.getRoot()));
        insertDirectory("Carpeta3", myFileSystem.getPath(myFileSystem.getRoot()));
        int i = 0;
        for (Node n: myFileSystem.getRoot().getChildren()) {
            insertDirectory("Carpeta1", myFileSystem.getPath(n));
            i++;
            insertFile("File " + i, "Esto es una prueba de contenido" + i+i+i+i+i+i, myFileSystem.getPath( n.getChildren().get(0)));
        }
    }
    
    public void delete(ArrayList<String> filePath) throws IOException{  ///filepath a partir de root   --->  {root, carpeta, nombre file o directorio }
        //mapeo para obtener el nodo a base del nombre o la ruta
        Node node = myFileSystem.getNode(filePath);
        
        if (node != null){
            myFileSystem.remove(myFileSystem.pathListToStr(filePath));
        }
    }

    public void move(String originPath, String destinyPath, MoveTypes type, String newName) throws IOException {
        ArrayList<String> oriPath = myFileSystem.pathStrToList(originPath);
        ArrayList<String> destPath = myFileSystem.pathStrToList(destinyPath);
        switch (type){
            case NORMAL:
                if (moveTo(oriPath, destPath))
                    fillTree();
                break;
            case RENAME:
                if (moveRename(oriPath, destPath, newName))
                    fillTree();
                break;
            case OVERWRITE:
                if (moveOverwriting(oriPath, destPath))
                    fillTree();
                break;
        }
    }
    
    private boolean moveTo(ArrayList<String> nodePath, ArrayList<String> destinyPath){
        Node ToMove = myFileSystem.getNode(nodePath);
        Node destiny = myFileSystem.getNode(destinyPath);
        return myFileSystem.moveTo(ToMove, destiny);
    }
    private boolean moveOverwriting(ArrayList<String> nodePath, ArrayList<String> destinyPath) throws IOException{
        Node ToMove = myFileSystem.getNode(nodePath);
        Node destiny = myFileSystem.getNode(destinyPath);
        return myFileSystem.moveOverwriting(ToMove, destiny);
    }
    
    public boolean moveRename(ArrayList<String> nodePath, ArrayList<String> destinyPath,String newName) throws IOException{
        Node ToMove = myFileSystem.getNode(nodePath);
        Node destiny = myFileSystem.getNode(destinyPath);
        return myFileSystem.moveRename(ToMove, destiny,newName);
    }
    
    public ArrayList<Node> find(String name){//todos los nodos que tienen un element con el nombre recibido
        return myFileSystem.find(name);
    }

    public String getContent(File file) {
        StringBuilder content = new StringBuilder();
        
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
                    content.append(line);
                } else {
                    myReader.nextLine();
                }

                lineNumber++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return content.toString();
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
        if (myFileSystem.getNode(myFileSystem.pathStrToList(pathDestiny)).getChildren()
                .contains(myFileSystem.getNode(myFileSystem.pathStrToList(pathOrigin)))) {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            Date date = new Date();
            String dateToStr = dateFormat.format(date);
            String[] newNameL = pathOrigin.split("/");
            String[] newNameSTx = newNameL[newNameL.length-1].split("\\.");
            String newName = newNameSTx[0] + "_" + dateToStr;
            copyRealVirtual(pathOrigin, pathDestiny, isDirectory, newName);
        }else
            copyRealVirtual(pathOrigin, pathDestiny, isDirectory, null);
    }

    public void copyVirtualReal(String pathOrigin, String pathDestiny, boolean isDirectory) throws IOException {
        copyController.copy(pathOrigin, pathDestiny, isDirectory);
    }

    public void copyRealVirtual(String pathOrigin, String pathDestiny, boolean isDirectory, String newName) throws IOException {
        String[] nameL = pathOrigin.split("/");
        String[] name = nameL[nameL.length - 1].split("\\.");
        String n = name[0];
        if (!isDirectory) {

            byte[] encoded = Files.readAllBytes(Paths.get(pathOrigin));
            String content = new String(encoded, StandardCharsets.UTF_8);
            if (newName != null)
                insertFile(newName, content, myFileSystem.pathStrToList(pathDestiny));
            else
                insertFile(n, content, myFileSystem.pathStrToList(pathDestiny));
        }else{
            insertDirectory(n, myFileSystem.pathStrToList(pathDestiny));
        }
    }


}
