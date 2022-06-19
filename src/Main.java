import java.util.ArrayList;
import Modelo.Disk;
import memory.memoryHandler;
import n_ary_tree.File;
import n_ary_tree.Folder;
import n_ary_tree.Node;
import n_ary_tree.Tree;

public class Main {
    public static void main(String[] args) {
        System.out.println("Mishis love");
        System.out.println("HOLA");
        System.out.println("HOLA 2");

        //Set memory data
        memoryHandler.setData(100, 100);

        java.io.File directorio = new java.io.File(memoryHandler.getSimulationPath());
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");

            } else {
                System.out.println("Error al crear directorio");
            }
        }
        Disk disk = Disk.getInstance();
        disk.createVirtualDisk(100, 100);
        
        disk.setSize(100*100);
        
        memoryHandler.setData(100, 100);
        Tree arbol = new Tree();
        Folder f = new Folder("Carpeta1");
        arbol.insert(f, arbol.getRoot());

        ArrayList<String> a = new ArrayList<>();
        a.add("My File System");
        a.add("Carpeta1");
        Node c = arbol.getNode(a);

        File fil = new File("file1", "texto");
        ArrayList<Integer> sects = new ArrayList<>();
        sects.add(1);
        sects.add(2);
        fil.setSectors(sects);
        arbol.insert(fil, c);
        ArrayList<String>pF = new ArrayList<>();pF.add("My File System");pF.add("Carpeta1");pF.add("file1");
        ArrayList<String>pd = new ArrayList<>();pd.add("My File System");
        arbol.moveTo(arbol.getNode(pF), arbol.getNode(pd));
                
              /*  java.io.File file = new java.io.File("./Simulacion File System/root/Carpeta1/file1.txt");
		String targetDirectory = "./Simulacion File System/root/";
                java.io.File fk = new java.io.File("./Simulacion File System/root/file1.txt");
		fk.delete();
                if (file.renameTo(new java.io.File(targetDirectory+ file.getName()))) {
			System.out.println("File is moved to " + targetDirectory);
		} else {
			System.out.println("Failed");
                }*/
        
//        arbol.remove("root");
    }
}
