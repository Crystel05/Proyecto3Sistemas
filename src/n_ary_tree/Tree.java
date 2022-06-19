/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n_ary_tree;

import Modelo.Disco;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class Tree {
    Node root;
    Disco disco = new Disco();
 
    public Tree()
    {
        root = new Node();
        root.setValue(new Folder("root"));
        java.io.File directorio = new java.io.File(memory.memoryHandler.getSimulationPath() + "/root");
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
    
    public void delete(Node n){
        Node parent = n.getParent();
        ArrayList<Node> l = (ArrayList<Node>) parent.getChildren();
        l.remove(n);
        parent.setChildren(l);
        
        String p = "";
        if(n.getValue() instanceof File){
         p = memory.memoryHandler.getSimulationPath()+"/"+pathListToStr(getPath(n));
        }else{
            p =memory.memoryHandler.getSimulationPath()+"/"+ pathListToStr(getPath(n));
        }
           java.io.File f = new java.io.File(p);
           if (f.delete()){
               System.err.println(p);
                System.out.println("El fichero ha sido borrado satisfactoriamente");
           }else{
               System.err.println(p);
                System.out.println("El fichero no puede ser borrado");
           }
           
    }
    
    public void removeAux(Node n)
    {
        System.err.println(n.value.getName());
        if(n.getValue() instanceof File) {
            //TODO: Función para eliminar el archivo de la memoria
            delete(n);
           
         }
        if(n.getValue() instanceof Folder) {
            for (Node childs : n.getChildren()) {
                removeAux(childs);
            }
            delete(n);
        } 
    }
    
    public void remove(String pPath)
    {
        ArrayList<String> path = pathStrToList(pPath);
        Node n = getNode(path);
        System.err.println(n.value.getName());
        removeAux(n);
        
    }
    
    public ArrayList getFileAux(Node actual){
        ArrayList<Node> list =  new ArrayList<>();
         if (actual.getValue() instanceof File){
             list.add(actual);
         }
         for (Node node : actual.getChildren()) {
             list.addAll(getFileAux(node));
         }
         return list;
    }
    
    public ArrayList getFiles(Node actual){
        return getFileAux(actual);
    }
    
    public ArrayList getFolderAux(Node actual){
        ArrayList<Node> list =  new ArrayList<>();
         if (actual.getValue() instanceof Folder){
             list.add(actual);
         }
         for (Node node : actual.getChildren()) {
             list.addAll(getFolderAux(node));
         }
         return list;
    }
    
    public ArrayList getFolders(Node actual){
        return getFolderAux(actual);
    }
    
    public ArrayList findAuxx(Node actual ,String name){
        ArrayList<Node> list =  new ArrayList<>();
        if (actual.getValue().getName().equals(name)){
            list.add(actual);
        }
        for (Node node : actual.getChildren()) {
            list.addAll(findAuxx(node, name));
        }
        return list;
    }
    
    public ArrayList find(String name){
        return findAuxx(root, name);
    }
    
    public ArrayList<String> pathStrToList(String pPath)
    {
        String[] aux= pPath.split("/");
        ArrayList<String> path = new ArrayList<>();
        Collections.addAll(path, aux);
        
        return path;
    }
    
    public String pathListToStr(ArrayList<String> pPath)
    {
        String path= "";
        for(String subDirect : pPath) {
            path+=subDirect+"/";
        }
        path = path.substring(0, path.length()-1); ///Eliminar ultimo caracter "/" del string
        return path;
    }
    
    public void createDirectory(String path){
        java.io.File directorio = new java.io.File(path);
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");
                
            } else {
                System.out.println("Error al crear directorio");
            }
        }
    }

    public void createFile(String name, String content, String path) {
        Integer currentSize = content.length();
        if(disco.getDisponibles() >= currentSize) {
            try {
                String finalPath = path +".txt";
                System.out.println(finalPath);
                java.io.File file = new java.io.File(finalPath);
                
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(content);
                bw.close();
                
                // modificar el disco
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Node insert(Element newNodeElement, Node location,Disco disk)
    {
        disco=disk;
        String path = memory.memoryHandler.getSimulationPath();
        ArrayList<String> AuxPath =getPath(location);
        for (String str : AuxPath) {
            path+="/"+str;
        }
        path+="/"+newNodeElement.getName();
        
        //If is a directory
        if(newNodeElement instanceof Folder){
            createDirectory(path);
        }
        if(newNodeElement instanceof File){
            createFile(newNodeElement.getName(), ((File) newNodeElement).getData(),path);
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