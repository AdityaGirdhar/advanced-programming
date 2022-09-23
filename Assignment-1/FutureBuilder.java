import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class FutureBuilder {
    public static String textBlocks() {
        return """
    ______   __  __  ______   __  __    _____    ______           ____     __  __    ____    __     _____    ______    _____
   / ____/  / / / / /_  __/  / / / /   / __  |  / ____/          / __ )   / / / /   /  _/   / /    / __  |  / ____/   / __  |
  / /_     / / / /   / /    / / / /   / /_/ /  / __/    ______  / __  |  / / / /    / /    / /    / / / /  / __/     / /_/ /
 / __/    / /_/ /   / /    / /_/ /   / _, _/  / /___   /_____/ / /_/ /  / /_/ /   _/ /    / /___ / /_/ /  / /___    / _, _/ 
/_/      |_____/   /_/    |_____/   /_/ |_|  /_____/          /_____/  |_____/   /___/   /_____//_____/  /_____/   /_/ |_|                                                                                                             
""";
    }

    public static void main(String[] args) {
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
        System.out.println(textBlocks());
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");

        Scanner scan = new Scanner(System.in);
        int response = 1;

        while (true) {
            System.out.println("Welcome!");
            System.out.println(" 1) Enter the application");
            System.out.println(" 2) Exit");
            response = scan.nextInt();

            if (response == 2) {
                break;
            }

            System.out.println("Choose the mode you want to enter in:");
            System.out.println(" 1) Enter as Student Mode");
            System.out.println(" 2) Enter as Company Mode");
            System.out.println(" 3) Enter as Placement Cell Mode");
            System.out.println(" 4) Return To Main Application");
            response = scan.nextInt();

            if (response == 4) {
                continue;
            }
        }
        System.out.println("Thank you for using FutureBuilder!");
    }
}

class Student { // Make ArrayList of companies applied to and companies accepted
    private String name;
    private int rollNo;
    private double cgpa;
    private String branch;
    private boolean isPlaced = false;
    private boolean hasRegistered = false;
    private boolean hasApplied = false;
    private boolean isOffered = false;
    private boolean isBlocked = false;
    private PlacementCell instituteCell;
    private Company highestCTC;

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
        cell.addStudent(this);
        this.hasRegistered = true;
    }

    public void applyToCompany(Company c) {
        if (c.getMinCgpa() <= this.cgpa) {
            c.addStudent(this);
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

    public boolean isBlocked() {
        return this.isBlocked;
    }

    public String toString() {
        return "Name: "+name+", Offered: "+(this.isOffered ? "Yes" : "No")+", Blocked: "+(this.isBlocked ? "Yes" : "No");
    }
}

class PlacementCell {
    private ArrayList<Student> studentsApplied = new ArrayList<Student>();
    private ArrayList<Company> companiesRegistered = new ArrayList<Company>();
    private boolean studentRegs = false;
    private boolean companyRegs = false;
    private boolean companyRegsOver = false;

    public void openCompanyRegistrations() {
        companyRegs = true;
        System.out.println("Company Registrations are now open! The registrations start from TODAY and end on");
    }

    public void openStudentRegistrations() {
        studentRegs = true;
        System.out.println("Student Registrations are now open! The registrations start from TODAY ("+ LocalDateTime.now() +") and end on" + LocalDateTime.now().plusDays(7));
    }

    public void addStudent(Student s) {
        studentsApplied.add(s);
    }

    public void getAvailableCompanies(Student s) {
        boolean found = false;
        for (Company c : companiesRegistered) {
            if (c.getMinCgpa() <= s.getCgpa()) {
                found = true;
                System.out.println(c);
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
        System.out.println(companiesRegistered.size());
        return companiesRegistered.size();
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

    public void getStudentDetails(String _name, int _rollNo) {
        for (Student s : studentsApplied) {
            if (s.getRollNo() == _rollNo) {
                System.out.println(s);
                break;
            }
        }
    }

    public void getCompanyDetails(String _name) {
        for (Company c : companiesRegistered) {
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
            total += s.getCTC();
            n++;
        }
        System.out.println(total/n);
    }

    public void getCompanyResult(String _name) {
        for (Company c : companiesRegistered) {
            if (_name.equals(c.getName())) {
                c.printProcessResult();
                break;
            }
        }
    }

    public void addCompany(Company c) {
        companiesRegistered.add(c);
    }
}

class Company { // Date functionality left
    private String name;
    private String role;
    private ArrayList<Student> applied = new ArrayList<Student>();
    private ArrayList<Student> selected = new ArrayList<Student>();
    private double minCgpa;
    private double ctc;
    private LocalDateTime regDate;

    public void printProcessResult() {
        System.out.println(selected.size() + " students selected: ");
        for (Student s : selected) {
            System.out.println(s);
        }
    }
    public double getCtc() {
        return ctc;
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student s) {
        applied.add(s);
    }

    public double getMinCgpa() {
        return minCgpa;
    }

    public void registerToInstituteDrive(PlacementCell cell, String _name, String _role, double _ctc, double _minCgpa) {
        this.name = _name;
        this.role = _role;
        this.ctc = _ctc;
        this.minCgpa = _minCgpa;
        cell.addCompany(this);
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
        return "Name: " + name + ", role: " + role + ", Minimum CGPA: " + minCgpa + ", CTC: " + ctc;
    }
}
