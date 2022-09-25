import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
Scanner inputter = new Scanner(System.in);

class Student {
    static int numberofStudents = 0;
    static public List<Student> students = new ArrayList<Students>;
    String name;
    int rollNo;
    int cgpa;
    int pendingCGPAUpdate = -1;
    String branch;
    String status = "not-applied";
    Company companyOffered;
    int currentHighestCTC = 0;
    void Student(int roll, String name, int cgpa, String branch){
        this.rollNo = roll;
        this.name = name;
        this.cgpa = cgpa;
        this.branch = branch;
        students.add(this);
        numberofStudents += 1;
    }
    private void registerForCompany(Company comp){
        comp.registeredStudents.add(this);
        status = "applied";
    }
    private void getAllAvailableCompanies(){
        List<Company> availableCompanies = Company.companies;
        availableCompanies.removeIf(s -> !s.checkConditions(this));

    }
    private void getCurrentStatus(){
        System.out.println("Student Status: " + status);
        if(status == "offered"){
            this.companyOffered.print();
        }
    }
    private void requestUpdateCGPA(int newCGPA){
        this.pendingCGPAUpdate = newCGPA;
        PlacementCell.pendingStudentCGPAChanges = this;
        PlacementCell.changeStudentCGPA();
    }
    private void arOffer(){

    }
    public void print(){}
}
class Company {
    static int numberOfCompanies = 0;
    static public List<Company> companies = new ArrayList<Company>;
    List<Student> registeredStudents = new ArrayList<Student>;
    List<Student> selectedStudents = new ArrayList<Student>;
    int package;
    int cgpaCriteria;
    LocalDateTime registrationDateTime;
    void Company(int package, int cgpaCriteria, String name){
        this.registrationDateTime = LocalDateTime.now();
        this.package = package;
        this.cgpaCriteria =cgpaCriteria;
        this.name=name;
        companies.add(this)
        numberOfCompanies += 1;
    }
    void getSelectedStudents(){
        registeredStudents.print();
    }
    void updateRPCGPACriteria(){

    }
    void print(){}
    void checkConditions(Student stu){
        if(stu.cgpa >= cgpaCriteria && stu.status != "placed" && package >= 3*stu.currentHighestCTC){
            return true;
        }
        return false;
    }
}
class PlacementCell {
    int companyOpen = 0;
    int studentsOpen = 0;
    static public Student pendingStudentCGPAChanges;
    LocalDateTime companyRegisterDeadline;
    LocalDateTime studentRegisterDeadline;
    public void changeStudentCGPA(){
        pendingStudentCGPAChanges.cgpa = pendingStudentCGPAChanges.pendingCGPAUpdate
        pendingStudentCGPAChanges.pendingCGPAUpdate = -1;
    }
    
    public void openStudentRegistrations(){
        if(companyOpen == 0 && studentsOpen == 0 && LocalDateTime.now() > companyRegisterDeadline){
            studentsOpen = 1;
            studentRegisterDeadline =LocalDateTime.now();
        }
    }
    public void openCompanyRegistrations(){
        if(studentsOpen == 0 0 && companyOpen == 0)
        {
            companyRegisterDeadline =LocalDateTime.now();
            companyOpen = 1;
        }

    }
    public void getNumberofStudentsRegistered(){
        Student.numberofStudents;
    }
    public void getNumberofStatusStudents(){

    }
    public void getStudentDetails(Student stu){

    }
    public void getAveragePackage(){

    }
    public void getCompanyProcessResults(String companyName){}

}
class FutureBuilder {
    public static void main(String[] args) {
        mainApplication();
    }

    void mainApplication(){
        System.out.println("Welcome to FutureBuilder");
        System.out.println("    1) Enter the Application");
        System.out.println("    2) Exit the Application");
        String temp = inputter.nextLine();
        if(temp == "1"){
            modeSelector()
        }
        if(temp == "2"){
            return;
        }
        mainApplication();
    }
    void modeSelector(){
        System.out.println("Choose The mode you want to Enter in:-");
        System.out.println("    1) Enter as Student Mode");
        System.out.println("    2) Enter as Company Mode");
        System.out.println("    3) Enter as Placement Cell Mode");
        System.out.println("    4) Return To Main Application");
        temp = inputter.nextLine();
        if(temp == '2'){

        }
        if(temp == '3'){
            
        }
        if(temp == '4'){
            return;
        }
        if(temp == '1'){
            
        }
        modeSelector();
    }
    void companyMode(){
        System.out.println("Choose the Company Query to perform-");
        System.out.println("    1) Add Company and Details");
        System.out.println("    2) Choose Company");
        System.out.println("    3) Get Available Companies");
        System.out.println("    4) Back");
        String temp = inputter.nextLine();
        switch (temp){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                return;
                break;

        }


    }
    void studentMode(){
        System.out.println("Choose the Student Query to perform-");
        System.out.println("    1) Enter as a Student(Give Student Name, and Roll No.)");
        System.out.println("    2) Add students");
        System.out.println("    3) Back");
        String temp = inputter.nextLine();
        switch (temp){
            case "1":
                break;
            case "2":
                break;
            case "3":
                return;

        }

    }
    void PlacementCellMode(){
        System.out.println("Welcome to IIITD Placement Cell");
        System.out.println("    1) Open Student Registrations");
        System.out.println("    2) Open Company Registrations");
        System.out.println("    3) Get Number of Student Registrations");
        System.out.println("    4) Get Number of Company Registrations");
        System.out.println("    5) Get Number of Offered/Unoffered/Blocked Students");
        System.out.println("    6) Get Student Details");
        System.out.println("    7) Get Company Details");
        System.out.println("    8) Get Average Package");
        System.out.println("    9) Get Company Process Results");
        System.out.println("    10) Back");
        String temp = inputter.nextLine();
        switch (temp){
            case "1":
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                break;
            case "5":

                break;
            case "6":
                break;
            case "7":
                break;
            case "8":
                break;
            case "9":
                break;
            case "10":
                return;
                break;

        }

    }

    
}
