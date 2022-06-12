/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memory;

/**
 *
 * @author Alexander
 */
public class sector {
    private int id;
    private int start;//subscript where it starts
    private int end;//subscript where it ends
    private boolean free;

    public sector(int id, int start, int end) {
        this.id = id;
        this.start = start;
        this.end = end;
        free = true;
    }

    public int getId() {
        return id;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean isFree() {
        return free;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
    
}
