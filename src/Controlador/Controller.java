package Controlador;

import n_ary_tree.Node;
import n_ary_tree.Tree;

public class Contoller {

    Tree treeController = new Tree();

    public boolean deleteFile(String fileName){
        //mapeo para obtener el nodo a base del nombre o la ruta
        Node node = mapeo();
        return treeController.delete(node);
    }

}
