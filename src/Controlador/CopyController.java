package Controlador;

import n_ary_tree.Tree;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CopyController {



    public void copy(String pathOrigin, String pathDestiny, boolean isDirectory) throws IOException {
        String[] nameList = pathOrigin.split("/");
        String name = nameList[nameList.length-1];
        File file_DirDest = new File(pathDestiny+ "/" +name);
        if (!isDirectory){

            FileWriter fwDest = new FileWriter(file_DirDest);
            BufferedWriter bwDest = new BufferedWriter(fwDest);
            if (!file_DirDest.exists()){
                file_DirDest.createNewFile();
            }

            byte[] encoded = Files.readAllBytes(Paths.get(pathOrigin));
            String content = new String(encoded, StandardCharsets.UTF_8);
            bwDest.write(content);
            bwDest.close();
        }else{
            boolean mkdir = file_DirDest.mkdir();
        }
    }

    public static void main(String[] args) throws IOException {
        CopyController controller = new CopyController();
        controller.copy("D:/Crystel_Montero/Documents/Universidad/2022/I Semestre/Sistemas/Proyectos/Proyecto3/Nueva carpeta/Prueba.txt",
                "D:/Crystel_Montero/Documents/Universidad/2022/I Semestre/Sistemas/Proyectos/Proyecto3/Nuevacarpeta(2)", false);
    }
}
