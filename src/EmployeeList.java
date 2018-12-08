import java.io.File;
import java.util.GregorianCalendar;

public class EmployeeList
{
    private Employee []list;
    private int size, maxSize;
    public void addEmployeeEnd(Employee e)
    {
        //check if full.
        list[size++]=e;
    }


    public void addEmployeeAt(Employee e, int index)
    {
        //check if full.
        for(int i=size; i>=index; i--)
        {
            list[i+1]=list[i];
        }
        list[index]=e;
        size++;
    }


    public void deleteEmployeeAt(int index)
    {
        //check if empty.
        for(int i=index; i<size; i++)
            list[i]=list[i+1];
        size--;
    }


    public void deleteEmployee(long id)
    {
        //check if empty.
        boolean found=false;
        for(int i=0; i<size; i++)
        {
            if(list[i].getEmpID()==id)
            {
                found=true;
                for(int j=size; j>=i; j--)
                    list[j+1]=list[j];
                break;
            }
        }
        if(found)
            size--;
        else
            System.out.println("No record was found for ID= "+id);
    }


    public void updateRecord(long id, String fname, String lname, char g, GregorianCalendar date, String dept, float sal, String pos)
    {
        //check if empty.
        boolean found=false;
        for(int i=0; i<size; i++)
        {
            if(list[i].getEmpID()==id)
            {
                found=true;
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
        if(found)
            System.out.println("Record was updated successfully= ");
        else
            System.out.println("No record was found for ID= "+id);
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

    public EmployeeList()
    {
        maxSize=50;
        size=0;
        list= new Employee[maxSize];
    }

    public EmployeeList(int max)
    {
        maxSize=max;
        size=0;
        list= new Employee[maxSize];
    }
    public EmployeeList(String path) //3rd constructor: for file type parameter.
    {
        File name= new File(path);
        if(name.exists())
        {
            
        }
    }
}
