/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.pl2_project;
import java.io.*;
import java.util.*;

/**
*
*@author DELL
*/
public class Report {
  
    public static void exportreport(String subject,String studentid,String context){
       String filePath = "C:/PL2_project/project_files/report_files/"+subject+"_"+studentid+".txt";
         
        try 
      {
            File file = new File(filePath);

            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("File created successfully!");
                } else {
                    System.out.println("Failed to create the file!");
                }
            } else {
                System.out.println("File already exists.");
            }

            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write some content to the file
            bufferedWriter.write(context);

            // Close the BufferedWriter to flush and close the file
            bufferedWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
   }
   
}
