/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n_ary_tree;

import Modelo.Disk;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Alexander
 */
public class Tree {
    private Node root;
    private final Disk disk = Disk.getInstance();

    public Tree()
    {
        root = new Node();
        root.setValue(new Folder("My File System"));
        java.io.File directorio = new java.io.File(Disk.getSimulationPath() + "/My File System");
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
               // System.out.println("Directorio raiz creado");
            } else {
               // System.out.println("Error al crear el directorio raiz");
            }
        }
    }
 
    public ArrayList<String> getPath(Node pNode){
        ArrayList<String> path = new ArrayList<>();
        Node aux = pNode;
        while(aux != root){
            path.add(0,aux.getValue().getName());
            aux = aux.getParent();
        }
        path.add(0,"My File System");
        return path;
    }
    
    public Node getNode(ArrayList<String> path) {
        Node Aux = null;
        if (path.size() > 0){
            Aux = root;
        }
        for (int i = 1; i < path.size(); i++) {
            for (Node node : Aux.getChildren()) {
                String[] s = path.get(i).split("\\.");
                if(s[0].equals(node.getValue().getName())){
                    Aux = node;
                }    
            }
        }
        
        return Aux;
    }
    
    public boolean moveTo(Node toMove, Node destiny)
    {
        if(destiny.getValue() instanceof File){System.err.println("El destino no es un directorio");return false;}
        ArrayList<String>pathToMove = getPath(toMove);
        String pToMove = pathListToStr(pathToMove);
        if(toMove.getValue() instanceof File)
            pToMove = Disk.getSimulationPath()+"/"+pToMove+".txt";
        if(toMove.getValue() instanceof Folder)
            pToMove = Disk.getSimulationPath()+"/"+pToMove;

        ArrayList<String>pathDestiny = getPath(destiny);
        String pDestiny = pathListToStr(pathDestiny);
        pDestiny = Disk.getSimulationPath()+"/"+pDestiny+"/";

        //Mover
        java.io.File file = new java.io.File(pToMove);
        String targetDirectory = pDestiny;
        boolean ret = file.renameTo(new java.io.File(targetDirectory+ file.getName())); 
        if (ret ){
            Node parent = toMove.getParent();
            List<Node> l =  parent.getChildren();
            l.remove(toMove);
            parent.setChildren(l);

            toMove.setParent(destiny);
            destiny.addChild(toMove);
        }
        return  ret;
        
    }
    public boolean moveOverwriting(Node toMove, Node destiny) throws IOException{
        if(destiny.getValue() instanceof File){System.err.println("El destino no es un directorio");return false;}
        ArrayList<String>pathToMove = getPath(toMove);
        String pToMove = pathListToStr(pathToMove);
        if(toMove.getValue() instanceof File)
            pToMove = Disk.getSimulationPath()+"/"+pToMove+".txt";
        if(toMove.getValue() instanceof Folder)
            pToMove = Disk.getSimulationPath()+"/"+pToMove;

        System.out.println("Path to move = "+ pToMove);
        ArrayList<String>pathDestiny = getPath(destiny);
        String pDestiny = pathListToStr(pathDestiny);
        pDestiny = Disk.getSimulationPath()+"/"+pDestiny+"/";
        System.out.println("Destiny = "+ pDestiny);
        //Delete el existente
        String p = "";
        if(toMove.getValue() instanceof File){
            p =  pDestiny+toMove.getValue().getName()+".txt";
        }else{
            p=pDestiny+toMove.getValue().getName();
        }
        java.io.File fk= new java.io.File(p);
        
        if(fk.exists()){
            Node n = destiny.getChild(toMove.getValue().getName());
            delete(n);
            System.out.println("Reemplazado ");
        }

        //Mover
        java.io.File file = new java.io.File(pToMove);
        String targetDirectory = pDestiny;
        boolean ret = file.renameTo(new java.io.File(targetDirectory+ file.getName())); 
        if (ret ){
            Node parent = toMove.getParent();
            List<Node> l =  parent.getChildren();
            l.remove(toMove);
            parent.setChildren(l);

            toMove.setParent(destiny);
            destiny.addChild(toMove);
        }
        return  ret;
    }
    
    public boolean moveRename(Node toMove, Node destiny,String newName) throws IOException{
        if(destiny.getValue() instanceof File){System.err.println("El destino no es un directorio");return false;}
        ArrayList<String>pathToMove = getPath(toMove);
        String pToMove = pathListToStr(pathToMove);
        if(toMove.getValue() instanceof File)
            pToMove = Disk.getSimulationPath()+"/"+pToMove+".txt";
        if(toMove.getValue() instanceof Folder)
            pToMove = Disk.getSimulationPath()+"/"+pToMove;

        System.out.println("Path to move = "+ pToMove);
        ArrayList<String>pathDestiny = getPath(destiny);
        String pDestiny = pathListToStr(pathDestiny);
        pDestiny = Disk.getSimulationPath()+"/"+pDestiny+"/";
        System.out.println("Destiny = "+ pDestiny);
        
        //Mover
        toMove.getValue().setName(newName);
        java.io.File file = new java.io.File(pToMove);
        String targetDirectory = pDestiny;
        if(toMove.getValue() instanceof File){newName+=".txt";}
        boolean ret = file.renameTo(new java.io.File(targetDirectory+ newName)); 
        if (ret ){
            Node parent = toMove.getParent();
            List<Node> l =  parent.getChildren();
            l.remove(toMove);
            parent.setChildren(l);

            toMove.setParent(destiny);
            destiny.addChild(toMove);
        }
        return  ret;
    }
    
    public void deleteSimulationAux(Node n){
        if (n.getValue() instanceof Folder){
            List<Node> l =  n.getChildren();
            for (Node node : l) {
                deleteSimulationAux(node);
            }
        }
        String p = "";
        if(n.getValue() instanceof File){
         p = Disk.getSimulationPath()+"/"+pathListToStr(getPath(n))+".txt";
        }else{
            p = Disk.getSimulationPath()+"/"+ pathListToStr(getPath(n));
        }
        System.out.println("Eliminando: "+p);
        java.io.File f = new java.io.File(p);
        f.delete();
    }
    
    public void deleteSimulation(){deleteSimulationAux(root);}
    
    public void delete(Node n){
        if (n!=root){
            Node parent = n.getParent();
            List<Node> l =  parent.getChildren();
            l.remove(n);
            parent.setChildren(l);
        }
        
        String p = "";
        if(n.getValue() instanceof File){
         p = Disk.getSimulationPath()+"/"+pathListToStr(getPath(n))+".txt";
        }else{
            p = Disk.getSimulationPath()+"/"+ pathListToStr(getPath(n));
        }
        java.io.File f = new java.io.File(p);
        if (f.delete()){
//            System.out.println(p);
//            System.out.println("El fichero ha sido borrado satisfactoriamente");
        }else{
//            System.out.println(p);
//            System.out.println("El fichero no puede ser borrado");
        }
           
    }
    
    private void removeAux(Node n) throws IOException
    {
        if(n.getValue() instanceof File) {
            //TODO: Funci??n para eliminar el archivo de la memoria
            delete(n);
            BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(disk.getPathActualDisk()));
            String texto = br.readLine();
            int count = 0;
            ArrayList<Integer> sectors = ((File)(n.getValue())).getSectors();
            StringBuilder body= new StringBuilder();
            if(!sectors.contains(0)){
                body = new StringBuilder(texto);
            }
            while(texto != null) {
                if (sectors.contains(count)){
                    //Remover de la lista de sectores de disco
                    List<Integer> sects = disk.getSectors();
                    sects.remove((Object)count);
                    disk.setSectors(sects);
                    disk.addFreeSpace();
                    //Escribir la linea de ceros en el disco
                    for (int i = 0; i < disk.getSectorSize(); i++) {
                        body.append("0");
                    }
                    if (sectors.contains(count+1))
                        body.append("\n");
                }else{
                    if(texto!=null) body.append("\n").append(texto);
                }
                texto = br.readLine();
                count++;
            }
            System.out.println(count);
            java.io.File f = new java.io.File(disk.getPathActualDisk());
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            assert body != null;
            bw.write(body.toString());
            bw.close();
            
        }
        catch (FileNotFoundException ex) {
            System.out.println("Error: Fichero de disco no encontrado");
                ex.printStackTrace();
        }
            
           
         }
        if(n.getValue() instanceof Folder) {
            for (Node childs : n.getChildren()) {
                removeAux(childs);
            }
            delete(n);
        } 
    }
    
    public void remove(String pPath) throws IOException
    {
        ArrayList<String> path = pathStrToList(pPath);
        Node n = getNode(path);
        System.err.println(n.getValue().getName());
        removeAux(n);
        
    }
    
    private ArrayList<Node> getFileAux(Node actual){
        ArrayList<Node> list =  new ArrayList<>();
         if (actual.getValue() instanceof File){
             list.add(actual);
         }
         for (Node node : actual.getChildren()) {
             list.addAll(getFileAux(node));
         }
         return list;
    }
    
    public ArrayList<Node> getFiles(){
        return getFileAux(root);
    }
    
    private ArrayList<Node> getFolderAux(Node actual){
        ArrayList<Node> list =  new ArrayList<>();
         if (actual.getValue() instanceof Folder){
             list.add(actual);
         }
         for (Node node : actual.getChildren()) {
             list.addAll(getFolderAux(node));
         }
         return list;
    }
    
    public ArrayList<Node> getFolders(){
        return getFolderAux(root);
    }
    
    public ArrayList<Node> getNodesAux(Node actual){
        ArrayList<Node> list =  new ArrayList<>();
        list.add(actual);
         for (Node node : actual.getChildren()) {
             list.addAll(getNodesAux(node));
         }
         return list;
    }
    
    public ArrayList<Node> getNodes(){
        return getNodesAux(root);
    }
    
    public ArrayList<Node> findAuxOnlyFiles(Node actual ,String name){

        ArrayList<Node> list = new ArrayList<>();
        if (actual.getValue() instanceof File){
            if (actual.getValue().getName().equals(name)){
                list.add(actual);
            }
        }
        for (Node node : actual.getChildren()) {
            list.addAll(findAuxOnlyFiles(node, name));
        }
        return list;
    }
    
    public ArrayList<Node> findAux(Node actual ,String name){

        ArrayList<Node> list = new ArrayList<>();
        if (actual.getValue().getName().equals(name)){
            list.add(actual);
        }
        for (Node node : actual.getChildren()) {
            list.addAll(findAux(node, name));
        }
        return list;
    }
    
    public ArrayList<Node> find(String name){
        if(".txt".equals(name) || "*.txt".equals(name)){
            return getFiles();
        }
        if (name.endsWith(".txt")){
            String str = name.substring(0, name.length()-4);
            return findAuxOnlyFiles(root, str);
        }
        return findAux(root, name);
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
            //    System.out.println("Directorio creado");
                
            } else {
             //   System.out.println("Error al crear directorio");
            }
        }
    }

    public boolean createFile(File newFile, String path) {
        List<String> contents = divideContentOnSectors(newFile.getData());
        if(disk.getFreeSpace() >= contents.size()) {
            newFile.setSize(newFile.getData().length());
            try {
                String finalPath = path + ".txt";
                java.io.File file = new java.io.File(finalPath);
                
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(newFile.getData());
                bw.close();
                String fileContent = "";
                java.io.File diskFile = new java.io.File(disk.getPathActualDisk());
                Scanner myReader = new Scanner(diskFile);
                ArrayList<Integer> fileSectors = new ArrayList<>();
                int i  = 0;
                if (disk.getSectors().isEmpty()){
                    while (myReader.hasNextLine()) {
                        String nextLine = myReader.nextLine();
                        if (i < contents.size()) {
                            fileContent = fileContent + contents.get(i) + "\n";
                            fileSectors.add(i);
                            disk.addTo(i);
                            disk.substractFreeSpace();
                        }else{
                            fileContent = fileContent + nextLine + "\n";
                        }
                        i++;
                    }

                }else{
                    int cont = 0;
                    while (myReader.hasNextLine()) {
                        String nextLine = myReader.nextLine();
                        if (!disk.getSectors().contains(cont)){
                            if (i < contents.size()) {
                                fileContent = fileContent + contents.get(i) + "\n";
                                fileSectors.add(cont);
                                disk.addTo(cont);
                                disk.substractFreeSpace();
                            }else {
                                fileContent = fileContent + nextLine + "\n";
                            }
                            i++;
                        } else {
                            fileContent = fileContent + nextLine + "\n";
                        }
                        cont ++;
                    }
                    newFile.setSectors(fileSectors);
                    FileWriter fwDisk = new FileWriter(diskFile);
                    BufferedWriter bwDisk = new BufferedWriter(fwDisk);
                    bwDisk.write(fileContent);
                    bwDisk.close();
                }
                newFile.setSectors(fileSectors);
                FileWriter fwDisk = new FileWriter(diskFile);
                BufferedWriter bwDisk = new BufferedWriter(fwDisk);
                bwDisk.write(fileContent);
                bwDisk.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
        return false;
    }

    public List<String> divideContentOnSectors(String content) {
        List<String> contentSectors = new ArrayList<>();
        if (content.length() <= disk.getSectorSize()){
            if (content.length() < disk.getSectorSize())
                content = content + ceros((disk.getSectorSize() - content.length()));
            contentSectors.add(content);

        }else{
            for (int start = 0; start < content.length(); start += disk.getSectorSize()) {
                String substring = content.substring(start, Math.min(content.length(), start + disk.getSectorSize()));
                if (content.length() < start + disk.getSectorSize()){
                    substring = substring + ceros(((start + disk.getSectorSize()) - content.length()));
                }
                contentSectors.add(substring);
            }
        }
        return contentSectors;
    }

    private String ceros(int zeros) {
        String zers = "";
        for (int i = 0; i< zeros; i++) {
            zers = zers + "0";
        }
        return zers;
    }

    public void insert(Element newNodeElement, Node location) {
        String path = Disk.getSimulationPath();
        ArrayList<String> AuxPath = getPath(location);
        for (String str : AuxPath) {
            path+="/"+str;
        }
        path+="/"+newNodeElement.getName();
        
        //If is a directory
        boolean createSuccess = true;
        if(newNodeElement instanceof Folder){
            createDirectory(path);
        }
        if(newNodeElement instanceof File){
            createSuccess = createFile((File) newNodeElement, path);
        }
        if (createSuccess) {
            //New node
            Node aux = new Node(newNodeElement, location);
            List<Node> auxChild = location.getChildren();
            auxChild.add(aux);
            location.setChildren(auxChild);
        }
    }

    public Node getRoot() {
        return root;
    }
}