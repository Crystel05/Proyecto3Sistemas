/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n_ary_tree;

import java.util.ArrayList;

/**
 *
 * @author sande
 */
public class Tree {
    Node root;
 
    public Tree()
    {
        root = new Node();
        root.setValue(new folder("root"));
    }
 
    
    ArrayList<String> getPath(Node pNode){
        ArrayList<String> path = new ArrayList<>();
        Node aux = pNode;
        while(aux != root){
            path.add(0,aux.getValue().getName());
            aux = aux.getParent();
        }
        path.add(0,"root");
        return path;
    }
 
   public Node insert(Node newNode, Node location)
   {
       location.children.add(newNode);
       return newNode;
   }

    public Node getRoot() {
        return root;
    }
 
 
}