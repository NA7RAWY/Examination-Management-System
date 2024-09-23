package com.mycompany.pl2_project;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

public abstract class Menus {

    public static void MainMenu() throws IOException {
        System.out.print("Please enter:\n1 if you are Admin\n2 if you are Lecturer\n3 if you are Student\n4 to Exit\nYour choice is : ");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();


        switch (choice) {
            case 1:
                handleAdminLogin();
                AdminMenu();
                break;
            case 2:
                handleLecturerLogin();
                LecturerMenu();
                break;
            case 3:
                handleStudentLogin();
                StudentMenu();
                break;
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice, Please enter a valid option.");
        }
    }

    public static void handleAdminLogin() {
        Scanner input = new Scanner(System.in);
        boolean loginSuccessful = false;

        do {
            System.out.print("Please enter your username: ");
            String adminUsername = input.nextLine();
            System.out.print("Please enter your password: ");
            String adminPassword = input.nextLine();

            if (Admin.login(adminUsername, adminPassword)) {
                System.out.println("Logged in successfully");
                loginSuccessful = true;
            } else {
                System.out.println("Login failed, Please enter the information again in the right way.");
            }
        } while (!loginSuccessful);
    }

    public static void AdminMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter:\n1 If you want to change the password\n2 To manage lecturers\n3 To manage students\n4 To manage subjects\n5 Exit\nYour choice: ");
            int adminChoice = input.nextInt();

            switch (adminChoice) {
                case 1:
                    handleAdminChangePassword();
                    break;
                case 2:
                    AdminLecturerMenu();// Add code for managing lecturers
                    break;
                case 3:
                    AdminStudentMenu(); // Call the method for managing students
                    break;
                case 4:
                    AdminSubjectMenu();//Call the method for managing subjects
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    public static void AdminStudentMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter:\n1 If you want to add students"
                    + "\n2 If you want to delete students"
                    + "\n3 If you want to display all students"
                    + "\n4 If you want to update information"
                    + "\n5 If you want to search"
                    + "\n6 Exit"
                    + "\nYour choice: ");

            int studentChoice = input.nextInt();

