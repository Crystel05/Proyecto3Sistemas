/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memory;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class MemoryHandler {
    private static final String simulationPath ="./Simulacion File System";
    public static int numSectors;
    public static int lenghtXSector;
    public static List<Sector> Sectors = new LinkedList<>();
    
    public static void setData(int pNumSectors, int pLenghtXSector) {
        numSectors = pNumSectors;
        lenghtXSector = pLenghtXSector;
        int count=0;
        for (int i = 0; i < numSectors; i++) {
            Sectors.add(new Sector(i, count, count+lenghtXSector-1));
            count+=lenghtXSector;
        }
    }

    public static String getSimulationPath(){
        return simulationPath;
    }

    public static int getNumSectors() {
        return numSectors;
    }

    public static int getLenghtXSector() {
        return lenghtXSector;
    }

    public static List<Sector> getSectors() {
        return Sectors;
    }

    
    
}
