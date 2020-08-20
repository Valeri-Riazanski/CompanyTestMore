package com.val.idea;

import java.time.LocalDate;
import java.util.ArrayList;

public class CompanyTest {
    public static void main(String[] args) {
        Company yourCompany = new Company("Ibaba");
        yourCompany.createProject("Balance",1_000_000.0, LocalDate.of(2020,12,31));
        yourCompany.createProject("SuperApp", 5E06,LocalDate.of(2021,3,30));
        yourCompany.printProjectsCompany();
        yourCompany.recruitEmployee("Alex", 23, 50_000.0, "junior");
        yourCompany.recruitEmployee("Maxx",28,70_000.0,"middle");
        yourCompany.recruitEmployee("Petr", 33, 90_000.0, "middle");
        yourCompany.printEmployeeCompany();
        Company.Employee coderOne = yourCompany.getEmployee(1);
        yourCompany.getProject(1).assignEmployeeProject(coderOne,LocalDate.of(2020,5,12) );
        Company.Employee coderTwo = yourCompany.getEmployee(2);
        yourCompany.getProject(2).assignEmployeeProject(coderTwo, LocalDate.of(2020,6,15));
        Company.Employee coderThree = yourCompany.getEmployee(3);
        yourCompany.getProject(1).assignEmployeeProject(coderThree,LocalDate.of(2020, 7, 18));
        yourCompany.getProject(1).statusProject();
        yourCompany.getProject(2).statusProject();
        yourCompany.getProject(1).unAssignEmployeeProject(coderThree, LocalDate.of(2020,8,2));
        yourCompany.getProject(1).statusProject();
        yourCompany.getEmployee(1).resumeEmployee();
        yourCompany.getEmployee(2).resumeEmployee();
        yourCompany.getEmployee(3).resumeEmployee();
    }
}

class Company{
    class Project{
        //fields Project
        private int idProject;
        private String nameProject;
        private double budget;
        private LocalDate deadLine;
        private int countEmployProject;
        private int nextIdEmployeeProject = 1;
        private ArrayList<StatusProjectLine> projectLines = new ArrayList<StatusProjectLine>();
        //constructors project
        public Project(String nameProject, double budget, LocalDate deadLine){
            this.idProject = nextIdProject;
            nextIdProject++;
            this.nameProject = nameProject;
            this.budget = budget;
            this.deadLine = deadLine;
        }
        //methods Project
        public String getNameProject(){
            return  this.nameProject;
        }
        public double getBudget(){
            return this.budget;
        }
        public LocalDate getDeadLine(){
            return this.deadLine;
        }
        public int getIdProject(){
            return this.idProject;
        }
        public void statusProject(){
            System.out.println(ConsoleColors.PURPLE + "Project's status: " + "Id=" + idProject + ", name="
                    + nameProject + ", budget="+ budget + ", deadLine=" + deadLine + ConsoleColors.RESET + "\n"
                    + ConsoleColors.GREEN + "ID | Employ  | assignDate | unAssignDate  " + ConsoleColors.RESET);

            for (StatusProjectLine line : this.projectLines){
                System.out.print(line.getId() + "  | " + line.getName()
                + "    | " + line.getAssignDate() + " | " + line.getUnAssignDate() + "\n");

            }
        }
        public void assignEmployeeProject(Employee coder, LocalDate assignDate){
            this.countEmployProject = nextIdEmployeeProject;
            nextIdEmployeeProject++;
            this.projectLines.add(new StatusProjectLine(this.countEmployProject, coder, assignDate));
            coder.createResumeLines(new ResumeLines(this, assignDate));
        }
        public void unAssignEmployeeProject(Employee coder, LocalDate unAssignDate){
            int coderId = coder.getIdEmployee();
            int lastIdLine = 0;
            for (int i = 0; i < projectLines.size(); i++) {
                if (coderId == projectLines.get(i).getIdEmployee()) {
                    lastIdLine = i;
                }
            }
            projectLines.get(lastIdLine).setUnAssignDate(unAssignDate);
            int projectId = this.idProject;
            int lastIdLineProject = 0;
            for (int k = 0; k < coder.getResumeLines().size(); k++) {
                if (projectId == coder.getResumeLines().get(k).getIdProject()) {
                    lastIdLineProject = coder.getResumeLines().get(k).getIdProject();
                }
            }
            coder.getResumeLines().get(lastIdLineProject - 1).setUnAssignDate(unAssignDate);
            }
    }
    class Employee{
        //fields Employee
        private int idEmployee;
        private String name;
        private int age;
        private double salary;
        private String position;
        ArrayList<ResumeLines> resumeLines = new ArrayList<ResumeLines>();
        //constructors Employee
        public Employee(String name, int age, double salary, String position){
            this.idEmployee = nextIdEmployee;
            nextIdEmployee++;
            this.name = name;
            this.age = age;
            this.salary = salary;
            this.position = position;
        }
        //methods Employee
        public int getIdEmployee(){
            return this.idEmployee;
        }
        public String getNameEmployee(){
            return this.name;
        }
        public int getAge(){
            return this.age;
        }
        public double getSalary(){
            return this.salary;
        }
        public String getPosition(){
            return this.position;
        }
        public void createResumeLines(ResumeLines resumeLines) {
            this.resumeLines.add(resumeLines);
        }
        public ArrayList<ResumeLines> getResumeLines() {
            return this.resumeLines;
        }
        public void resumeEmployee(){
            System.out.println(ConsoleColors.BLUE + "resume Employee: " + ", name=" + this.name
                    + ", position=" + this.position + ConsoleColors.RESET + "\n"
                    + ConsoleColors.YELLOW + "Project  | assignDate | unAssignDate  " + ConsoleColors.RESET);
            for (ResumeLines lines : resumeLines) {
                System.out.print(lines.getName() + " | " + lines.getAssignDate() + " | " + lines.getUnAssignDate() + "\n" );
            }
        }
    }
    class ConsoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset

