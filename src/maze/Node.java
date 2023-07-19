/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package maze;

/**
 *
 * @author rh200
 */
public class Node {
    
    public String name;
    public int x;
    public int y;
    public String leftNodeName;
    public String rightNodeName;
    public Node left;
    public Node right;
    public Node previous;
    
    public Node()
    {
        this.name = null;
        this.x = 0;
        this.y = 0;
        this.leftNodeName = null;
        this.rightNodeName = null;
        this.left = null;
        this.right = null;
        this.previous = null;
    }
    
    public Node(String name, int x, int y, String leftNodeName, String rightNodeName)
    {
        this.name = name;
        this.x = x;
        this.y = y;
        this.leftNodeName = leftNodeName;
        this.rightNodeName = rightNodeName;
        this.left = null;
        this.right = null;
        this.previous = null;
    }
}
