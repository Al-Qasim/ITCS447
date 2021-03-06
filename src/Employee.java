import java.util.GregorianCalendar;
import java.util.regex.Pattern;

public class Employee
{
    private long empID;
    private String firstName, lastName, department, position;
    private String gender;
    private GregorianCalendar birthDate;
    private float salary;
    public void setEmpID(long id) throws InvalidIDException {
        empID=id;
        if(!Pattern.matches("^[0-9]{9}$",id+""))
            throw new InvalidIDException(id);
    }
    public void setFirstName(String name){firstName=name;}
    public void setLastName(String name){lastName=name;}
    public void setDepartment(String dept){department=dept;}
    public void setPosition(String pos){position=pos;}
    public void setGender(String g){gender=g;}
    public void setBirthDate(GregorianCalendar date){birthDate=date;}
    public void setSalary(float sal){salary=sal;}
    public long getEmpID() {return empID;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getDepartment(){return department;}
    public String getPosition(){return position;}
    public String getGender(){return gender;}
    public GregorianCalendar getBirthDate(){return birthDate;}//might have to change
    public float getSalary(){return salary;}
    public void printEmployee()
    {
        System.out.println("EmpID: "+empID);
        System.out.println("First Name: "+ firstName);
        System.out.println("Last Name: "+ lastName);
        System.out.println("D.O.B: "+ birthDate);
        System.out.println("Gender: "+ gender);
        System.out.println("Department: "+ department);
        System.out.println("Position: "+ position);
        System.out.println("Salary: "+ salary);

    }
    public Employee()
    {
        empID=0;
        firstName="";
        lastName="";
        department="";
        position="";
        gender="U";
        birthDate= null;
        salary=0;
    }
    public Employee(long id, String fname, String lname, String g, GregorianCalendar date, String dept, float sal, String pos) throws InvalidIDException
    {
        setEmpID(id);
        firstName=fname;
        lastName=lname;
        department=dept;
        position=pos;
        gender=g;
        birthDate=date;
        salary=sal;
    }
}
