/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n_ary_tree;

/**
 *
 * @author Alexander
 */
public class file extends Element{

    private String data;
    public file(String name, String pData) {
        super(name);
        this.data = pData;
    }
    
    public String getData() {
        return data;
    }

    public void setData(String pData) {
        this.data = pData;
    }
}
