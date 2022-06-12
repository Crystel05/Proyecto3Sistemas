/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package n_ary_tree;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Alexander
 */
public class Node {
    Element value; //para almacenar cualquier valor
    Node parent;//variable nodo padre
    private List<Node> children = new LinkedList<>();
    
    //Creando los constructores
    public Node()
    {
        this.value =  null;
        this.parent =  null;
    }
    public Node(Element pValue, Node pParent)
    {
        this.value =pValue;
        this.parent = pParent;
 
    }
    /*
        Getters and setters
    */
    public Element getValue()
    {
        return value;
    }

    public Node getParent() {
        return parent;
    }
    
    public List<Node> getChildren() {
        return children;
    }

    public void setValue(Element value) {
        this.value = value;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }
    
    /********************************************************/
    
    public void addChild(Node pChild) {
        this.children.add(pChild);
    }
}