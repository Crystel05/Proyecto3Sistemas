import java.util.ArrayList;
import Modelo.Disk;
import java.io.IOException;
import n_ary_tree.File;
import n_ary_tree.Folder;
import n_ary_tree.Node;
import n_ary_tree.Tree;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Mishis love");
        System.out.println("HOLA");
        System.out.println("HOLA 2");

        //Set memory data

        java.io.File directorio = new java.io.File(Disk.getSimulationPath());
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");

            } else {
                System.out.println("Error al crear directorio");
            }
        }
        Disk disk = Disk.getInstance();
        disk.createVirtualDisk(100, 100);
        
        //disk.setSize(100*100);

        Tree arbol = new Tree();
        Folder f = new Folder("Carpeta1");
        arbol.insert(f, arbol.getRoot());
        Folder f2 = new Folder("Carpeta2");
        arbol.insert(f2, arbol.getRoot());
        ArrayList<String> a = new ArrayList<>();
        a.add("My File System");
        a.add("Carpeta1");
        Node c = arbol.getNode(a);
        ArrayList<String> a2 = new ArrayList<>();
        a2.add("My File System");
        a2.add("Carpeta2");
        Node c2 = arbol.getNode(a2);
        Folder f1 = new Folder("Carpeta1");
        arbol.insert(f1,c2 );
        
        File fil = new File("file1", "texto");
        File fil2 = new File("file1", "texto");
        
        arbol.insert(fil, c);
        arbol.insert(fil2, c);
        
        ArrayList<String>pF = new ArrayList<>();pF.add("My File System");pF.add("Carpeta1");pF.add("file1");
        ArrayList<String>pd = new ArrayList<>();pd.add("My File System");pd.add("Carpeta2");
        System.out.println("Resultado de la busqueda: "+arbol.find(".txt"));
        arbol.moveRename(arbol.getNode(pF), arbol.getNode(pd),"File200");
        arbol.deleteSimulation();
       // arbol.remove("My File System/Carpeta1/file1");        
              /*  java.io.File file = new java.io.File("./Simulacion File System/root/Carpeta1/file1.txt");
		String targetDirectory = "./Simulacion File System/root/";
                java.io.File fk = new java.io.File("./Simulacion File System/root/file1.txt");
		fk.delete();
                if (file.renameTo(new java.io.File(targetDirectory+ file.getName()))) {
			System.out.println("File is moved to " + targetDirectory);
		} else {
			System.out.println("Failed");
                }*/
        
        
    }
}
