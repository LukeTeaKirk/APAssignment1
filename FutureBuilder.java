import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Random;  


Scanner inputter = new Scanner(System.in);
class Offers {
    Company comp;
    int CTC;
}
class Student {
    static int numberofStudents = 0;
    static public List<Student> students = new ArrayList<Students>;
    String name;
    int rollNo;
    int exitFlag = 0;
    int cgpa;
    public List<Offers> rcvdOffers = new ArrayList<Offers>;
    int pendingCGPAUpdate = -1;
    String branch;
    String status = "not-applied";
    int registered = 0;
    int currentHighestCTC = 0;
    int numberOfOffers = 0;
    void Student(int roll, String name, int cgpa, String branch){
        this.rollNo = roll;
        this.name = name;
        this.cgpa = cgpa;
        this.branch = branch;
        students.add(this);
        numberofStudents += 1;
    }
    public void registerForCompany(Company comp){
        comp.registeredStudents.add(this);
        this.registered = 1;
        this.status = "applied";
    }
    public void getAllAvailableCompanies(){
        List<Company> availableCompanies = Company.companies;
        availableCompanies.removeIf(s -> !s.checkConditions(this));
    }
    public void getCurrentStatus(){
        System.out.println("Student Status: " + status);
        if(status == "offered"){
            this.companyOffered.print();
        }
    }
    public void requestUpdateCGPA(int newCGPA){
        this.pendingCGPAUpdate = newCGPA;
        PlacementCell.pendingStudentCGPAChanges = this;
        PlacementCell.changeStudentCGPA();
    }
    private void arOffer(Company comp){
        String temp = inputter.nextLine();
        if(this.exitFlag == 1){
            return false;
        }
        if(this.status == "accepted"){
            return true;
        }
        if(temp == "accept"){
            this.status = "accepted";
            comp.acceptedOfferStudents.add(this);
            return false;
        }
        if(temp == "reject"){
            comp.rejectedOfferStudents.add(this);
            return true;
        }
        if(temp == "exit"){
            this.exitFlag = 1;
            return false;
        }

    }
    public void acceptreject(){
        rcvdOffers.removeIf(s -> arOffer(s.comp));
        this.exitFlag = 0;
    }

    public void print(){

    }
}
class Company {
    static int numberOfCompanies = 0;
    static public List<Company> companies = new ArrayList<Company>;
    List<Student> registeredStudents = new ArrayList<Student>;
    List<Student> selectedStudents = new ArrayList<Student>;
    List<Student> acceptedOfferStudents = new ArrayList<Student>;
    List<Student> rejectedOfferStudents = new ArrayList<Student>;
    double package;
    double cgpaCriteria;
    LocalDateTime registrationDateTime;
    String role;
    void Company(int package, double cgpaCriteria, String name){
        this.registrationDateTime = LocalDateTime.now();
        this.package = package;
        this.cgpaCriteria = cgpaCriteria;
        this.name=name;
        companies.add(this)
        numberOfCompanies += 1;
    }
    void randomizer(){
        Random random= new Random();  
        return random.nextBoolean();  
    }
    void selectedStudents(){
        if(PlacementCell.studentsOpen == 0){
            this.selectedStudents = this.registeredStudents.removeIf(s -> arOffer(s.comp));
            if(len(this.selectedStudents == 0)){
                this.selectedStudents.add(registeredStudents.get(0));
            }
        }
    }
    void getSelectedStudents(){
        selectedStudents.print();
    }
    void updateCGPA(double cgpa){
        this.cgpaCriteria = cgpa;
    }
    void updatePackage(double package){
        this.package = package;
    }
    void updateRole(String role){
        this.role = role;
    }
    void print(){}
    void checkConditions(Student stu){
        if(stu.cgpa >= this.cgpaCriteria && stu.status != "placed" && this.package >= 3*stu.currentHighestCTC){
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
