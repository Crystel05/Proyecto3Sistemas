import java.io.File;
import java.util.ArrayList;
import memory.memoryHandler;
import n_ary_tree.Filee;
import n_ary_tree.Folder;
import n_ary_tree.Node;
import n_ary_tree.Tree;

public class Main {
    public static void main(String[] args) {
        System.out.println("Mishis love");
        System.out.println("HOLA");
        System.out.println("HOLA 2");

        //Set memory data
        memoryHandler.setData(10, 10);

        File directorio = new File(memoryHandler.getSimulationPath());
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado");

            } else {
                System.out.println("Error al crear directorio");
            }
        }

        Tree arbol = new Tree();
        Folder f = new Folder("Carpeta1");
        arbol.insert(f, arbol.getRoot());

        ArrayList<String> a = new ArrayList<>();
        a.add("root");
        a.add("Carpeta1");
        Node c = arbol.getNode(a);

        Filee fil = new Filee("file1", "texto");
        ArrayList<Integer> sects = new ArrayList<>();
        sects.add(1);
        sects.add(2);
        fil.setSectors(sects);
        arbol.insert(fil, c);
        //arbol.remove("root/Carpeta1/file1");
    }
}
