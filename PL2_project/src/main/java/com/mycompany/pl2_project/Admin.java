package com.mycompany.pl2_project;

import java.io.*;
import java.util.*;


public class Admin extends Person{
    
    public Admin(String username, String password, String name) {
        super(username, password, name);
    } 
//    Admin admin = new Admin("islam123","i123m","islam_zakria");

    // admin login method
    public static boolean login(String UsName, String pass){
          String UsName_test="";
          String pass_test="";
          File adUs_File = new File("C:\\PL2_project\\project_files\\admin_files\\ad_username.txt");
          File adPa_File = new File("C:\\PL2_project\\project_files\\admin_files\\ad_password.txt");
        try {
            Scanner adUs = new Scanner(adUs_File);
            Scanner adPa = new Scanner(adPa_File);
            //here i put the info in the file in variables to compare with the inputs the user gave to me 
            while(adUs.hasNextLine()){
                UsName_test +=  adUs.nextLine();
            }
            while(adPa.hasNextLine()){
                pass_test +=  adPa.nextLine();
            }
            adUs.close();
            adPa.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Exception" + ex.getMessage());// here if any problem with files the exception message will appear
        }  
        // here if all data from the user is true the method will return true else will return false 
        // with strings we use .equals not ==
        return UsName.equals(UsName_test) && pass.equals(pass_test); 
    }
    //change admin password
    public static boolean changePass(String OldPassword,String NewPassword) throws IOException{
            String pass_test="";
            File adPa_File = new File("C:\\PL2_project\\project_files\\admin_files\\ad_password.txt");
            try{
                Scanner adPa = new Scanner(adPa_File);
                while(adPa.hasNextLine()){
                pass_test +=  adPa.nextLine();
            }
                adPa.close();
            } catch (FileNotFoundException ex) {
            System.out.println("Exception" + ex.getMessage());
        }
            if(!(pass_test.equals(OldPassword))){
                return false;
            }
            else{   
                try{
                    PrintWriter clearing_file = new PrintWriter(adPa_File);
                    // here i cleared the file and make it empty
                    clearing_file.print("");
                    clearing_file.close();
                
                }catch (FileNotFoundException ex) {
                    System.out.println("Exception" + ex.getMessage());
        }
               try{
                   // here i added the new password in the empty file 
                    FileWriter adding_cont = new FileWriter(adPa_File);
                    adding_cont.append(NewPassword);
                    adding_cont.close();
                }catch (FileNotFoundException ex) {
                    System.out.println("Exception" + ex.getMessage());
        } 
                return true;
            }
    }
    
    
    public static boolean addStudent(String username, String password, String name) throws IOException{
        try{
        // Use try-with-resources to automatically close resources
        try (PrintWriter studentPrintWriter = new PrintWriter(new FileWriter("C:\\PL2_project\\project_files\\students_files\\students.txt", true))) {
            Student newStudent = new Student(username, password, name);
            studentPrintWriter.println(username + "," + password + "," + name + "," + newStudent.getStudentId());
            return true;
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }    
    }
    
    
    public static boolean deleteStudent(int studentId) throws IOException {
        File inputFile = new File("C:\\PL2_project\\project_files\\students_files\\students.txt");
        File tempFile = new File("C:\\PL2_project\\project_files\\students_files\\tempStudents.txt");
        
        try (Scanner scanner = new Scanner(inputFile);
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Check if the line contains the student with the specified ID
                if (parts.length >= 4 && Integer.parseInt(parts[3]) == studentId) {
                    found = true;
                    continue; // Skip writing this line to the temporary file
                }

                // Write the line to the temporary file
                writer.println(line);
            }

            if (!found) {
                System.out.println("Student with ID " + studentId + " not found.");
                return false; // Indicate failure
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Exception: " + e.getMessage());
            return false; // Indicate failure
        }

        // Replace the original file with the temporary file
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Failed to update the file.");
            return false; // Indicate failure
        }

        System.out.println("Student with ID " + studentId + " deleted successfully.");
        return true; // Indicate success
    }
    public static boolean updateStudent(int studentId, String newUsername, String newPassword, String newName) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\students_files\\students.txt");
    File tempFile = new File("C:\\PL2_project\\project_files\\students_files\\tempStudents.txt");

