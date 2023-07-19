/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author xhu
 */
public class FileManager {
    public String name;
    public int numberOfLines;
    public String[] lineData;
    public String data="";
    
    public FileManager()
    {
        numberOfLines = 0;
    }        
    
    public FileManager(String fileName)
    {
        this.name = fileName;
        numberOfLines = 0;
        File f = new File(name);
        try
        {
            Scanner myScanner = new Scanner(f);
            while(myScanner.hasNextLine())
            {
                myScanner.nextLine();
                numberOfLines++;
            }  
            myScanner.close();
        }
        catch(IOException e)
        {
            System.out.println("Cannot read the file"+e.getMessage());
        }
        lineData = new String[numberOfLines];
    }
     public void readFile(String fileName)
    {
        File f;
        if(fileName == null)
            f = new File(this.name);
        else
            f = new File(fileName);
        try
        {
            Scanner myScanner = new Scanner(f);
            int lineNum = 0;
            while(myScanner.hasNextLine())
            {
                String line = myScanner.nextLine();
                //System.out.println(line);
                lineData[lineNum] = line;
                data += line;
                lineNum++;
                
            }  
            myScanner.close();
        }
        catch(IOException e)
        {
            System.out.println("Cannot read the file"+e.getMessage());
        }
    }
    
    public void writeFile(String fileName, String c)
    {
        File f;
        
        if(fileName == null)
            f = new File(this.name);
        else
            f = new File(fileName);
        try
        {
            f.createNewFile();
            FileWriter writer = new FileWriter(f); 
            writer.write(c);
            writer.flush();
            writer.close();
        }
        catch(IOException e)
        {
            System.out.println("Cannot write to the file"+e.getMessage());
        }
    }
}
