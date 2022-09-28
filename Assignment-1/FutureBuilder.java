import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;


public class FutureBuilder {
    public static void main(String[] args) {
        System.out.println("\n========================================================");
        System.out.println("                 Welcome to FutureBuilder               ");
        System.out.println("         Designed and developed by Aditya Girdhar       ");
        System.out.println("========================================================\n");

        Scanner scan = new Scanner(System.in);
        PlacementCell cell = new PlacementCell();
        int response;

        mainMenu: while (true) {
            System.out.println(" 1) Enter the application");
            System.out.println(" 2) Exit");
            response = scan.nextInt();
            if (response == 1) {
                modeSelection: while (true) {
                    System.out.println("\nChoose the mode you want to enter in:");
                    System.out.println(" 1) Enter as Student Mode");
                    System.out.println(" 2) Enter as Company Mode");
                    System.out.println(" 3) Enter as Placement Cell Mode");
                    System.out.println(" 4) Return To Main Application");
                    response = scan.nextInt();

                    if (response == 1) {
                        studentMode: while (true) {
                            System.out.println("""
                                    \nChoose the Student Query to perform-
                                     1) Enter as student
                                     2) Add students
                                     3) Back""");
                            response = scan.nextInt();
                            if (response == 1) {
                                System.out.print("Enter roll no: ");
                                int roll = scan.nextInt();
                                Student current = cell.returnStudentObjectByRollNo(roll);
                                if (current == null) {
                                    System.out.println("Student not found, kindly recheck the roll no and try again.");
                                    continue studentMode;
                                }
                                studentMenu: while (true) {
                                    if (!current.isBlocked()) {
                                        System.out.println("\nWelcome, " + current.getName() + ".");
                                        System.out.println("""
                                                Choose an option:
                                                 1) Register For Placement Drive
                                                 2) Register For Company
                                                 3) Get all available companies
                                                 4) Get Current Status
                                                 5) Update CGPA
                                                 6) Accept offer
                                                 7) Reject offer
                                                 8) Back""");
                                        response = scan.nextInt();
                                        if (response == 1) {
                                            System.out.println("Enter date-time: (dd/mm/yy-hh:mm) ");
                                            scan.next();
                                            cell.registerStudent(current);
                                            System.out.println("Current details:");
                                            System.out.println(current);
                                        } else if (response == 2) {
                                            System.out.print("Enter company name: ");
                                            String companyName = scan.next();
                                            if (cell.isCompanyPresent(companyName)) {
                                                cell.addStudentToCompany(companyName, current);
                                                System.out.println("Successfully registered for " + companyName + ".");
                                            } else {
                                                System.out.println(companyName + " not found, kindly recheck and try again.");
                                            }
                                        } else if (response == 3) {
                                            cell.getAvailableCompanies(current);
                                        } else if (response == 4) {
                                            if (current.offersSize() != 0) {
                                                current.printHighestOffer();
                                            } else {
                                                System.out.println("Sorry, no updates. Kindly check back later!");
                                            }
                                        } else if (response == 5) {
                                            System.out.print("Enter updated CGPA: ");
                                            double c = scan.nextDouble();
                                            cell.requestCgpaChange(current, c);
                                            System.out.println("CGPA updated.");
                                        } else if (response == 6) {
                                            if (current.offersSize() == 0) {
                                                System.out.println("Sorry, nothing to accept.");
                                                continue;
                                            } else {
                                                current.setHighestCTC(current.highestOffer());
                                                System.out.println("Congratulations " + current.getName() + "! You have accepted the offer by " + current.getHighestCTC().getName());
                                                current.setPlaced(true);
                                            }
                                        } else if (response == 7) {
                                            if (current.offersSize() == 0) {
                                                System.out.println("Sorry, nothing to reject.");
                                                continue;
                                            } else {
                                                System.out.println("Are you sure you want to reject the offer by " + current.getHighestCTC().getName() + "? (Y/N)");
                                                String c = scan.next();
                                                if (c.equals("Y")) {
                                                    current.removeHighestOffer();
                                                    System.out.println("Offer rejected.");
                                                    if (current.offersSize() == 0) {
                                                        current.setBlocked(true);
                                                    }
                                                } else {
                                                    continue;
                                                }
                                            }
                                        } else if (response == 8) {
                                            continue studentMode;
                                        }
                                    }
                                    else {
                                        System.out.println("We're sorry to inform you that you have been blocked from the placement cycle \nbecause you rejected all the offers available to you.");
                                        continue studentMode;
                                    }
                                }
                            }
                            else if (response == 2) {
                                System.out.print("Enter number of students: ");
                                int n = scan.nextInt();
                                for (int i = 0; i < n; i++) {
                                    String name; int rollNo; double cgpa; String branch;
                                    System.out.println("Enter details ("+(i+1)+"/"+n+")");
                                    System.out.print("Name: ");
                                    scan.nextLine();
                                    name = scan.nextLine();
                                    System.out.print("Roll no: ");
                                    rollNo = scan.nextInt();
                                    System.out.print("CGPA: ");
                                    cgpa = scan.nextDouble();
                                    System.out.print("Branch: ");
                                    branch = scan.next();
                                    Student s = new Student(name, rollNo, cgpa, branch);
                                    cell.addStudent(s);
                                    System.out.println("Student successfully added.\n");
                                }
                            }
                            else if (response == 3) {
                                continue modeSelection;
                            }
                        }
                    }
                    if (response == 2) {
                        companyMode: while (true) {
                            System.out.println("""
                                    \nChoose the Company Query to perform:
                                     1) Add Company and Details
                                     2) Choose Company
                                     3) Get Available Companies
                                     4) Back""");
                            response = scan.nextInt();
                            if (response == 1) {
                                String name; String role; double ctc; double minCgpa;
                                System.out.print("Enter name: ");
                                name = scan.next();
                                System.out.print("Enter role offered: ");
                                role = scan.next();
                                System.out.print("Enter ctc: ");
                                ctc = scan.nextDouble();
                                System.out.print("Enter CGPA requirement: ");
                                minCgpa = scan.nextDouble();
                                Company c = new Company(name, role, ctc, minCgpa);
                                cell.addCompany(c);
                                System.out.println("Company added.\n" + c);
                            }
                            if (response == 2) {
                                companyEnterMenu: while (true) {
                                    System.out.println("\nChoose to enter an available Company: ");
                                    cell.printCompaniesIndexed();
                                    response = scan.nextInt();
                                    Company c = cell.getCompanyObject(response-1);
                                    companyMenu: while(true) {
                                        System.out.println("\nWelcome, " + c.getName());
                                        System.out.println("1) Update Role\n" +
                                                "2) Update Package\n" +
                                                "3) Update CGPA criteria\n" +
                                                "4) Register To Institute Drive\n" +
                                                "5) Back");
                                        response = scan.nextInt();
                                        if (response == 1) {
                                            System.out.print("Enter new role: ");
                                            String r = scan.next();
                                            c.updateRole(r);
                                            System.out.println("Role updated to " + r + ".");
                                        }
                                        if (response == 2) {
                                            System.out.print("Enter new package: ");
                                            double p = scan.nextDouble();
                                            c.updateCtc(p);
                                            System.out.println("Package updated to " + p + ".");
                                        }
                                        if (response == 3) {
                                            System.out.print("Enter new CGPA criteria: ");
                                            double p = scan.nextDouble();
                                            c.updateMinCgpa(p);
                                            System.out.println("Minimum CGPA updated to " + p + ".");
                                        }
                                        if (response == 4) {
                                            System.out.print("Enter date-time: (dd/mm/yy-hh:mm) ");
                                            String dt = scan.next();
                                            c.registerToInstituteDrive(cell, dt);
                                        }
                                        if (response == 5) {
                                            continue companyMode;
                                        }
                                    }
                                }
                            }
                            if (response == 3) {
                                System.out.println("Available Companies:");
                                cell.printCompaniesIndexed();
                                System.out.println();
                            }
                            if (response == 4) {
                                continue modeSelection;
                            }
                        }
                    }
                    if (response == 3) {
                        placementCellMode: while (true) {
                            System.out.println(
                                    """

                                            Welcome, IIITD Placement Cell
                                             1) Open Student Registrations
                                             2) Open Company Registrations
                                             3) Get Number of Student Registrations
                                             4) Get Number of Company Registrations
                                             5) Get Number of Offered/Unoffered/Blocked Students
                                             6) Get Student Details
                                             7) Get Company Details
                                             8) Get Average Package
                                             9) Get Company Process Results
                                             10) Exit"""
                            );
                            response = scan.nextInt();
                            if (response == 1) {
                                String startDate, endDate;
                                System.out.println("To being student registrations,");
                                System.out.print("Enter start date & time (dd/mm/yy-hh:mm): ");
                                startDate = scan.next();
                                System.out.print("Enter end date & time (dd/mm/yy-hh:mm): ");
                                endDate = scan.next();
                                cell.openStudentRegistrations(startDate, endDate);
                            }
                            else if (response == 2) {
                                String startDate, endDate;
                                System.out.println("To being company registrations,");
                                System.out.print("Enter start date & time (dd/mm/yy-hh:mm): ");
                                startDate = scan.next();
                                System.out.print("Enter end date & time (dd/mm/yy-hh:mm): ");
                                endDate = scan.next();
                                cell.openCompanyRegistrations(startDate, endDate);
                            }
                            else if (response == 3) {
                                System.out.println("Number of students registered:");
                                System.out.println(cell.numStudentRegs());
                            }
                            else if (response == 4) {
                                System.out.println("Number of companies registered:");
                                System.out.println(cell.numCompanyRegs());
                            }
                            else if (response == 5) {
                                System.out.print("Offered: ");
                                System.out.println(cell.numPlaced());
                                System.out.print("Un-offered: ");
                                System.out.println(cell.numUnplaced());
                                System.out.print("Offered: ");
                                System.out.println(cell.numBlocked());
                            }
                            else if (response == 6) {
                                System.out.print("Enter student's roll number: ");
                                int roll = scan.nextInt();
                                System.out.println(cell.printStudentByRollNo(roll));
                            }
                            else if (response == 7) {
                                System.out.print("Enter company name: ");
                                String n = scan.next();
                                System.out.println(cell.findCompanyByName(n));
                            }
                            else if (response == 8) {
                                System.out.print("Average Package: ");
                                cell.getAveragePackage();
                                System.out.println();
                            }
                            else if (response == 9) {
                                System.out.print("Enter company name: ");
                                String _name = scan.next();
                                cell.getCompanyResult(_name);
                            }
                            else if (response == 10) {
                                continue modeSelection;
                            }
                        }
                    }
                    if (response == 4) {
                        continue mainMenu;
                    }
                }
            }
            else if (response == 2) {
                break mainMenu;
            }
            else {
                System.out.println("Invalid value, try again.");
            }
        }
        System.out.println("Thank you for using FutureBuilder!");
    }
}

