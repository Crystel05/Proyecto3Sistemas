package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author yerli
 */
public class Disco {
    
    private Integer tamanio;
    private Integer disponibles;

    public Integer getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(Integer disponibles) {
        this.disponibles = disponibles;
    }

    public Integer getTamanio() {
        return tamanio;
    }

    public void setTamanio(Integer tamanio) {
        this.tamanio = tamanio;
    }
    
    public void createVirtualDisk(int sectors, int sectorSize) {
        try {
            this.disponibles = sectors;
            String path = "./virtualDisk.txt";
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < sectors; i++) {
                for (int j = 0; j < sectorSize; j++) {
                    bw.write("0");
                }
                bw.write("\n");
            }
            bw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
