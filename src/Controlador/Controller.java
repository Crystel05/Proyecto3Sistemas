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
        disk.setSize(sectors*sectorSize);
        myFileSystem = new Tree();
        prueba();
    }
    public void insertFile(String name, String content, ArrayList<String> locationPath)
    {
        Node location = myFileSystem.getNode(locationPath);
        Filee newNodeElement = new Filee(name, content);
        myFileSystem.insert(newNodeElement, location, disk);
    }
    
    private void prueba(){
        Folder f = new Folder("Carpeta1");
        myFileSystem.insert(f, myFileSystem.getRoot(), disk);
        Folder f1 = new Folder("Carpeta2");
        myFileSystem.insert(f1, myFileSystem.getRoot(), disk);
        Folder f2 = new Folder("Carpeta3");
        myFileSystem.insert(f2, myFileSystem.getRoot(), disk);

        for (Node n: myFileSystem.getRoot().getChildren()) {
            Folder f1_sub1 = new Folder("Carpeta1");
            myFileSystem.insert(f1_sub1, n, disk);
            Filee file = new Filee("File 1", ".");
            myFileSystem.insert(file, n.getChildren().get(0), disk);
        }
    }
    
    public boolean delete(ArrayList filePath){  ///filepath a partir de root   --->  {root, carpeta, nombre file o directorio }
        //mapeo para obtener el nodo a base del nombre o la ruta
        Node node = myFileSystem.getNode(filePath);
        
        if (node != null){
            myFileSystem.delete(node);
            return true;
        }
        return false;
          
    }
    
    public ArrayList getDirectories(){ //Lista nodos que son directorios
        return myFileSystem.getFolders();
    }
    
    public ArrayList getEverythingNodes(){ // Todos los nodos
        return myFileSystem.getNodes();
    }
    
    public ArrayList find(String name){//todos los nodos que tienen un element con el nomber recibido
        return myFileSystem.find(name);
    }
    
}