class Student {
    private String name;
    private int rollNo;
    private double cgpa;
    private String branch;
    private ArrayList<Company> offers = new ArrayList<Company>();
    private boolean isPlaced = false;
    private boolean hasRegistered = false;
    private boolean hasApplied = false;
    private boolean isOffered = false;
    private boolean isBlocked = false;
    private PlacementCell instituteCell;
    private double highestCtc = 0;
    private Company highestCTC;

    public Company currentHighest() {
        return highestCTC;
    }

    public void setPlaced(boolean placed) {
        isPlaced = placed;
    }

    public void printHighestOffer() {
        int maxIndex = 0; int i = 0;
        for (Company c : offers) {
            if (c.getCtc() > offers.get(maxIndex).getCtc()) {
                maxIndex = i;
            }
            i++;
        }
        this.highestCTC = offers.get(maxIndex);
        System.out.println("You have an offer from "+offers.get(maxIndex).getName()+"! Please accept the offer.");
    }

    public Company highestOffer() {
        int maxIndex = 0; int i = 0;
        for (Company c : offers) {
            if (c.getCtc() > offers.get(maxIndex).getCtc()) {
                maxIndex = i;
            }
            i++;
        }
        return offers.get(maxIndex);
    }

    public void removeHighestOffer() {
        int maxIndex = 0; int i = 0;
        for (Company c : offers) {
            if (c.getCtc() > offers.get(maxIndex).getCtc()) {
                maxIndex = i;
            }
            i++;
        }
        offers.remove(maxIndex);
    }
    public int offersSize() {
        return offers.size();
    }
    public double getHighestCtc() {
        return highestCtc;
    }
    public void addOffer(Company c) {
        offers.add(c);
    }
    public Student(String _name, int _rollNo, double _cgpa, String _branch) {
        name = _name;
        rollNo = _rollNo;
        cgpa = _cgpa;
        branch = _branch;
    }

