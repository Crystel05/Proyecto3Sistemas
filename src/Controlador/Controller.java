package Controlador;
import Modelo.Disco;

public class Controller {

    Disco disco = new Disco();
    
    public void createVirtualDisk(int sectors, int sectorSize) {
        disco.createVirtualDisk(sectors, sectorSize);
        
        disco.setTamanio(sectors*sectorSize);
    }

}
