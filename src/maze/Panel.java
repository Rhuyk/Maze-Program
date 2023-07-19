/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author xhu
 */
public class Panel extends JPanel{
    
    private JPanel buttonPanel;
    private JButton previousMaze;
    private JButton nextMaze;
    private FileManager fileManager;
    private int currentMazeNum;
    private Node root;
    private HashMap<String, Node> nodeMap;
    private Stack<Node> pathNodes;
    private ArrayList<String> pathNodeNames;
    
    //Panel Contructor
    public Panel()
    {
        this.currentMazeNum = 1;
        this.root = null;
        this.nodeMap = null;
        this.pathNodes = null;
        this.pathNodeNames = null;
        
        loadMazeFile(currentMazeNum);
        buttonPanel();
        enableValidButtons();
    }
    
    //GUI Paint Component
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        //paints the name of the maze file
        g.setColor(Color.BLACK);
        g.drawString(fileManager.name, 320, 50);
        
        //paints all the nodes, paths, and nodes' names (blue)
        Set eSet = nodeMap.keySet();
        Iterator it = eSet.iterator();
        while (it.hasNext())
        {
            String nodeName = (String) it.next();
            Node currentNode = nodeMap.get(nodeName);
            int x = currentNode.x * 80 + 100;
            int y = currentNode.y * 80 + 100;
            g.setColor(Color.BLUE);
            g.fillOval(x, y, 10, 10);
            
            g.setColor(Color.BLUE);
            g.drawString(currentNode.name, x-10, y-2);
            
            if (currentNode.left != null)
            {
                int x_left = currentNode.left.x * 80 + 100;
                int y_left = currentNode.left.y * 80 + 100;
                g.drawLine(x+5, y+5, x_left+5, y_left+5);
            }
            if (currentNode.right != null)
            {
                int x_right = currentNode.right.x * 80 + 100;
                int y_right = currentNode.right.y * 80 + 100;
                g.drawLine(x+5, y+5, x_right+5, y_right+5);
            }
        }
        
        //paints the path (green) to the exit. Advance step by step after repaint() is called.
        Node currentNode = pathNodes.pop();
        pathNodeNames.add(currentNode.name);
        for (int i = 0; i < pathNodeNames.size(); i++)
        {
            if (i + 1 < pathNodeNames.size())
            {
                Node currentPathNode = nodeMap.get(pathNodeNames.get(i));
                Node nextPathNode = nodeMap.get(pathNodeNames.get(i+1));
                
                int x = currentPathNode.x * 80 + 100;
                int y = currentPathNode.y * 80 + 100;
                int x_next = nextPathNode.x * 80 + 100;
                int y_next = nextPathNode.y * 80 + 100;
                g.setColor(Color.GREEN);
                g.drawLine(x+5, y+5, x_next+5, y_next+5);
                
                g.setColor(Color.BLUE);
                g.fillOval(x, y, 10, 10);
                g.setColor(Color.BLUE);
                g.fillOval(x_next, y_next, 10, 10);
            }
        }
        
        //pause for 1 second
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        //paints the current node (green) that is going through the path to the exit, and call repaint()
        if(!pathNodes.empty())
        {
            int x = currentNode.x * 80 + 100;
            int y = currentNode.y * 80 + 100;
            
            g.setColor(Color.GREEN);
            g.fillOval(x+1, y+1, 8, 8);
        
            repaint();
        }
        
        //paints exit node red
        int x = nodeMap.get("EXIT").x * 80 + 100;
        int y = nodeMap.get("EXIT").y * 80 + 100;
        g.setColor(Color.RED);
        g.fillOval(x+1, y+1, 8, 8);
    }
    
    //creates buttons for maze switching
    private void buttonPanel()
    {
        previousMaze = new JButton("Previous maze");
        previousMaze.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMazeNum--;
                loadMazeFile(currentMazeNum);
                enableValidButtons();
                repaint();
            }
        });
        
        nextMaze = new JButton("Next maze");
        nextMaze.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentMazeNum++;
                loadMazeFile(currentMazeNum);
                enableValidButtons();
                repaint();
            }
        });
        
        buttonPanel = new JPanel();
        buttonPanel.add(previousMaze);
        buttonPanel.add(nextMaze);
        setLayout(new BorderLayout());
        this.add(buttonPanel, BorderLayout.NORTH);
    }
    
    //enables valid buttons based on existing mazes
    private void enableValidButtons()
    {
        if (checkMazeFileExistence(currentMazeNum -1))
        {
            previousMaze.setEnabled(true);
        }
        else
        {
            previousMaze.setEnabled(false);
        }
        if (checkMazeFileExistence(currentMazeNum +1))
        {
            nextMaze.setEnabled(true);
        }
        else
        {
            nextMaze.setEnabled(false);
        }
    }
    
    //loads the maze file
    private void loadMazeFile(int mazeNumber)
    {
        String fileName = "Maze" + mazeNumber + ".txt";
        if(!checkMazeFileExistence(mazeNumber))
        {
            return;
        }
        fileManager = new FileManager(fileName);
        fileManager.readFile(fileName);
        constructBinaryTree();
    }
    
    //checks if the maze file exist
    private boolean checkMazeFileExistence(int mazeNumber)
    {
        String fileName = "Maze" + mazeNumber + ".txt";
        File file = new File(fileName);
        if(file.exists())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //reads all the nodes and details into nodeMap from the maze file
    private void readNodesFromFile()
    {
        for (int i = 1; i < fileManager.lineData.length; i++)
        {
            String fileLine = fileManager.lineData[i];
            String[] parts = fileLine.split(",");
            
            String nodeName = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            String leftNodeName = parts[3];
            String rightNodeName = parts[4];
            
            Node node = new Node(nodeName, x, y, leftNodeName, rightNodeName);
            this.nodeMap.put(nodeName, node);
        }
    }
    
    //constructs a binary tree from the nodeMap and stacks the exit path nodes
    private void constructBinaryTree()
    {
        this.root = null;
        this.nodeMap = new HashMap<String, Node>();
        this.pathNodeNames = new ArrayList<>();
        
        readNodesFromFile();
        this.root = nodeMap.get("START");
        addNode(this.root);
        this.pathNodes = new Stack<Node>();
        setMazePath(nodeMap.get("EXIT"));
    }
    
    //generates a binary tree from nodeMap using this recursive addNode() method
    private void addNode(Node root)
    {
        if (!root.leftNodeName.equals("A"))
        {
            if (root.leftNodeName.equals("W"))
            {
                root.left = nodeMap.get("EXIT");
            } 
            else if (root.left == null)
            {
                root.left = nodeMap.get(root.leftNodeName);
                addNode(root.left);
            }
            root.left.previous = root;
        }
        if (!root.rightNodeName.equals("A"))
        {
            if (root.rightNodeName.equals("W"))
            {
                root.right = nodeMap.get("EXIT");
            } 
            else if (root.right == null)
            {
                root.right = nodeMap.get(root.rightNodeName);
                addNode(root.right);
            }
            root.right.previous = root;
        }
    }
    
    //stacks the path nodes from EXIT to START
    private void setMazePath(Node exitNode)
    {
        if (exitNode != null)
        {
            pathNodes.add(exitNode);
            setMazePath(exitNode.previous);
        }
    }
}