    public String getName() {
        return name;
    }

    public void setOffered(boolean offered) {
        isOffered = offered;
    }

    public double getCTC() {
       return highestCTC.getCtc();
    }
    public double getCgpa() {
        return cgpa;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void registerForDrive(PlacementCell cell) {
        this.hasRegistered = true;
    }

    public void applyToCompany(Company c) {
        if (c.getMinCgpa() <= this.cgpa) {
            c.addStudentApplication(this);
            this.hasApplied = true;
        }
    }

    public void getAvailableCompanies(PlacementCell cell) {
        cell.getAvailableCompanies(this);
    }

    public void getCurrentStatus() {
        System.out.println("======STUDENT STATUS======");
        System.out.println(this);
        if (isOffered) {
            System.out.println("Company details: ");
            System.out.println(highestCTC);
        }
        System.out.println("==========================");
    }

    public void updateCgpa(double cg) {
        instituteCell.requestCgpaChange(this, cg);
    }

    public Company getHighestCTC() {
        return highestCTC;
    }

    public void setHighestCTC(Company highestCTC) {
        this.highestCTC = highestCTC;
    }

    public void setHighestCtc(double highestCtc) {
        this.highestCtc = highestCtc;
    }

    public void acceptOffer(Company c) {
        isOffered = true;
        isPlaced = true;
        highestCTC = c;
    }

    public void rejectOffer(Company c) {
        isOffered = true;
    }

    public void setCgpa(double cgpa) {
        this.cgpa = cgpa;
    }

    public boolean isPlaced() {
        return isPlaced;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public String toString() {
        return "Name: "+name+", CGPA: "+this.cgpa+", Placed: "+(this.isPlaced ? "Yes" : "No")+", Offered: "+(this.isOffered ? "Yes" : "No")+", Blocked: "+(this.isBlocked ? "Yes" : "No");
    }
}

class PlacementCell {
    private ArrayList<Student> studentsApplied = new ArrayList<Student>();
    private ArrayList<Student> studentsRegistered = new ArrayList<Student>();
    private ArrayList<Company> companiesApplied = new ArrayList<Company>();
    private ArrayList<Company> companiesRegistered = new ArrayList<Company>();
    private boolean studentRegs = false;
    private boolean companyRegs = false;
    private boolean companyRegsOver = false;

    public boolean isCompanyPresent(String _name) {
        for (Company c : companiesRegistered) {
            if (_name.equals(c.getName())) {
                return true;
            }
        }
        return false;
    }

    public Student returnStudentObjectByRollNo(int rollNo) {
        for (Student s : studentsApplied) {
            if (s.getRollNo() == rollNo) {
                return s;
            }
        }
        return null;
    }
    public void addStudentToCompany(String _name, Student s) {
        for (Company c : companiesRegistered) {
            if (_name.equals(c.getName())) {
                c.addStudentApplication(s);
            }
        }
    }
    public String findCompanyByName(String _name) {
        for (Company c : companiesApplied) {
            if (_name.equals(c.getName())) {
                return c.toString();
            }
        }
        return "'"+_name+"' not found, kindly recheck and try again.";
    }

    public void registerStudent(Student s) {
        if (studentRegs) {
            studentsRegistered.add(s);
            s.registerForDrive(this);
            System.out.println("You have been successfully registered.");
        }
        else {
            System.out.println("Sorry, student registrations haven't started yet.");
        }

    }
    public String printStudentByRollNo(int roll) {
        for (Student s : studentsApplied) {
            if (s.getRollNo() == roll) {
                return s.toString();
            }
        }
        return "Invalid roll no, student does not exist.";
    }
    public int numCompanies() {
        return companiesApplied.size();
    }

    public int numStudents() {
        return studentsApplied.size();
    }
    public boolean isCompanyRegs() {
        return companyRegs;
    }

    public boolean isCompanyRegsOver() {
        return companyRegsOver;
    }

    public boolean isStudentRegs() {
        return studentRegs;
    }

    public void openCompanyRegistrations(String startDate, String endDate) {
        this.companyRegs = true;
        System.out.println("Company Registrations are now open from " + startDate + " to " + endDate);
    }

    public Company getCompanyObject(int index) {
        return companiesApplied.get(index);
    }

    public Student getStudentObject(int index) {
        return studentsApplied.get(index);
    }

    public void openStudentRegistrations(String startDate, String endDate) {
        studentRegs = true;
        System.out.println("Student Registrations are now open from "+ startDate +" to " + endDate);
    }

    public void addStudent(Student s) {
        studentsApplied.add(s);
    }

    public void printCompaniesIndexed() {
        int i = 1;
        for (Company c : companiesApplied) {
            System.out.print(i+") ");
            System.out.println(c.getName());
            i++;
        }
        if (companiesApplied.size() == 0) {
            System.out.println("Sorry, no companies have registered yet.");
        }
    }
    public void getAvailableCompanies(Student s) {
        boolean found = false; int i = 1;
        System.out.println("Available companies:");
        for (Company c : companiesApplied) {
            if (c.getMinCgpa() <= s.getCgpa() && c.getCtc() >= 3*s.getHighestCtc()) {
                found = true;
                System.out.print(i + ") ");
                System.out.println(c);
                i++;
            }
        }
        if (!found) {
            System.out.println("Sorry, no company is available.");
        }
    }

    public void requestCgpaChange(Student s, double newCgpa) {
        s.setCgpa(newCgpa);
    }

    public int numStudentRegs() {
        System.out.println(studentsApplied.size());
        return studentsApplied.size();
    }

    public int numCompanyRegs() {
        System.out.println(companiesApplied.size());
        return companiesApplied.size();
    }

    public int numPlaced() {
        int count = 0;
        for (Student s : studentsApplied) {
            if (s.isPlaced()) {
                count++;
            }
        }
        System.out.println(count);
        return count;
    }

    public int numUnplaced() {
        return studentsApplied.size() - this.numPlaced();
    }

    public int numBlocked() {
        int count = 0;
        for (Student s : studentsApplied) {
            if (s.isBlocked()) {
                count++;
            }
        }
        System.out.println(count);
        return count;
    }

    public void getStudentDetails(int _rollNo) {
        for (Student s : studentsApplied) {
            if (s.getRollNo() == _rollNo) {
                System.out.println(s);
                break;
            }
        }
    }

    public void getCompanyDetails(String _name) {
        for (Company c : companiesApplied) {
            if (_name.equals(c.getName())) {
                System.out.println(c);
                break;
            }
        }
    }

    public void getAveragePackage() {
        double total = 0;
        int n = 0;
        for (Student s : studentsApplied) {
            total += s.getHighestCtc();
            n++;
        }
        System.out.println(total/n);
    }

    public void getCompanyResult(String _name) {
        for (Company c : companiesApplied) {
            if (_name.equals(c.getName())) {
                c.printProcessResult();
                return;
            }
        }
        System.out.println("Company not found, kindly recheck and try again.");
    }

    public void addCompany(Company c) {
        companiesApplied.add(c);
    }
    public void registerCompany(Company c) {
        companiesRegistered.add(c);
    }
}

class Company { // Date functionality left
    private String name;
    private String role;
    private ArrayList<Student> selected = new ArrayList<Student>();
    private double minCgpa;
    private double ctc;
    private LocalDateTime regDate;
    private boolean registered = false;
    private String regDateTime;

    public Company(String _name, String _role, double _ctc, double _minCgpa) {
        name = _name;
        role = _role;
        ctc = _ctc;
        minCgpa = _minCgpa;
    }
    public void printProcessResult() {
        System.out.println(selected.size() + " students selected: ");
        for (Student s : selected) {
            s.setOffered(true);
            s.addOffer(this);
            System.out.println(s);
        }
    }
    public double getCtc() {
        return ctc;
    }

    public String getName() {
        return name;
    }

    public void addStudentApplication(Student s) {
        selected.add(s);
    }

    public double getMinCgpa() {
        return minCgpa;
    }

    public void registerToInstituteDrive(PlacementCell cell, String dt) {
        if (cell.isCompanyRegs()) {
            registered = true;
            cell.registerCompany(this);
            this.regDateTime = dt;
            System.out.println("Company registered.");
        }
        else if (cell.isCompanyRegsOver()){
            System.out.println("Sorry, company registrations have been closed.");
        }
        else {
            System.out.println("Sorry, company registrations haven't started yet.");
        }
    }

    public boolean isRegistered() {
        return registered;
    }

    public void updateRole(String role) {
        this.role = role;
    }

    public void updateCtc(double ctc) {
        this.ctc = ctc;
    }

    public void updateMinCgpa(double minCgpa) {
        this.minCgpa = minCgpa;
    }

    public String toString() {
        return "Name: " + name + ", role: " + role + ", Minimum CGPA: " + minCgpa + ", CTC: " + ctc + ", Registered: " + (registered ? "Yes" : "No");
    }
}
