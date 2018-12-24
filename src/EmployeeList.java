import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmployeeList
{
    public Employee []list;
    private int size, maxSize;
    public void addEmployeeEnd(Employee e)
    {
        if(!(size==maxSize))
            list[size++]=e;
        else
            System.out.println("List is FULL!");
    }

    /**
     * this function to insert the Employee at specific position
     * by move the array to the right starting from the size + 1 to the position
     * @param e object of Employee class
     * @param index integer contain the position of the insert
     */

    public void addEmployeeAt(Employee e, int index) {
        if (!(size == maxSize))
        {
            for (int i = size; i >= index; i--) {
                list[i + 1] = list[i];
            }
            list[index] = e;
            size++;
        } else
            System.out.println("List is FULL!");
    }

    /**
     * this function to delete the Employee at specific position
     * by move the array to the left starting from the position to the size
     * @param index integer contain the position of the insert
     */

    public void deleteEmployeeAt(int index) {
        if (!(size == maxSize))
        {
            for (int i = index; i < size; i++)
                list[i] = list[i + 1];
            size--;
        } else
            System.out.println("List is FULL!");
    }

    /**
     * this function to delete Employee has specific id
     * @param id long integer contain the id of the employee that we want to deleted
     */

    public void deleteEmployee(long id) {
        if (!isEmpty()) {
            boolean found = false;
            for (int i = 0; i < size; i++) {
                if (list[i].getEmpID() == id) {
                    found = true;
                    for (int j = size; j >= i; j--)
                        list[j + 1] = list[j];
                    break;
                }
            }
            if (found)
                size--;
            else
                System.out.println("No record was found for ID= " + id);
        } else
            System.out.println("List is EMPTY!");
    }


    public void updateRecord(long id, String fname, String lname, String g, GregorianCalendar date, String dept, float sal, String pos) {
        if (!isEmpty())
        {
            boolean found = false;
            for (int i = 0; i < size; i++) {
                if (list[i].getEmpID() == id) {
                    found = true;
                    list[i].setFirstName(fname);
                    list[i].setLastName(lname);
                    list[i].setGender(g);
                    list[i].setBirthDate(date);
                    list[i].setDepartment(dept);
                    list[i].setSalary(sal);
                    list[i].setPosition(pos);
                    break;
                }
            }
            if (found)
                System.out.println("Record was updated successfully= ");
            else
                System.out.println("No record was found for ID= " + id);
        } else
            System.out.println("List is EMPTY!");
    }


    public boolean searchByEmpId(long id) {
        boolean found = false;
        if (isEmpty())
            System.out.println("List is empty.");
        else {
            int i;
            for (i = 0; i < size; i++) {
                if (list[i].getEmpID() == id) {
                    found = true;
                    break;
                }
            }
//            if (found)
//                System.out.println("Record was found at index= " + i);
//            else
//                System.out.println("No record was found for ID= " + id);
        }
        return found;
    }


    public void sortByEmpId(long id) {
//        if (isEmpty())
//           System.out.println("List is empty.");
//        else {
//           boolean found=false;
//        int i;
//        for(i=0; i<size; i++)
//        {
//            if(list[i].getEmpID()==id)
//            {
//                found=true;
//                break;
//            }
//        }
//    }
//        if(found)
//            System.out.println("Record was found at index= "+ i);
//        else
//            System.out.println("No record was found for ID= "+id);
    }


    public boolean isEmpty()
    {
        return(size==0);
    }

    public int size(){ return size;}

    public void expandArray(int newSize)
    {
        Employee [] newList= new Employee[newSize];
        list=newList;
        maxSize=newSize;
    }

    /**
     * constructor without parameter
     */

    public EmployeeList()
    {
        maxSize=50;
        size=0;
        list= new Employee[maxSize];
    }

    /**
     * constructor with size of the list of employee
     * @param max
     */
    public EmployeeList(int max)
    {
        maxSize=max;
        size=0;
        list= new Employee[maxSize];

    }

    /**
     * constructor with string contain the path of the file to get the data from it
     * by using some object like "scanner" to read the data from the file ,
     * "Employee" to set all data that was take it from the file and
     * "EmployeeList" to add the Employee object.
     * also, this constructor has "try and catch" if the file not found or the id is invalid
     * @param path string contain the path of the file
     */
    public EmployeeList(String path) //3rd constructor: for file type parameter.
    {

        EmployeeList emplist= new EmployeeList();
        File name= new File(path);
        if(name.exists())
        {
//            Employee employee= new Employee();
            try {
                Scanner input= new Scanner(name);
                while(input.hasNext())
                {
                    Employee employee= new Employee();

                    employee.setEmpID(input.nextLong());
                    employee.setFirstName(input.next());
                    employee.setLastName(input.next());
                    employee.setGender(input.next());
//                    employee.setGender(input.next(".").charAt(0));


                    String NDate = input.next();
                    String D[]=NDate.split("/");
                    GregorianCalendar temp= new GregorianCalendar(Integer.parseInt(D[2]),Integer.parseInt(D[1]),Integer.parseInt(D[0]));//For DOB
//                    temp.setTime(sdf.parse(input.next()));//For DOB
                    employee.setBirthDate(temp);//For DOB

                    employee.setDepartment(input.next());
                    employee.setPosition(input.next());
                    employee.setSalary(input.nextFloat());
                    emplist.addEmployeeEnd(employee);
                }
                list= emplist.list;
                size= emplist.size;
                maxSize= emplist.maxSize;


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidIDException e) {
                e.printStackTrace();
            }
//            catch (ParseException e) {
//                e.printStackTrace();
//            }
        }
        else
            System.out.println(String.format("%s    %s", path, "Does Not Exist!"));
    }
}
