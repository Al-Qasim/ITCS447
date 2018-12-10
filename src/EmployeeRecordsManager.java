import com.github.lgooddatepicker.components.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.ZoneId;
import java.util.Date;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.Properties;

public class EmployeeRecordsManager extends JFrame implements ActionListener
{
    private EmployeeList listProgress;
    private JFileChooser fileChooser= new JFileChooser();
    private File opened;
    private Formatter writer;
    private int listSize; //what for?
    private JButton createNew, importExisting, addEmp, delEmp, finalize;
    private JLabel lblID, lblFname, lblLname, lblDOB, lblDept, lblGender, lblPos, lblSal;
    private JTextField tfID, tfFname, tfLname, tfSal;
    private DatePicker datePicker;
    private DefaultListModel listModel;
    private JList listInCreation;
    private JComboBox cbGender, cbDept, cbPos;
    private GregorianCalendar selectedDate;
    private JPanel lowerPanel, personalPanel, proPanel;
    private static final String []gender={"Male","Female"};
    private static final String []departments={"Production", "Marketing", "Store and Purchase",
                                                "Finance", "Customer Service", "Overall Management"};
    private static final String []positions={"General Manager", "Manager", "Engineer", "Supervisor", "Accountant",
                                              "Technician", "Mechanic", "Secretary", "Clerk", "Labor"};
    public EmployeeRecordsManager()
    {
        super("Employee Records Manager");
        setLayout(new FlowLayout());

        createNew= new JButton("Create New List", new ImageIcon(getClass().getResource("newListV3.png")));
        importExisting= new JButton("Import Existing  ", new ImageIcon(getClass().getResource("importV3.png")));
        Box vBox= Box.createVerticalBox();
        vBox.add(createNew);
        createNew.addActionListener(this);
        importExisting.addActionListener(this);
        vBox.add(importExisting);
        add(vBox);


        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    public EmployeeRecordsManager(String create)
    {
        super("Create New List");
        setLayout(new FlowLayout());


        Box h1= Box.createVerticalBox();
        Box h2= Box.createVerticalBox();
        Box h3= Box.createVerticalBox();

        personalPanel= new JPanel();
        // proPanel.setSize(800,300);
        personalPanel.setBorder(new TitledBorder("Personal Information"));

        lblID= new JLabel("Employee ID: ");
        h1.add(lblID);
        tfID=new JTextField("", 15);
        h1.add(tfID);

        lblFname= new JLabel("First Name: ");
        h1.add(lblFname);
        tfFname=new JTextField("", 15);
        h1.add(tfFname);

        lblLname= new JLabel("Last Name: ");
        h1.add(lblLname);
        tfLname=new JTextField("", 15);
        h1.add(tfLname);



        lblGender= new JLabel("Gender: ");
        h1.add(lblGender);
        cbGender= new JComboBox(gender);
        h1.add(cbGender);



        lblDOB= new JLabel("Date Of Birth: ");
        h1.add(lblDOB);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        datePicker = new DatePicker();
        h1.add(datePicker);

        selectedDate= new GregorianCalendar();

        personalPanel.add(h1);
        add(personalPanel);

        proPanel= new JPanel();
       // proPanel.setSize(800,300);
        proPanel.setBorder(new TitledBorder("Professional Information"));


        lblDept= new JLabel("Department: ");
        h2.add(lblDept);
        cbDept= new JComboBox(departments);
        h2.add(cbDept);


        lblPos= new JLabel("Position: ");
        h2.add(lblPos);
        cbPos= new JComboBox(positions);
        h2.add(cbPos);


        lblSal= new JLabel("Salary: ");
        h2.add(lblSal);
        tfSal=new JFormattedTextField();
        h2.add(tfSal);
        proPanel.add(h2);
        add(proPanel);

        addEmp= new JButton("Add Employee");
        addEmp.addActionListener(this);
        h3.add(addEmp);
        add(h3);

        Box lowerBox= Box.createVerticalBox();
        lowerPanel= new JPanel();
        //lowerPanel.setSize(800,300);
        lowerPanel.setBorder(new TitledBorder("Current List Records"));


        listModel= new DefaultListModel();
        listInCreation= new JList(listModel);
        listModel.addElement(String.format("%-25s %-25s %-25s %-25s %-25s %-25s %-25s %-25s",
                                            "Employee ID", "First Name","Last Name","Gender", "D.O.B.",
                                            "Department", "Position", "Salary"));
        listInCreation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listInCreation.setVisibleRowCount(8);
        lowerBox.add(new JScrollPane(listInCreation));


        Box btnBox= Box.createHorizontalBox();
        delEmp= new JButton("Remove Employee");
        delEmp.addActionListener(this);
        btnBox.add(delEmp);
        finalize= new JButton("Finalize List");
        finalize.addActionListener(this);
        btnBox.add(finalize);
        lowerBox.add(btnBox);
        lowerPanel.add(lowerBox);
        add(lowerPanel);

        listProgress= new EmployeeList(100);
        //listSize= 0;

        setSize(1000, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }
    public EmployeeRecordsManager(File f)
    {
        super("Create New List");
        setLayout(new FlowLayout());


        Box lowerBox= Box.createVerticalBox();
        lowerPanel= new JPanel();
        //lowerPanel.setSize(800,300);
        lowerPanel.setBorder(new TitledBorder("Current List Records"));


        listModel= new DefaultListModel();
        listInCreation= new JList(listModel);
        listModel.addElement(String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %20s",
                "Employee ID", "First Name","Last Name","Gender", "D.O.B.",
                "Department", "Position", "Salary"));

        System.out.println(f.getAbsolutePath());
        EmployeeList imported= new EmployeeList(f.getAbsolutePath()); // Create new list by passing absolute path to the constructor of employee list.
        for(int i=0; i<imported.size(); i++)
        {
            listModel.addElement(String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %20s",
                    imported.list[i].getEmpID(), imported.list[i].getFirstName(),imported.list[i].getLastName(),imported.list[i].getGender(), imported.list[i].getBirthDate(),
                    imported.list[i].getDepartment(), imported.list[i].getPosition(), imported.list[i].getSalary()));
        }

        listInCreation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listInCreation.setVisibleRowCount(8);
        lowerBox.add(new JScrollPane(listInCreation));
        lowerPanel.add(lowerBox);
        add(lowerPanel);



        setSize(1000, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==createNew)
        {
            new EmployeeRecordsManager("");
        }
        else if(e.getSource()==importExisting) // if import button is pressed.
        {

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt"); //extension filter for filechooser.
            fileChooser.addChoosableFileFilter(filter);//add filer to filechooser.
            int status= fileChooser.showOpenDialog(this);//save value of selection.
            if(status== fileChooser.APPROVE_OPTION)// open filechooser dialog when import button is pressed.
            {
                opened= fileChooser.getSelectedFile();
                new EmployeeRecordsManager(opened);
            }
        }

        else if(e.getSource()==addEmp) {

            if (tfID.getText().equals("") || tfFname.getText().equals("") || tfLname.getText().equals("") ||
                    datePicker.getText().equals("") || tfSal.getText().equals(""))
                JOptionPane.showMessageDialog(this, "You must fill in all required fields!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else
                {
                boolean found;
                char ge;
                long temp = Long.parseLong(tfID.getText());

                selectedDate = GregorianCalendar.from(datePicker.getDate().atStartOfDay(ZoneId.systemDefault()));

                if (cbGender.getSelectedItem().toString().equals("Male"))
                    ge = 'M';
                else
                    ge = 'F';
                float money = Float.parseFloat(tfSal.getText());

                found = listProgress.searchByEmpId(temp);
                if (found)
                    JOptionPane.showMessageDialog(this, "There is already a list member with the same ID!",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                else {
                    Employee New = null; //To New it in try
                    try {
                        New = new Employee(temp, tfFname.getText(), tfLname.getText(), ge,
                                selectedDate, cbDept.getSelectedItem().toString(),
                                money, cbPos.getSelectedItem().toString());
                    } catch (InvalidIDException e1) {
                        e1.printStackTrace();
                    }
                    listProgress.addEmployeeEnd(New);



                String E = String.format("%-25s %-25s %-25s %-25s %-25s %-25s %-25s %-25s",
                        tfID.getText(), tfFname.getText(), tfLname.getText(),
                        cbGender.getSelectedItem(), datePicker.getText(),
                        cbDept.getSelectedItem(), cbPos.getSelectedItem(), tfSal.getText());
                listModel.addElement(E);}
            }
        }

        else if(e.getSource()==delEmp)
        {
            if (listInCreation.isSelectionEmpty())
                JOptionPane.showMessageDialog(this, "You must select a record to delete!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else if(listInCreation.getSelectedIndex()!=0) {
                listModel.removeElementAt(listInCreation.getSelectedIndex());
                listProgress.deleteEmployeeAt(listInCreation.getSelectedIndex()+1);
            }
            else
                JOptionPane.showMessageDialog(this, "This is not a record of the list!",
                        "ERROR", JOptionPane.WARNING_MESSAGE);
        }

        else if(e.getSource()==finalize) {
            //do this now.
            if(listModel.getSize()==1)
                JOptionPane.showMessageDialog(this, "There are no records in the list!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else{
                try {
                    int status = fileChooser.showSaveDialog(this);
                    if (status == fileChooser.APPROVE_OPTION) {
                        File save = new File(fileChooser.getSelectedFile().getAbsolutePath());
                        writer = new Formatter(save);
                        for (int i = 0; i < listProgress.size(); i++) {
                            writer.format("%-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s\n",
                                    listProgress.list[i].getEmpID(), listProgress.list[i].getFirstName(), listProgress.list[i].getLastName(),
                                    listProgress.list[i].getGender(), listProgress.list[i].getBirthDate(),
                                    listProgress.list[i].getDepartment(), listProgress.list[i].getPosition(), listProgress.list[i].getSalary());
                        }
                        writer.close();
                    }
                    }catch(FileNotFoundException e1){
                    e1.printStackTrace();
                }


            }
        }

    }

    public static void main(String[] args)
    { new EmployeeRecordsManager(); }
}
