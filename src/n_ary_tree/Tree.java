/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n_ary_tree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class Tree {
    Node root;
 
    public Tree()
    {
        root = new Node();
        root.setValue(new Folder("root"));
        File directorio = new File("./Simulacion File System/root");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio raiz creado");
                
            } else {
                System.out.println("Error al crear el directorio raiz");
            }
        }
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
    
    public Node getNode(ArrayList<String> path)
    {    Node Aux = null;
        if (path.size() > 0){
            Aux = root;
        }
        for (int i = 1; i < path.size(); i++) {
            for (Node node : Aux.getChildren()) {
                if(path.get(i).equals(node.value.getName())){
                    Aux = node;
                }    
            }
        }
        
        return Aux;
    }
    public void createDirectory(String path){
        File directorio = new File(path);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
                
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }
    public void createFile(String path,String content)
    {
        try {
            File file = new File(path);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Node insert(Element newNodeElement, Node location)
    {
        String path = "./Simulacion File System";
        ArrayList<String> AuxPath =getPath(location);
        for (String str : AuxPath) {
            path+="/"+str;
        }
        path+="/"+newNodeElement.getName();
        
        //If is a directory
        if(newNodeElement instanceof Folder){
            createDirectory(path);
        }
        if(newNodeElement instanceof Filee){
            createFile(path, ((Filee) newNodeElement).getData());
        }
        //New node
        Node aux = new Node(newNodeElement,location);
        
        //Add to children
        List<Node> auxChild= location.getChildren();
        auxChild.add(aux);
        location.setChildren(auxChild);
        return aux;
    }

    public Node getRoot() {
        return root;
    }
}