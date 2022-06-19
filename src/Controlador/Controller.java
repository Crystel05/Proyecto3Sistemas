package Controlador;
import Modelo.Disco;
import java.util.ArrayList;
import memory.memoryHandler;
import n_ary_tree.*;

public class Controller {

    Tree treeController = new Tree();
    Disco disco = new Disco();
    
    public void createVirtualDisk(int sectors, int sectorSize) {
        disco.createVirtualDisk(sectors, sectorSize);
        
        disco.setTamanio(sectors*sectorSize);
        
        memoryHandler.setData(sectors, sectorSize);
    }
    
    public boolean deleteFile(ArrayList filePath){
        //mapeo para obtener el nodo a base del nombre o la ruta
        Node node = treeController.getNode(filePath);
        
        if (node != null){
            treeController.delete(node);
            return true;
        }
        return false;
          
    }


}
