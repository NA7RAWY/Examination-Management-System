package com.mycompany.pl2_project;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lecturer extends Person {
    private static int lastLecturerId = 1000;
    static {
        // Initialize lastLecturerId by finding the maximum ID from the existing data in the file
        lastLecturerId = findMaxLecturerId();
    }
    
    private int lecturerId;

    public Lecturer(String username, String password, String name) {
        super(username, password, name);
        this.lecturerId = ++lastLecturerId;
    }

    public int getLecturerId() {
        return lecturerId;
    }
    
    private static int findMaxLecturerId() {
        int maxId = 1000;

        try (Scanner scanner = new Scanner(new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt"))) {
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
    try (Scanner input = new Scanner(new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt"))) {
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
 public static boolean add_exam(int lecturerId,String subjectٍٍ) {
        String fileName = getUserInput("Enter the exam name: ");
        String filePath = "C:/PL2_project/project_files/exams_files/";

        // Check if the directory exists, create it if not
        File directory = new File(filePath);
        if (!directory.exists()) {
            directory.mkdirs();  // creates the necessary directories
        }

        try {
            // Use FileWriter with append mode (true) to avoid overwriting the existing content
            BufferedWriter exams = new BufferedWriter(new FileWriter("C:/PL2_project/project_files/exams_files/exams.txt", true));

            // Use a Scanner to read the existing file
            Scanner fileRead = new Scanner(new File("C:/PL2_project/project_files/exams_files/exams.txt"));

            boolean duplicate = false;
            String line;
            String id = Integer.toString(lecturerId);

            // Check for duplicates in the existing file
            while (fileRead.hasNextLine()) {
                line = fileRead.nextLine();
                if (line.equals(fileName + "," + id+ "," +subjectٍٍ)) {
                    duplicate = true;
                    break;
                }
            }

            // Close the Scanner after reading
            fileRead.close();

            if (duplicate) {
                // Duplicate found, return false
                return false;
            } else {
            
                // No duplicate, add the new entry to the exams file
                exams.write(fileName + "," + id+ "," +subjectٍٍ);
                exams.newLine(); // Add a newline character to separate entries
                exams.close(); // Close the BufferedWriter after writing

                // Create the text file for the exam
                Menus.createTextFile(fileName, filePath);
                
                // Return true to indicate success
                return true;
            }
        } catch (IOException ex) {
            Logger.getLogger(Lecturer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Return true in case of an exception (you may want to handle this differently)
        return true;
    }
   private static String getUserInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(prompt);
        return scanner.nextLine();
    }
   public static boolean isSubjectAssignedToLecturer(String subjectCode, int lecturerID) throws IOException {
    // Define the file path for the lecturer-subject assignment file
    File lecturerSubjectFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\L_subject.txt");

    // Using try-with-resources to automatically close the Scanner
    try (Scanner scanner = new Scanner(lecturerSubjectFile)) {
        // Loop through each line in the file
        while (scanner.hasNextLine()) {
            // Read a line from the file
            String line = scanner.nextLine();

            // Split the line into parts using a comma as a delimiter
            String[] parts = line.split(",");

            // Check if the line has at least two parts and matches the subject code and lecturer ID
            if (parts.length >= 2 && parts[0].trim().equals(subjectCode) && Integer.parseInt(parts[1].trim()) == lecturerID) {
                // Subject is already assigned to the lecturer
                return true;
            }
        }
    } catch (IOException e) {
        // Handle IOException by printing a message
        System.out.println("Exception: " + e.getMessage());
    }

    // Subject is not assigned to the lecturer
    return false;
}

   public static void deleteLineFromFile(String filePath, String lineToDelete) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File("tempFile.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                if (currentLine.equals(lineToDelete)) {
                    // Skip the line to delete
                    continue;
                }
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }

        // Replace the original file with the temp file
        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("Failed to rename temp file to original file");
            }
        } else {
            throw new IOException("Failed to delete original file");
        }
    }
public static boolean deleteExam(String examname,String subjectname, int lecturerId) throws IOException {

        // Specify the path of the text file to be deleted
        String filePath = "C:/PL2_project/project_files/exams_files/" + examname + ".txt";
        String examsfile = "C:/PL2_project/project_files/exams_files/exams.txt";
        
        // Create a File object representing the file
        File fileToDelete = new File(filePath);
        try {
            
             // Check if the file exists before attempting to delete
            if (fileToDelete.exists()) {
                if (fileToDelete.delete()) {
                    deleteLineFromFile(examsfile,examname+","+lecturerId+","+subjectname);
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SecurityException e) {
            // Handle security-related exceptions
            e.printStackTrace();
            return false;
        }
    }

 
}
