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
import java.util.Collections;
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
        File directorio = new File(memory.memoryHandler.getSimulationPath() + "/root");
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
        String p = "";
        if(n.getValue() instanceof Filee){
         p = memory.memoryHandler.getSimulationPath()+"/"+pathListToStr(getPath(n));
        }else{
            p =memory.memoryHandler.getSimulationPath()+"/"+ pathListToStr(getPath(n));
        }
           File f = new File(p);
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
        if(n.getValue() instanceof Filee) {
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