    try (Scanner scanner = new Scanner(inputFile);
         PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

        boolean found = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the line contains the student with the specified ID
            if (parts.length >= 4 && Integer.parseInt(parts[3]) == studentId) {
                found = true;
                // Update the information and write the updated line to the temporary file
                writer.println(newUsername + "," + newPassword + "," + newName + "," + studentId);
            } else {
                // Write the unchanged line to the temporary file
                writer.println(line);
            }
        }

        if (!found) {
            System.out.println("Student with ID " + studentId + " not found.");
            return false; // Indicate failure
        }

    } catch (IOException | NumberFormatException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }

    // Replace the original file with the temporary file
    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        System.out.println("Failed to update the file.");
        return false; // Indicate failure
    }

    System.out.println("Student with ID " + studentId + " updated successfully.");
    return true; // Indicate success
}
 public static boolean addLecturer(String username, String password, String name) throws IOException {
    try {
        // Use try-with-resources to automatically close resources
        try (PrintWriter lecturerPrintWriter = new PrintWriter(new FileWriter("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt", true))) {
            Lecturer newLecturer = new Lecturer(username, password, name);
            lecturerPrintWriter.println(username + "," + password + "," + name + "," + newLecturer.getLecturerId());
            return true;
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }
}
 public static boolean deleteLecturer(int lecturerId) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt");
    File tempFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\tempLecturers.txt");

    try (Scanner scanner = new Scanner(inputFile);
         PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

        boolean found = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the line contains the lecturer with the specified ID
            if (parts.length >= 4 && Integer.parseInt(parts[3]) == lecturerId) {
                found = true;
                continue; // Skip writing this line to the temporary file
            }

            // Write the line to the temporary file
            writer.println(line);
        }

        if (!found) {
            System.out.println("Lecturer with ID " + lecturerId + " not found.");
            return false; // Indicate failure
        }

    } catch (IOException | NumberFormatException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }

    // Replace the original file with the temporary file
    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        System.out.println("Failed to update the file.");
        return false; // Indicate failure
    }

    System.out.println("Lecturer with ID " + lecturerId + " deleted successfully.");
    return true; // Indicate success
}
public static boolean updateLecturer(int lecturerId, String newUsername, String newPassword, String newName) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt");
    File tempFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\tempLecturers.txt");

    try (Scanner scanner = new Scanner(inputFile);
         PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

        boolean found = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the line contains the lecturer with the specified ID
            if (parts.length >= 4 && Integer.parseInt(parts[3]) == lecturerId) {
                found = true;
                // Update the information and write the updated line to the temporary file
                writer.println(newUsername + "," + newPassword + "," + newName + "," + lecturerId);
            } else {
                // Write the unchanged line to the temporary file
                writer.println(line);
            }
        }

        if (!found) {
            System.out.println("Lecturer with ID " + lecturerId + " not found.");
            return false; // Indicate failure
        }

    } catch (IOException | NumberFormatException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }

    // Replace the original file with the temporary file
    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
        System.out.println("Failed to update the file.");
        return false; // Indicate failure
    }

    System.out.println("Lecturer with ID " + lecturerId + " updated successfully.");
    return true; // Indicate success
}

 public static boolean addSubjects(String subjectName, String subjectCode) throws IOException {
        File subjectsFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");

        // Validate if either subject code or subject name already exists

        try (PrintWriter subjectPrintWriter = new PrintWriter(new FileWriter(subjectsFile, true))) {
            Subject newSubject = new Subject(subjectName, subjectCode);
            subjectPrintWriter.println(subjectName + "," + subjectCode);
            return true;
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return false; // Indicate failure
        }
}
public static boolean deleteSubject(String subjectCode) throws IOException {
        File inputFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");
        File tempFile = new File("C:\\PL2_project\\project_files\\subjects\\tempSubjects.txt");

        try (Scanner scanner = new Scanner(inputFile);
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

//            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Check if the line contains the subject with the specified code
                if (parts.length >= 2 && parts[1].equals(subjectCode)) {
//                    found = true;
                    continue; // Skip writing this line to the temporary file
                }

                // Write the line to the temporary file
                writer.println(line);
            }

//            if (!found) {
//                System.out.println("Subject with code " + subjectCode + " not found.");
//                return false; // Indicate failure
//            }

        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
            return false; // Indicate failure
        }

        // Replace the original file with the temporary file
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
//            System.out.println("Failed to update the file.");
            return false; // Indicate failure
        }

        System.out.println("Subject with code " + subjectCode + " deleted successfully.");
        return true; // Indicate success
    }