            switch (studentChoice) {
                case 1:
                    adminAddStudents(); // Call the method for adding students
                    break;
                case 2:
                    admindeleteStudent(); // Call the method for deleting students
                    break;
                case 3:
                    displayAllStudents(); // Call the method for listing students
                    break;
                case 4:
                    adminUpdateStudents(); // Call the method for Updating students
                    break;
                case 5:
                    adminSearchStudents();
                    break;
                case 6:
                    return; // Return from the method to go back to the previous menu
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    public static void handleAdminChangePassword() throws IOException {
        Scanner input = new Scanner(System.in);
        boolean changeSuccessful = false;

        do {
            System.out.print("Please enter the old password: ");
            String adminOldPassword = input.nextLine();
            System.out.print("Please enter the new password: ");
            String adminNewPassword = input.nextLine();

            if (adminOldPassword.equals(adminNewPassword)) {
                System.out.println("New password and old password can't be the same");
            } else {

                if (Admin.changePass(adminOldPassword, adminNewPassword)) {
                    System.out.println("Password changed successfully");
                    changeSuccessful = true;
                } else {
                    System.out.println("The old password is wrong. Please enter the information again.");
                }
            }
        } while (!changeSuccessful);
    }

    public static void adminAddStudents() throws IOException {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the new student's username: ");
    String ST_username = input.nextLine();

    // Check if the username already exists
    if (isUsernameUnique(ST_username)) {
        System.out.print("Enter the new student's password: ");
        String ST_password = input.nextLine();

        System.out.print("Enter the new student's name: ");
        String ST_name = input.nextLine();

        if (Admin.addStudent(ST_username, ST_password, ST_name)) {
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Adding a student failed. Please enter the information again in the right way.");
        }
    } else {
        System.out.println("Username already exists. Please choose a unique username.");
    }
}

    public static boolean isUsernameUnique(String username) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\students_files\\students.txt");

    try (Scanner scanner = new Scanner(inputFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the username already exists
            if (parts.length >= 1 && parts[0].equals(username)) {
                return false; // Username is not unique
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return true; // Username is unique
}

    public static void admindeleteStudent() throws IOException {
    Scanner input = new Scanner(System.in);

    // Display all students with their data
   
    displayAllStudents();

    System.out.print("Enter the student's ID to delete: ");
    int ST_id = input.nextInt();

    if (Admin.deleteStudent(ST_id)) {
        System.out.println("Student deleted successfully.");
    } else {
        System.out.println("Deleting a student failed. Please enter the information again in the right way.");
    }
}

    public static void displayAllStudents() throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\students_files\\students.txt");

    try (Scanner scanner = new Scanner(inputFile)) {
        if (!scanner.hasNextLine()) {
            System.out.println("No students found.");
            return; // No need to proceed further
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length >= 4) {
                System.out.println("Name: " + parts[2] + ", Username: " + parts[0] + ", Student ID: " + parts[3]);
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }
}

    
    public static void adminUpdateStudents() throws IOException {
    Scanner input = new Scanner(System.in);

    // Display all students with their data
    displayAllStudents();

    System.out.print("Enter the student's ID to update: ");
    int ST_id = input.nextInt();

    if (isStudentExists(ST_id)) {
        System.out.print("Enter the new username: ");
        String newUsername = input.next();

        System.out.print("Enter the new password: ");
        String newPassword = input.next();

        System.out.print("Enter the new name: ");
        String newName = input.next();

        if (Admin.updateStudent(ST_id, newUsername, newPassword, newName)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Updating student failed. Please enter the information again in the right way.");
        }
    } else {
        System.out.println("Student with ID " + ST_id + " not found.");
    }
}
    public static boolean isStudentExists(int studentId) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\students_files\\students.txt");

    try (Scanner scanner = new Scanner(inputFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the line contains the student with the specified ID
            if (parts.length >= 4 && Integer.parseInt(parts[3]) == studentId) {
                return true; // Student exists
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return false; // Student does not exist
}
    
    
    public static void adminSearchStudents() throws IOException {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the student's name to search: ");
    String searchName = input.nextLine();

    ArrayList<String> matchingStudents = searchStudentsByName(searchName);

    if (matchingStudents.isEmpty()) {
        System.out.println("No students found with the name '" + searchName + "'.");
    } else {
        System.out.println("Matching students:");
        for (String studentInfo : matchingStudents) {
            System.out.println(studentInfo);
        }
    }
}

    public static ArrayList<String> searchStudentsByName(String searchName) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\students_files\\students.txt");
    ArrayList<String> matchingStudents = new ArrayList<>();

    try (Scanner scanner = new Scanner(inputFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length >= 3 && parts[2].equalsIgnoreCase(searchName)) {
                matchingStudents.add("Name: " + parts[2] + ", Username: " + parts[0] + ", Student ID: " + parts[3]);
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return matchingStudents;
}

    public static void AdminLecturerMenu() throws IOException {
    Scanner input = new Scanner(System.in);

    while (true) {
        System.out.print("Please enter:\n1 If you want to add lecturers"
                + "\n2 If you want to delete lecturers"
                + "\n3 If you want to display all lecturers"
                + "\n4 If you want to update information"
                + "\n5 If you want to search"
                + "\n6 Exit"
                + "\nYour choice: ");

        int lecturerChoice = input.nextInt();

        switch (lecturerChoice) {
            case 1:
                adminAddLecturers(); // Call the method for adding lecturers
                break;
            case 2:
                adminDeleteLecturer(); // Call the method for deleting lecturers
                break;
            case 3:
                displayAllLecturers(); // Call the method for listing lecturers
                break;
            case 4:
                adminUpdateLecturers(); // Call the method for updating lecturers
                break;
            case 5:
                adminSearchLecturers(); // Call the method for searching lecturers
                break;
            case 6:
                return; // Return from the method to go back to the previous menu
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }
}

   
    public static void adminAddLecturers() throws IOException {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the new lecturer's username: ");
    String LT_username = input.nextLine();

    // Check if the username already exists
    if (isUsernameUnique(LT_username)) {
        System.out.print("Enter the new lecturer's password: ");
        String LT_password = input.nextLine();

        System.out.print("Enter the new lecturer's name: ");
        String LT_name = input.nextLine();

        if (Admin.addLecturer(LT_username, LT_password, LT_name)) {
            System.out.println("Lecturer added successfully.");
        } else {
            System.out.println("Adding a lecturer failed. Please enter the information again correctly.");
        }
    } else {
        System.out.println("Username already exists. Please choose a unique username.");
    }
}
    public static void adminDeleteLecturer() throws IOException {
    Scanner input = new Scanner(System.in);

    // Display all lecturers with their data
    displayAllLecturers();

    System.out.print("Enter the lecturer's ID to delete: ");
    int LT_id = input.nextInt();

    if (Admin.deleteLecturer(LT_id)) {
        System.out.println("Lecturer deleted successfully.");
    } else {
        System.out.println("Deleting a lecturer failed. Please enter the information again correctly.");
    }
}
    
    public static void displayAllLecturers() throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt");

    try (Scanner scanner = new Scanner(inputFile)) {
        if (!scanner.hasNextLine()) {
            System.out.println("No lecturers found.");
            return; // No need to proceed further
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length >= 4) {
                System.out.println("Name: " + parts[2] + ", Username: " + parts[0] + ", Lecturer ID: " + parts[3]);
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }
}

    public static void adminUpdateLecturers() throws IOException {
    Scanner input = new Scanner(System.in);

    // Display all lecturers with their data
    displayAllLecturers();

    System.out.print("Enter the lecturer's ID to update: ");
    int LT_id = input.nextInt();

    if (isLecturerExists(LT_id)) {
        System.out.print("Enter the new username: ");
        String newUsername = input.next();

        System.out.print("Enter the new password: ");
        String newPassword = input.next();

        System.out.print("Enter the new name: ");
        String newName = input.next();

        if (Admin.updateLecturer(LT_id, newUsername, newPassword, newName)) {
            System.out.println("Lecturer updated successfully.");
        } else {
            System.out.println("Updating lecturer failed. Please enter the information again in the right way.");
        }
    } else {
        System.out.println("Lecturer with ID " + LT_id + " not found.");
    }
}
    public static boolean isLecturerExists(int lecturerId) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt");

    try (Scanner scanner = new Scanner(inputFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the line contains the lecturer with the specified ID
            if (parts.length >= 4 && Integer.parseInt(parts[3]) == lecturerId) {
                return true; // Lecturer exists
            }
        }
    } catch (IOException | NumberFormatException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return false; // Lecturer does not exist
}

    public static void adminSearchLecturers() throws IOException {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the lecturer's name to search: ");
    String searchName = input.nextLine();

    ArrayList<String> matchingLecturers = searchLecturersByName(searchName);

    if (matchingLecturers.isEmpty()) {
        System.out.println("No lecturers found with the name '" + searchName + "'.");
    } else {
        System.out.println("Matching lecturers:");
        for (String lecturerInfo : matchingLecturers) {
            System.out.println(lecturerInfo);
        }
    }
}

    public static ArrayList<String> searchLecturersByName(String searchName) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt");
    ArrayList<String> matchingLecturers = new ArrayList<>();

    try (Scanner scanner = new Scanner(inputFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length >= 3 && parts[2].equalsIgnoreCase(searchName)) {
                matchingLecturers.add("Name: " + parts[2] + ", Username: " + parts[0] + ", Lecturer ID: " + parts[3]);
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return matchingLecturers;
}
    public static void AdminSubjectMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter:\n1 If you want to add subjects"
                + "\n2 If you want to delete subjects"
                + "\n3 If you want to display all subjects"
                + "\n4 If you want to update information"
                + "\n5 If you want to search"
                + "\n6 If you want to Assign Subject to student"
                + "\n7 If you want to Assign Subject to lecturer"
                + "\n8 Exit"
                + "\nYour choice: ");

            int subjectChoice = input.nextInt();

            switch (subjectChoice) {
                case 1:
                    adminAddSubject(); // Call the method for adding students
                    break;
                case 2:
                    admindeleteSubjects(); // Call the method for deleting students
                    break;
                case 3:
                    displayAllSubjects(); // Call the method for listing students
                    break;
                case 4:
                    adminUpdateSubjects(); // Call the method for Updating students
                    break; // Add this break statement
                case 5:
                    searchSubject();
                    break;
                case 6:
                   adminAssignSubjecttoStudents();
                    break;
                case 7:
                    adminAssignSubjecttoLecturer();
                    break;
                case 8:
                    AdminMenu();// Return from the method to go back to the previous menu
                    break; 
                  
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    public static void adminAddSubject() throws IOException {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the new Subject's name: ");
    String subject_name = input.nextLine();
    System.out.print("Enter the new Subject's code: ");
    String subject_code = input.nextLine();
    // Check if the username already exists
    if (!subjectExists(subject_name) && !subjectCodeExists(subject_code)) {
        if (Admin.addSubjects(subject_name, subject_code)) {
            System.out.println("Subject added successfully.");
        } else {
            System.out.println("Adding a Subject failed. Please enter the information again in the right way.");
        }
    } else {
        System.out.println("Subject with code " + subject_code + " or name " + subject_name + " already exists. Cannot add duplicate subjects.");
    }
}
    private static boolean subjectExists(String subjectName) {
        File subjectsFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");

        try (Scanner scanner = new Scanner(subjectsFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 1 && parts[0].equals(subjectName)) {
                    return true; // Subject with the specified name already exists
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return false; // Subject with the specified name does not exist
}
    private static boolean subjectCodeExists(String subjectCode) { 
        File subjectsFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");

        try (Scanner scanner = new Scanner(subjectsFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[1].equals(subjectCode)) {
                    return true; // Subject with the specified code already exists
                }
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getMessage());
        }

        return false; // Subject with the specified code does not exist
}
    public static void admindeleteSubjects() throws IOException {
    Scanner input = new Scanner(System.in);

    // Display all students with their data
   
    displayAllSubjects();

    System.out.print("Enter the subject's code to delete: ");
    String subject_code = input.nextLine();

    if (Admin.deleteSubject(subject_code)) {
        System.out.println("subject deleted successfully.");
    } else {
        System.out.println("Deleting a student failed. Please enter the information again in the right way.");
    }
}
    public static void displayAllSubjects() throws IOException {
    File subjectsFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");

        try (Scanner scanner = new Scanner(subjectsFile)) {
            if (scanner.hasNextLine()) {
                // File is not empty, print the subjects
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    System.out.println(line);
                }
            } else {
                // File is empty
                System.out.println("No subjects found. The file is empty.");
            }
        }
}
    public static void adminUpdateSubjects() throws IOException {
    Scanner input = new Scanner(System.in);

    // Display all subjects with their data
    displayAllSubjects();

    System.out.print("Enter the subjects's code to update: ");
    String subject_code = input.nextLine();

    if (subjectCodeExists(subject_code)) {
        System.out.print("Enter the new name: ");
        String newSubjectName = input.nextLine();

        System.out.print("Enter the new code: ");
        String newSubjectCode = input.nextLine();

        if (Admin.updateSubject(subject_code,newSubjectCode, newSubjectName)) {
            System.out.println("Student updated successfully.");
        } else {
            System.out.println("Updating student failed. Please enter the information again in the right way.");
        }
    } else {
        System.out.println("subject with code " + subject_code + " not found.");
    }
}
    public static void searchSubject() throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        File subjectsFile = new File("C:\\PL2_project\\project_files\\subjects\\subjects.txt");
        System.out.println("plase enter the subject's code you want to find :");
        String subjectCode = input.nextLine();
        boolean found = false;

        try (Scanner scanner = new Scanner(subjectsFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                
                // Check if the line contains the subject with the specified code
                if (parts.length >= 2 && parts[1].equals(subjectCode)) {
                    found = true;
                    System.out.println(line);
                    break; // Stop searching once found
                }
            }
        }

        if (!found) {
            System.out.println("Subject with code " + subjectCode + " not found.");
        }
    }



    public static void adminAssignSubjecttoStudents() throws IOException {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the student's ID: ");
    int ST_ID = input.nextInt();
    input.nextLine(); // Consume the newline character

    // Check if the username already exists
    if (isStudentIDExisting(ST_ID)) {
        System.out.print("Enter the Subject code: ");
        String ST_subject = input.nextLine();

        if (Admin.assignSubjecttoStudent(ST_subject, ST_ID)) {
            System.out.println("Subject added successfully.");
        } else {
            System.out.println("Adding a student failed. Please enter the information again in the right way.");
        }
    }
    else{ System.out.println("ID doesn't exist");}
}



    public static void adminAssignSubjecttoLecturer() throws IOException {
    Scanner input = new Scanner(System.in);

    System.out.print("Enter the student's ID: ");
    int ST_ID = input.nextInt();
    input.nextLine(); // Consume the newline character

    // Check if the username already exists
    if (isLecturereIDExisting(ST_ID)) {
        System.out.print("Enter the Subject name: ");
        String ST_subject = input.nextLine();

        if (Admin.assignSubjecttolecturer(ST_subject, ST_ID)) {
            System.out.println("Subject added successfully.");
        } else {
            System.out.println("Adding a student failed. Please enter the information again in the right way.");
        }
    }
    else{ System.out.println("ID doesn't exist");}
}

    public static boolean isLecturereIDExisting(int id) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\lecturers_files\\lecturers.txt");

    try (Scanner scanner = new Scanner(inputFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the username already exists
            if (parts.length >= 4 && Integer.parseInt(parts[3] ) == id ) {
                return true; // Username is not unique
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return false; // Username is unique
}



    public static boolean isStudentIDExisting(int id) throws IOException {
    File inputFile = new File("C:\\PL2_project\\project_files\\students_files\\students.txt");

    try (Scanner scanner = new Scanner(inputFile)) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            // Check if the username already exists
            if (parts.length >= 4 && Integer.parseInt(parts[3] ) == id ) {
                return true; // Username is not unique
            }
        }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }

    return false; // Username is unique
}





    public static void handleLecturerLogin() {
         Scanner input = new Scanner(System.in);
            boolean loginSuccessful = false;
            do {
                System.out.print("Please enter your username: ");
                String lecturerUsername = input.nextLine();
                System.out.print("Please enter your password: ");
                String lecturerPassword = input.nextLine();
                
                
          
                if (Lecturer.login(lecturerUsername, lecturerPassword)) {
                    System.out.println("Logged in successfully");
                    loginSuccessful = true ;
                } else {
                    System.out.println("Login failed. Please enter the information again in the right way.");
                }
            } while (!loginSuccessful);
            // Close the Scanner to prevent resource leaks
        
    }
    
        public static void handleStudentLogin() {
        Scanner input = new Scanner(System.in);
        boolean loginSuccessful = false;
        do {
            System.out.print("Please enter your username: ");
            String studentUsername = input.nextLine();
            System.out.print("Please enter your password: ");
            String studentPassword = input.nextLine();

            if (Student.login(studentUsername, studentPassword)) {
                System.out.println("Logged in successfully");
                loginSuccessful = true;
            } else {
                System.out.println("Login failed, Please enter the information again in the right way.");
            }
        } while (!loginSuccessful);

    }
    
    public static void StudentMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
           
                System.out.print("Please enter:\n1 Open Exam\n2 Degree\n3 Exit\nYour choice: ");


                int studentChoice = input.nextInt();
                    switch (studentChoice) {
                        case 1:
                            System.out.println("You chose to Open Exam");
                            break;
                        case 2:
                            System.out.println("You chose to View Degree");
                            showmarks();
                            break;
                        case 3:
                            System.out.println("Exiting program");
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }

                } 
            } 
 
 
 
 
    public static void LecturerMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter:\n1 Add Exam\n2 Delete Exam\n3 Update Exam\n4 List of Exam\n5 Marks report\n6 Exit\nYour choice: ");

            try {
                input.hasNextInt();
                if (input.hasNextInt()) {
                    int lecturerChoice = input.nextInt();

                    switch (lecturerChoice) {
                        case 1:
                            System.out.println("You chose to Add Exam");
                            handleAddExam();
                            break;
                        case 2:
                            System.out.println("You chose to Delete Exam");
                            handledeleteExam();
                            // Add your logic for deleting an exam
                            break;
                        case 3:
                            System.out.println("You chose to Update Exam");
                            updateExam();
                            // Add your logic for updating an exam
                            break;
                        case 4:
                            System.out.println("You chose to List of Exam");
                            displayAllexams();
                            break;
                            case 5:
                            System.out.println("Which type of reports you want?");
                            reportsMenu();
                            break;
                        case 6:
                            System.out.println("Exiting program");
                            return;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    input.next(); // Consume the invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine(); // Consume the invalid input line
            }
        }
    }
     public static void createTextFile(String fileName, String filePath) {
        try {
            FileWriter fileWriter = new FileWriter(filePath + "/" + fileName + ".txt");
            fileWriter.close();
            System.out.println("File created successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
    }
  public static void handleAddExam() throws IOException {
    Scanner input = new Scanner(System.in);
    boolean addedSuccessfully = false;

    do {
        System.out.print("Please enter the exam subject's code you want to add: ");
        String subjectCode = input.nextLine();

        System.out.print("Please enter your ID: ");
        int lecturerId;
        
        try {
            lecturerId = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid integer.");
            continue; // Restart the loop if the ID is invalid
        }

        if (Lecturer.isSubjectAssignedToLecturer(subjectCode, lecturerId)) {
            
            if(Lecturer.add_exam(lecturerId,subjectCode)){
                
            }else{
           System.out.println("Invalid opreation.");
            }
            addedSuccessfully = true;
        } else {
            System.out.println("This subject is not assigned to you.");
        }
    } while (!addedSuccessfully);

}
    
  public static void updateExam() {
        Scanner input = new Scanner(System.in);

        // Get exam details to update
        System.out.print("Enter the name of the exam to update: ");
        String examName = input.nextLine();

        // Get lecturer ID to identify the exam uniquely
        System.out.print("Enter the lecturer ID for the exam: ");
        int lecturerId = input.nextInt();
        input.nextLine(); // Consume the newline character

        // Get the file path for exams
        String filePath = "C:/PL2_project/project_files/exams_files";
        String fileName = examName + ".txt";
        String fullPath = filePath + "/" + fileName;

        // Check if the exam file exists
        File examFile = new File(fullPath);
        if (!examFile.exists()) {
            System.out.println("Exam not found.");
            return;
        }

        // Get the original line from exams.txt to identify the exam
        String originalLine = examName + "," + lecturerId;

        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            // Display current exam details
            System.out.println("Current details for exam '" + examName + "':");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading exam details: " + e.getMessage());
            return;
        }

        // Get new exam details from the user
        System.out.println("Enter new details for the exam:");

        // Example: Ask the user which details to update
        System.out.print("Choose what to update:\n1. Exam Name\n2. Questions\n3. Exit\nYour choice: ");
        int updateChoice = input.nextInt();
        input.nextLine(); // Consume the newline character

        switch (updateChoice) {
            case 1:
                // Update Exam Name
                System.out.print("Enter the new name for the exam: ");
                String newExamName = input.nextLine();
                updateExamName(filePath, examName, newExamName);
                fullPath = filePath + "/" + newExamName + ".txt";  // Update fullPath with the new exam name
                updateExamsFile(originalLine, newExamName + "," + lecturerId);
                break;

            case 2:
                // Update Questions
                 System.out.println("Enter new questions for the exam (write each question and answer on a new line):");
    StringBuilder newQuestions = new StringBuilder();
    boolean addAnotherQuestion = true;
    while (addAnotherQuestion) {
        System.out.print("Question: ");
        String question = input.nextLine();

        System.out.print("Answer: ");
        String answer = input.nextLine();

        if (question.isEmpty() || answer.isEmpty()) {
            addAnotherQuestion = false;  // Exit the loop when an empty line is entered
        } else {
            newQuestions.append(question).append(" ? ").append(answer).append("\n");
        }
    }
    updateExamDetail(fullPath, "Questions", newQuestions.toString());
                updateExamsFile(originalLine, examName + "," + lecturerId);
                break;

            case 3:
                return;

            default:
                System.out.println("Invalid choice. No updates performed.");
                return;
        }

        // Inform the user that the update was successful
        System.out.println("Exam updated successfully!");
    }

    private static void updateExamsFile(String originalLine, String newLine) {
        try (BufferedReader examsReader = new BufferedReader(new FileReader("C:/PL2_project/project_files/exams_files/exams.txt"));
             BufferedWriter examsWriter = new BufferedWriter(new FileWriter("C:/PL2_project/project_files/exams_files/exams_temp.txt"))) {

            String line;
            while ((line = examsReader.readLine()) != null) {
                if (line.equals(originalLine)) {
                    examsWriter.write(newLine);
                } else {
                    examsWriter.write(line);
                }
                examsWriter.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error updating exams file: " + e.getMessage());
            return;
        }

        // Overwrite the original file with the temporary file
        try {
            Files.move(Paths.get("C:/PL2_project/project_files/exams_files/exams_temp.txt"),
                    Paths.get("C:/PL2_project/project_files/exams_files/exams.txt"),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Exams file updated successfully!");
        } catch (IOException e) {
            System.out.println("Failed to update exams file: " + e.getMessage());
        }
    }

    private static void updateExamName(String filePath, String oldExamName, String newExamName) {
        try {
            Path sourcePath = Paths.get(filePath, oldExamName + ".txt");
            Path targetPath = Paths.get(filePath, newExamName + ".txt");

            Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Exam name updated successfully!");
        } catch (IOException e) {
            System.out.println("Failed to update exam name: " + e.getMessage());
        }
    }
private static void updateExamDetail(String filePath, String detailName, String newDetail) {
    try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
         BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath + "_temp"))) {

        boolean detailUpdated = false;  // Flag to check if the detail has been updated

        String line;
        while ((line = fileReader.readLine()) != null) {
            if (line.startsWith(detailName)) {
                // Append new questions without deleting previous ones
                fileWriter.write(line);
                fileWriter.newLine();
                fileWriter.write(newDetail);
                fileWriter.newLine();
                detailUpdated = true;
            } else {
                fileWriter.write(line);
                fileWriter.newLine();
            }
        }

        // If the detail is not present in the file, append it
        if (!detailUpdated) {
            fileWriter.write(detailName + ": " + newDetail);
            fileWriter.newLine();
        }

    } catch (IOException e) {
        System.out.println("Error updating exam details: " + e.getMessage());
        return;
    }

    // Overwrite the original file with the temporary file
    try {
        Files.move(Paths.get(filePath + "_temp"), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(detailName + " updated successfully!");
    } catch (IOException e) {
        System.out.println("Failed to update " + detailName.toLowerCase() + ": " + e.getMessage());
    }
}

public static void handledeleteExam() throws IOException {
    Scanner input = new Scanner(System.in);
    boolean deletedSuccessfully = false;

    do {
        System.out.print("Please enter the subject's code you want to delete: ");
        String subjectCode = input.nextLine();
        System.out.print("Please enter the exam code you want to delete: ");
        String examCode = input.nextLine();

        System.out.print("Please enter your ID: ");
        int lecturerId;
        
        try {
            lecturerId = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID. Please enter a valid integer.");
            continue; // Restart the loop if the ID is invalid
        }

        if (Lecturer.isSubjectAssignedToLecturer(subjectCode,lecturerId)) {
            
            if(Lecturer.deleteExam(examCode,subjectCode,lecturerId)){
             System.out.print("Exam deleted!");
            }else{
           System.out.println("Invalid opreation.");
            }
            deletedSuccessfully = true;
        } else {
            System.out.println("This subject is not assigned to you.");
        }
    } while (!deletedSuccessfully);

}
public static void displayAllexams() throws IOException {
    File inputFile = new File("C:/PL2_project/project_files/exams_files/exams.txt");
            Scanner id = new Scanner(System.in);
            boolean hasexams=false;
     System.out.print("Please enter your ID: ");
        String lecturerId= id.nextLine();;
    try (Scanner scanner = new Scanner(inputFile)) {
        if (!scanner.hasNextLine()) {
            System.out.println("No exams found.");
            return; // No need to proceed further
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");

            if (parts.length >= 2 && lecturerId.equals(parts[1])) {
                System.out.println("exam: " + parts[0] + ",Lecturer id: " + parts[1]);
                hasexams=true;
            }
            
        }
        if(!hasexams){
               System.out.println("you don't have exams here!.");      
            }
    } catch (IOException e) {
        System.out.println("Exception: " + e.getMessage());
    }
}
public static void showmarks() throws FileNotFoundException{
     Scanner scan = new Scanner(System.in);
     System.out.println("Enter id!");
    String studentid = scan.nextLine();
         System.out.println("Enter subject code!");
     String studentsubject = scan.nextLine();
     System.out.println(Student.getmark(studentid,studentsubject));
}
public static void reportallgrades() throws FileNotFoundException{
       String subject;
        Scanner scan = new Scanner(System.in);
         System.out.println("Enter subject code!");
      subject = scan.nextLine();
   try (Scanner input = new Scanner(new File("C:/PL2_project/project_files/students_files/ST_subject.txt"))) {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            String[] parts = line.split(",");
            if (parts.length >= 3) {
                String id = parts[1].trim();
                String studentsubject = parts[0].trim();
                    String  degree=parts[2].trim();
                if (subject.equals(studentsubject)) {
                 System.out.println(id + " : " + degree);
        
                    
                }
            }
        }
      }

}
public static void specisficreport() throws FileNotFoundException{
     Scanner scan = new Scanner(System.in);
     System.out.println("Enter id!");
    String studentid = scan.nextLine();
     System.out.println("Subject");
    String Subject = scan.nextLine();
    System.out.print("here is student grade : ");
     System.out.println(Student.getmark(studentid,Subject));
     System.out.println("Write your report here ");
    String context = scan.nextLine(); 
    Report.exportreport(Subject, studentid, context);
} 

public static void reportsMenu() throws IOException {
        Scanner input = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter:\n1 Marks report\n2 to do report for specific sudent \n3 Exit");

            try {
                input.hasNextInt();
                if (input.hasNextInt()) {
                    int lecturerChoice = input.nextInt();

                    switch (lecturerChoice) {
                        case 1:
                            System.out.println("Here's the grades");
                            reportallgrades();
                                break;
                        case 2:
                            specisficreport();
                            break;
                        case 3:
                            System.out.println("Exiting program");
                            return;
                       
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid integer.");
                    input.next(); 
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
                input.nextLine(); 
            }
        }
    }

}

    
    
