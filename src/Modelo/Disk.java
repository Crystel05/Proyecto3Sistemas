package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author yerli
 */
public class Disk {

    private static Disk disk;

    public static Disk getInstance(){
        if (disk == null){
            disk = new Disk();
        }
        return disk;
    }
    
    private Integer size;
    private Integer freeSpace;

    public Integer getFreeSpace() {
        return freeSpace;
    }

    public void setFreeSpace(Integer freeSpace) {
        this.freeSpace = freeSpace;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
        setFreeSpace(size);
    }
    
    public void createVirtualDisk(int sectors, int sectorSize) {
        try {
            String path = "src/Files/Disk/virtualDisk.txt";
            File file = new File(path);
            this.freeSpace = sectors;
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