public static boolean updateSubject(String subjectCode, String newSubjectCode, String newSubjectName) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");
    File tempFile = new File("C:\\PL2_project\\project_files\\subjects\\tempSubjects.txt");

    try (Scanner scanner = new Scanner(inputFile);
         PrintWriter writer = new PrintWriter(new FileWriter(tempFile))) {

//        boolean found = false;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the line contains the subject with the specified code
            if (parts.length >= 2 && parts[1].equals(subjectCode)) {
                //found = true;
                // Update the information and write the updated line to the temporary file
                writer.println(newSubjectName + "," + newSubjectCode);
            } else {
                // Write the unchanged line to the temporary file
                writer.println(line);
            }
        }

//        if (!found) {
//            System.out.println("Subject with code " + subjectCode + " not found.");
//            return false; // Indicate failure
//        }

    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }

    // Replace the original file with the temporary file
    if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
//        System.out.println("Failed to update the file.");
        return false; // Indicate failure
    }

//    System.out.println("Subject with code " + subjectCode + " updated successfully.");
    return true; // Indicate success
}




public static boolean assignSubjecttoStudent(String subjectCode, int studentID) throws IOException {
    // Check if the subject exists before assigning
    if (!isSubjectExisting(subjectCode)) {
        System.out.println("Subject does not exist. Please add the subject first.");
        return false;
    }

    // Check if the subject is already assigned to the student
    if (isSubjectAssignedToStudent(subjectCode, studentID)) {
        System.out.println("Subject is already assigned to the student.");
        return false;
    }

    try {
        // Use try-with-resources to automatically close resources
        try (PrintWriter subjectPrintWriter = new PrintWriter(new FileWriter("C:\\PL2_project\\project_files\\students_files\\ST_subject.txt", true))) {
            subjectPrintWriter.println(subjectCode + "," + studentID+","+"N/A");
            return true;
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }
}

private static boolean isSubjectAssignedToStudent(String subjectCode, int studentID) throws IOException {
    File studentSubjectFile = new File("C:\\PL2_project\\project_files\\students_files\\ST_subject.txt");

    try (Scanner scanner = new Scanner(studentSubjectFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[0].trim().equals(subjectCode) && Integer.parseInt(parts[1].trim()) == studentID) {
                return true; // Subject is already assigned to the student
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return false; // Subject is not assigned to the student
}



public static boolean assignSubjecttolecturer(String subjectCode, int lecturerID) throws IOException {
    // Check if the subject exists before assigning
    if (!isSubjectExisting(subjectCode)) {
        System.out.println("Subject does not exist. Please add the subject first.");
        return false;
    }

    // Check if the subject is already assigned to the lecturer
    if (isSubjectAssignedToLecturer(subjectCode, lecturerID)) {
        System.out.println("Subject is already assigned to the lecturer.");
        return false;
    }

    try {
        // Use try-with-resources to automatically close resources
        try (PrintWriter subjectPrintWriter = new PrintWriter(new FileWriter("C:\\PL2_project\\project_files\\lecturers_files\\L_subject.txt", true))) {
            subjectPrintWriter.println(subjectCode + " , " + lecturerID);
            return true;
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
        return false; // Indicate failure
    }
}

private static boolean isSubjectAssignedToLecturer(String subjectCode, int lecturerID) throws IOException {
    File lecturerSubjectFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\L_subject.txt");

    try (Scanner scanner = new Scanner(lecturerSubjectFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[0].trim().equals(subjectCode) && Integer.parseInt(parts[1].trim()) == lecturerID) {
                return true; // Subject is already assigned to the lecturer
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return false; // Subject is not assigned to the lecturer
}

private static boolean isSubjectExisting(String subjectCode) throws IOException {
    File subjectsFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");

    try (Scanner scanner = new Scanner(subjectsFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[1].equals(subjectCode)) {
                return true; // Subject with the specified code exists
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return false; // Subject with the specified code does not exist
}

}

