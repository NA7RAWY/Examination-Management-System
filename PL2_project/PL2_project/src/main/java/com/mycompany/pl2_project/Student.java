package com.mycompany.pl2_project;
import java.io.*;
import java.util.*;

public class Student extends Person {
    private static int lastStudentId = 2022000;
    static {
        // Initialize lastStudentId by finding the maximum ID from the existing data in the file
        lastStudentId = findMaxStudentId();
    }
    
    private int studentId, studentid;

    public Student(String username, String password, String name) {
        super(username, password, name);
        this.studentId = ++lastStudentId;
        //this.studentid = --lastStudentId;
    }

    public int getStudentId() {
        return studentId;
    }
    
    private static int findMaxStudentId() {
        int maxId = 2022000;

        try (Scanner scanner = new Scanner(new File("C:\\PL2_project\\project_files\\students_files\\students.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                int currentId = Integer.parseInt(parts[3].trim()); // Assuming ID is the fourth element
                if (currentId > maxId) {
                    maxId = currentId;
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return maxId;
    }
    
    public static boolean login(String UsName, String pass) {
    try (Scanner input = new Scanner(new File("C:\\PL2_project\\project_files\\students_files\\students.txt"))) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String UsName_test = parts[0].trim();
                String pass_test = parts[1].trim();

                if (UsName.equals(UsName_test) && pass.equals(pass_test)) {
                    return true; // Match found, login successful
                }
            } else {
                System.out.println("Invalid data format in the file");
            }
        }
    } catch (FileNotFoundException ex) {
        System.out.println("Exception: " + ex.getMessage());
    }
    return false; // No matching username-password pair found
}
   public static boolean abletoenter(String studentid,String studentsubject){
       boolean able=false;
       try (Scanner input = new Scanner(new File("C:/PL2_projectproject_files/students_files/ST_subject.txt"))) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String id = parts[1].trim();
                String subject = parts[0].trim();
                String mark = parts[2].trim();

                if (id.equals(studentid) && subject.equals(studentsubject) &&(mark.equals("N/A") )) {
                    able=true;
                }
            }
        }
    } catch (FileNotFoundException ex) {
        System.out.println("Exception: " + ex.getMessage());
    }
       return able;
   }
   public static String enterexam(String studentid,String studentsubject) throws FileNotFoundException{
    String path = null;
     String examname = null;
      boolean examexist=false;
      try (Scanner input = new Scanner(new File("C:/PL2_project/project_files/exams_files/exams.txt"))) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                 examname = parts[0].trim();
               
                String subject = parts[2].trim();

                if (subject.equals(studentsubject)) {
                   examexist=true;
                   break;
                    
                }
            }
        }
      }
      path+="C:/PL2_project/project_files/exams_files/"+examname+".txt";
      if(abletoenter(studentid,studentsubject) && examexist){
          return path;
      }else{
   return "cannot enter!";
        }
      
       
   }
   public static String getmark(String studentid,String studentsubject) throws FileNotFoundException{
       String  degree="Not assigned to that course";
       String temp=null;
   try (Scanner input = new Scanner(new File("C:/PL2_project/project_files/students_files/ST_subject.txt"))) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String id = parts[1].trim();
                String subject = parts[0].trim();
                    temp=parts[2].trim();
                if (subject.equals(studentsubject) && studentid.equals(id)) {
                    degree=temp;
                    break;
                    
                }
            }
        }
      }
  return degree; 
}
     
    
}

