/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author xhu
 */
public class Maze {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame("Maze");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel panel = new Panel();
        frame.add(panel);
        frame.setSize(700, 600);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    
}
