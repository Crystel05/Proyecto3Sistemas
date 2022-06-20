package Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author yerli
 */
public class Disk {

    private static final String simulationPath ="./Simulacion File System";
    private static Disk disk;
    private String pathActualDisk;
    private Integer freeSpace;
    private Integer sectorSize;
    private List<Integer> sectors = new ArrayList<>();

    public static Disk getInstance(){
        if (disk == null){
            disk = new Disk();
        }
        return disk;
    }

    public static String getSimulationPath() {
        return simulationPath;
    }

    public Integer getFreeSpace() {
        return freeSpace;
    }

    public void substractFreeSpace () {
        this.freeSpace = freeSpace -1;
    }

    public String getPathActualDisk() {
        return pathActualDisk;
    }

    public List<Integer> getSectors() {
        return sectors;
    }

    public void addTo(Integer sector) {
        this.sectors.add(sector);
    }

    public Integer getSectorSize() {
        return sectorSize;
    }

    public void createVirtualDisk(int sectors, int sectorSize) {
        try {
            memory.MemoryHandler.lenghtXSector = sectorSize;
            memory.MemoryHandler.numSectors = sectors;
            System.out.println(sectors + "  "+  sectorSize);
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss");
            Date date = new Date();
            String dateToStr = dateFormat.format(date);
            String path = "src/Files/Disk/virtualDisk" +  dateToStr + ".txt";
            pathActualDisk = path;
            this.sectorSize = sectorSize;
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