        // Regular Colors
        public static final String BLACK = "\033[0;30m";   // BLACK
        public static final String RED = "\033[0;31m";     // RED
        public static final String GREEN = "\033[0;32m";   // GREEN
        public static final String YELLOW = "\033[0;33m";  // YELLOW
        public static final String BLUE = "\033[0;34m";    // BLUE
        public static final String PURPLE = "\033[0;35m";  // PURPLE
        public static final String CYAN = "\033[0;36m";    // CYAN
        public static final String WHITE = "\033[0;37m";   // WHITE
    }
    //fields Company
    private static int nextIdEmployee = 1;
    private static int nextIdProject = 1;
    private String nameCompany;
    private ArrayList<Project> projectsList = new ArrayList<Project>();
    private ArrayList<Employee> employeesList = new ArrayList<Employee>();
    //construction Company
    public Company(String nameCompany){
        this.nameCompany = nameCompany;
    }
    //methods Company
    public void createProject(String nameProject, double budget, LocalDate deadLine){
        projectsList.add(new Project(nameProject, budget, deadLine));
    }
    public void printProjectsCompany(){
        System.out.println(ConsoleColors.BLUE + "Company's projects:" + "\n"
                + ConsoleColors.YELLOW + "ID | Name      |  Budget    |  DeadLine " + ConsoleColors.RESET);
        for (int i = 0; i < projectsList.size(); i++){
            System.out.print( projectsList.get(i).getIdProject() + "  | " + projectsList.get(i).getNameProject()
                    + "   |  " + projectsList.get(i).budget + " | " + projectsList.get(i).getDeadLine() + "\n");
        }
    }
    public void recruitEmployee(String name, int age, double salary, String position){
        employeesList.add(new Employee(name, age, salary, position));
    }
    public void printEmployeeCompany(){
        System.out.println(ConsoleColors.BLUE + "Company's employee:" + "\n"
                + ConsoleColors.GREEN + "ID | Name   |  Salary  |  PositionSalary " + ConsoleColors.RESET);
        for (int i = 0; i < employeesList.size(); i++){
            System.out.print( employeesList.get(i).getIdEmployee() + "  | " + employeesList.get(i).getNameEmployee()
                    + "   |  " + employeesList.get(i).getSalary() + " | " + employeesList.get(i).getPosition() + "\n");
        }
    }
    public Project getProject(int idProject){
        return projectsList.get(idProject - 1);
    }
    public Employee getEmployee(int idEmployee){
        return  employeesList.get(idEmployee - 1);
    }
}
class StatusProjectLine{
    //fields
    private int id;
    private Company.Employee coder;
    private LocalDate assignDate;
    private LocalDate unAssignDate;
    //constructors
    public StatusProjectLine(int id, Company.Employee coder, LocalDate assignDate) {
        this.id = id;
        this.coder = coder;
        this.assignDate = assignDate;
    }
    //methods
    public void setUnAssignDate(LocalDate unAssignDate) {
        this.unAssignDate = unAssignDate;
    }
    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.coder.getNameEmployee();
    }
    public int getIdEmployee() {
        return  this.coder.getIdEmployee();
    }
    public String getAssignDate() {
        return this.assignDate.toString();
    }
    public String getUnAssignDate() {
        if (unAssignDate == null) {
            return LocalDate.now().toString();
        } else {
            return this.unAssignDate.toString();
        }
    }
}
class ResumeLines {
    //fields
    private  Company.Project project;
    private LocalDate assignDate;
    private  LocalDate unAssignDate;
    //constructors
    public ResumeLines(Company.Project project, LocalDate assignDate) {
        this.project = project;
        this.assignDate = assignDate;
    }
    //methods
    public void setUnAssignDate(LocalDate unAssignDate) {
        this.unAssignDate = unAssignDate;
    }
    public String getName() {
        return this.project.getNameProject();
    }
    public int getIdProject() {
        return this.project.getIdProject();
    }
    public String getAssignDate() {
        return this.assignDate.toString();
    }
    public String getUnAssignDate() {
        if (this.unAssignDate == null) {
            return LocalDate.now().toString();
        } else {
            return unAssignDate.toString();
        }
    }
}
