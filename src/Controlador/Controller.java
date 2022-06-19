package Controlador;
import java.util.ArrayList;
import memory.memoryHandler;
import n_ary_tree.*;
import Modelo.Disk;

public class Controller {

    private final Disk disk = Disk.getInstance();
    private Tree myFileSystem;
    private static Controller controller;
    public static Controller getInstance(){
        if (controller == null){
            controller = new Controller();
        return controller;
        }
    }
    public Disk getDisk() {
        return disk;
    }

    public Tree getMyFileSystem() {
        return myFileSystem;
    }

    public void createVirtualDisk(int sectors, int sectorSize) {
        disk.createVirtualDisk(sectors, sectorSize);
        disk.setSize(sectors*sectorSize);
        myFileSystem = new Tree();
        prueba();
    }

    private void prueba(){
        Folder f = new Folder("Carpeta1");
        myFileSystem.insert(f, myFileSystem.getRoot());
        Folder f1 = new Folder("Carpeta2");
        myFileSystem.insert(f1, myFileSystem.getRoot());
        Folder f2 = new Folder("Carpeta3");
        myFileSystem.insert(f2, myFileSystem.getRoot());

        for (Node n: myFileSystem.getRoot().getChildren()) {
            Folder f1_sub1 = new Folder("Carpeta1");
            myFileSystem.insert(f1_sub1, n);
            File file = new File("File 1", ".");
            myFileSystem.insert(file, n.getChildren().get(0));
        }
    }
    
    public boolean delete(ArrayList filePath){  ///filepath a partir de root   --->  {root, carpeta, nombre file o directorio }
        //mapeo para obtener el nodo a base del nombre o la ruta
        Node node = treeController.getNode(filePath);
        
        if (node != null){
            treeController.delete(node);
            return true;
        }
        return false;
          
    }
    
    public ArrayList getDirectories(){ //Lista nodos que son directorios
        return treeController.getFolders();
    }
    
    public ArrayList getEverythingNodes(){ // Todos los nodos
        return treeController.getNodes();
    }
    
    public ArrayList find(String name){//todos los nodos que tienen un element con el nomber recibido
        return treeController.find(name);
    }
    
}
