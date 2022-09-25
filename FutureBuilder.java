import java.util.ArrayList;
import java.util.Iterator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Random;  

Scanner inputter = new Scanner(System.in);

class Offers {
    Company comp;
    double CTC;
    Student stu;
    int accepted = 0;
    int rejected = 0;
    void Offers(Company comp, double CTC, Student stu){
        this.comp = comp;
        this.stu = stu;
        this.CTC = CTC;
        PlacementCell.totalOffers.add(this);
    }
    void printer(){
        System.out.println("Offered By: " this.comp.name);
        System.out.println("Package: " this.ctc);
        System.out.println("Role: " this.comp.role);
    }
}

class Student {
    static int numberofStudents = 0;
    static public List<Student> students = new ArrayList<Students>;

    String name;
    int rollNo;
    int exitFlag = 0;
    double cgpa;
    public List<Offers> rcvdOffers = new ArrayList<Offers>;
    double pendingCGPAUpdate = -1;
    String branch;
    String status = "not-applied";
    int registered = 0;
    int currentHighestCTC = 0;
    int numberOfOffers = 0;
    LocalDateTime registerDateTime;
    void printer(){
        System.out.println("Student Name = " + this.name);
        System.out.println("Student rollNo = " + this.rollNo);
        System.out.println("Student cgpa = " + this.cgpa);
        System.out.println("Student branch = " + this.branch);

    }
    void Student(int roll, String name, double cgpa, String branch){
        this.rollNo = roll;
        this.name = name;
        this.cgpa = cgpa;
        this.branch = branch;
        students.add(this);
        numberofStudents += 1;
    }
    public void registerForCompany(Company comp){
        PlacementCell.updateTime();
        if(PlacementCell.companyOpen){
            comp.registeredStudents.add(this);
            this.registered = 1;
            this.status = "applied";    
        }
    }
    public void registerToPlacement(){
        PlacementCell.updateTime();
        if(PlacementCell.studentsOpen){
            PlacementCell.registeredStudents.add(this);
            this.registered = 1;
            this.registerDateTime = LocalDateTime.now();            
        }
    }
    public void getAllAvailableCompanies(){
        List<Company> availableCompanies = Company.companies;
        availableCompanies.removeIf(s -> !s.checkConditions(this));
    }
    public void getCurrentStatus(){
        System.out.println("Student Status: " + status);
        if(status == "offered"){
            this.rcvdOffers.get(rcvdOffers.size()-1).printer();
            System.out.println("Please accept the offer");
        }
    }
    public void requestUpdateCGPA(double newCGPA){
        this.pendingCGPAUpdate = newCGPA;
        PlacementCell.pendingStudentCGPAChanges = this;
        PlacementCell.changeStudentCGPA();
    }
    public void reject(){
        Offer off = rcvdOffers.get(rcvdOffers.size() -1);
        off.comp.rejectedOfferStudents.add(this);
        off.rejected = "1";
        rcvdOffers.remove(rcvdOffers.size() -1);
        if(rcvdOffers.size() == 0){
            this.status = "blocked";
        }

    }
    public void accept(){
        Offer off = rcvdOffers.get(rcvdOffers.size() -1);
        off.comp.acceptedOfferStudents.add(this);
        this.status = "accepted";
        off.accepted = 1;
        this.exitFlag = 0;
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
    int registered = 0;

    void Company(double package, double cgpaCriteria, String name, String role){
        this.registrationDateTime = LocalDateTime.now();
        this.package = package;
        this.cgpaCriteria = cgpaCriteria;
        this.name=name;
        this.role=role;
        companies.add(this)
        numberOfCompanies += 1;
    }

    void registerToPlacement(){
        PlacementCell.updateTime();
        if(PlacementCell.companyOpen){
            this.registrationDateTime = LocalDateTime.now();
            PlacementCell.registeredCompanies.add(this);
            this.registered = 1;    
        }
        
    }

    void randomizer(){
        Random random= new Random();  
        return random.nextBoolean();  
    }

    void selectStudents(){
        PlacementCell.updateTime();
        if(PlacementCell.studentsOpen == 0){
            this.selectedStudents = this.registeredStudents.removeIf(s -> arOffer(s.comp));
            if(len(this.selectedStudents == 0)){
                this.selectedStudents.add(registeredStudents.get(0));
            }
            this.selectedStudents.forEach(s => s.Offers.add(new Offer(this, this.package, s)))
        }
    }

    void getSelectedStudents(){
        selectedStudents.forEach(s => s.printer());
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
    void printer(){
        System.out.println("Company Name = " + this.name);
        System.out.println("Company Package = " + this.package);
        System.out.println("Company CgpaCriteria = " + this.cgpaCriteria);
        System.out.println("Company Role = " + this.role);
    }

    void checkConditions(Student stu){
        if(stu.cgpa >= this.cgpaCriteria && stu.status != "placed" && this.package >= 3*stu.currentHighestCTC){
            return true;
        }
        return false;
    }
}

class PlacementCell {
    static List<Offers> totalOffers = new ArrayList<Offers>;
    static int companyOpen = 0;
    static int studentsOpen = 0;
    static Student pendingStudentCGPAChanges;
    static LocalDateTime companyRegisterDeadline;
    static LocalDateTime studentRegisterDeadline;
    static List<Student> registeredStudents = new ArrayList<Student>;
    static List<Company> registeredCompanies = new ArrayList<Company>;
    static public void updateTime(){
        if(companyOpen == 1 && LocalDateTime.now() > companyRegisterDeadline){
            companyOpen = 0;
        }
        if(studentsOpen == 1 && LocalDateTime.now() > studentRegisterDeadline){
            studentsOpen = 0;
        }
    }
    static public void changeStudentCGPA(){
        pendingStudentCGPAChanges.cgpa = pendingStudentCGPAChanges.pendingCGPAUpdate
        pendingStudentCGPAChanges.pendingCGPAUpdate = -1;
    }
    
    static public void openStudentRegistrations(){
        if(companyOpen == 0 && studentsOpen == 0 && LocalDateTime.now() > companyRegisterDeadline){
            studentsOpen = 1;
            studentRegisterDeadline =LocalDateTime.now();
        }
    }
    static public void openCompanyRegistrations(){
        if(studentsOpen == 0 0 && companyOpen == 0)
        {
            companyRegisterDeadline =LocalDateTime.now();
            companyOpen = 1;
        }

    }
    static public void getNumberofOfferedStudents(){
        int temp = 0;
        registeredStudents.forEach{s => 
            if(s.status == "offered")
                {temp+=1}
            };
        System.out.println("Number of Offered Students = " + temp);
    }

    static public void getNumberofOfferedStudents(){
        int temp = 0;
        registeredStudents.forEach{s => 
            if(s.status == "unoffered")
                {temp+=1}
            };
        System.out.println("Number of Unoffered Students = " + temp);

    }

    static public void getNumberofOfferedStudents(){
        int temp = 0;
        registeredStudents.forEach{s => 
            if(s.status == "blocked")
                {temp+=1}
            };
        System.out.println("Number of blocked Students = " + temp);

    }

    static public void getNumberofStudentsRegistered(){
        System.out.println("Number of Students Registered: " + registeredStudents.size());
    }

    static public void getStudentDetails(Student stu){
        stu.printer();
    }
    static public void getAveragePackage(){

    }
    static public void getCompanyProcessResults(String companyName){

    }

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
            System.out.println("Bye!");
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
            companyMode();
        }
        if(temp == '3'){
            placementCellMode();
        }
        if(temp == '4'){
            return;
        }
        if(temp == '1'){
            studentMode();
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
                double package;
                double cgpa;
                String name;
                String role;
                System.out.println("Enter package");
                package = inputter.nextLine().toDouble();
                System.out.println("Enter cgpa");
                cgpa = inputter.nextLine().toDouble();
                System.out.println("Enter name");
                name = inputter.nextLine();
                System.out.println("Enter role");
                role = inputter.nextLine();
                new Company(package, cgpa, name, role);
                break;
            case "2":
                System.out.println("Enter Company Name");
                temp = inputter.nextLine();
                List<Company> companyList = Company.companies;
                companyList.removeIf(s -> !(s.name == temp);
                enterCompany(companyList.get(0));
                break;
            case "3":
                Company.companies.forEach(s-> s.printer());
                break;
            case "4":
                return;
                break;
        }
        companyMode();

    }
    void enterCompany(Company comp){
        System.out.println("Welcome " + comp.name);
        System.out.println("    1) Update Role");
        System.out.println("    2) Update Package");
        System.out.println("    3) Update CGPA criteria");
        System.out.println("    4) Register To Institute Drive");
        System.out.println("    5) Back");
        String temp = inputter.nextLine();
        switch (temp){
            case "1":
                System.out.println("Enter new role");
                temp = inputter.nextLine();
                comp.updateRole(temp);
                break;
            case "2":
                System.out.println("Enter new package");
                double pack = inputter.nextLine().toDouble();
                comp.updatePackage(pack);
                break;
            case "3":
                System.out.println("Enter new cgpa requirement");
                double cgpa = inputter.nextLine().toDouble();
                comp.updateCGPA(cgpa);
                break;
            case "4":
                comp.registerToPlacement();
                break;
            case "5":
                return;

        }
        enterCompany(comp);
    }
    void studentMode(){
        System.out.println("Choose the Student Query to perform-");
        System.out.println("    1) Enter as a Student(Give Student Name, and Roll No.)");
        System.out.println("    2) Add student");
        System.out.println("    3) Back");
        String temp = inputter.nextLine();
        switch (temp){
            case "1":
                System.out.println("Enter Student Name");
                temp = inputter.nextLine();
                List<Student> studentList = Student.students
                studentList.removeIf(s -> !(s.name == temp);
                System.out.println("Enter Roll No.");
                temp = inputter.nextLine();
                studentList.removeIf(s -> !(s.rollNo == int(temp));
                enterStudent(studentList.get(0));
                break;
            case "2":
                int roll;
                String name;
                double cgpa;
                String branch
                System.out.println("Enter Student Name");
                name = inputter.nextLine();
                System.out.println("Enter Student Name");
                cgpa = inputter.nextLine().toDouble();
                System.out.println("Enter Student Name");
                branch = inputter.nextLine();
                System.out.println("Enter Student Name");
                roll = inputter.nextLine().toInt();
                new Student(roll, name, cgpa, branch);
                break;
            case "3":
                return;

        }
        studentMode();
    }
    void enterStudent(Student stu){
        System.out.println("Welcome " + stu.name);
        System.out.println("    1) Register For Placement Drive");
        System.out.println("    2) Register For Company");
        System.out.println("    3) Get All available companies");
        System.out.println("    4) Get Current Status");
        System.out.println("    5) Update CGPA");
        System.out.println("    6) Accept offer");
        System.out.println("    7) Reject offer");
        System.out.println("    8) Back");
        String temp = inputter.nextLine();
        switch (temp){
            case "1":
                stu.registerToPlacement();
                break;
            case "2":
                System.out.println("Enter Company Name");
                temp = inputter.nextLine();
                List<Company> companyList = Company.companies;
                companyList.removeIf(s -> !(s.name == temp);
                stu.registerForCompany(companyList.get(0));
                break;
            case "3":
                stu.getAllAvailableCompanies();
                break;
            case "4":
                stu.getCurrentStatus();
                break;
            case "5":
                stu.updateCGPA("");
                break;
            case "6":
                stu.accept();
                break;
            case "7":
                stu.reject();
                break;
            case "8":
                return;

        }
        enterStudent(stu);
    }
    void placementCellMode(){
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
                PlacementCell.openStudentRegistrations();
                break;
            case "2":
                PlacementCell.openCompanyRegistrations();
                break;
            case "3":
                PlacementCell.getNumberofStudentsRegistered();
                break;
            case "4":
                PlacementCell.getNumberofCompaniesRegistered();
                break;
            case "5":
                PlacementCell.getNumberofOfferedStudents();
                PlacementCell.getNumberofUnofferedStudents();
                PlacementCell.getNumberofBlockedStudents();
                break;
            case "6":
                System.out.println("Enter Student Name");
                temp = inputter.nextLine();
                List<Student> studentList = Student.students
                studentList.removeIf(s -> !(s.name == temp);
                System.out.println("Enter Roll No.");
                temp = inputter.nextLine();
                studentList.removeIf(s -> !(s.rollNo == int(temp));
                studentList.get(0).printer();
                break;
            case "7":
                System.out.println("Enter Company Name");
                temp = inputter.nextLine();
                List<Company> companyList = Company.companies
                companyList.removeIf(s -> !(s.name == temp);
                companyList.get(0).printer();
                break;
            case "8":
                double packages = 0;
                int counter = 0;
                PlacementCell.totalOffers.forEach{s =>
                    packages += s.CTC;
                    counter += 1;
                }
                System.Out.println("Average Package  = " packages/counter);
                break;
            case "9":

                break;
            case "10":
                return;
                break;

        }
        placementCellMode();
    }
}
