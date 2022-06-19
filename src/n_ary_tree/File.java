/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n_ary_tree;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class File extends Element{

    private String data;
    private ArrayList<Integer> sectors;
    private LocalDate fechaCreacion;
    private LocalDate fechaModificacion;
    private Integer tamanio;

    public File(String name, String pData) {
        super(name);
        this.sectors = new ArrayList<>();
        this.data = pData;
    }
    
    public String getData() {
        return data;
    }

    public void setData(String pData) {
        this.data = pData;
    }
    
    public void setSectors(ArrayList<Integer> sectors) {
        this.sectors = sectors;
    }

    public ArrayList<Integer> getSectors() {
        return sectors;
    }
}
