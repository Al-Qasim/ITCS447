import com.github.lgooddatepicker.components.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.Properties;

public class EmployeeRecordsManager extends JFrame implements ActionListener
{
    private EmployeeList listProgress;
    private JFileChooser fileChooser;
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
        listModel.addElement(String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %20s",
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
    public EmployeeRecordsManager(int num)
    {
        super("Create New List");
        setLayout(new FlowLayout());


        lblFname= new JLabel("Yet to Implement");
        add(lblFname);


        setSize(500, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==createNew)
        {
            new EmployeeRecordsManager("");
        }
        else if(e.getSource()==importExisting)
        {
            //new EmployeeRecordsManager(1);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser= new JFileChooser();
            fileChooser.setFileFilter(filter);
            fileChooser.showDialog(this, "Select");
        }
        else if(e.getSource()==addEmp)
        {

            boolean found = false;
            char ge;
            long temp= Long.parseLong(tfID.getText());

            selectedDate= GregorianCalendar.from(datePicker.getDate().atStartOfDay(ZoneId.systemDefault()));

            if(cbGender.getSelectedItem().toString() =="Male")
                ge='M';
            else
                ge='F';
            float money= Float.parseFloat(tfSal.getText());

            found=listProgress.searchByEmpId(temp);
            if(found)
                JOptionPane.showMessageDialog(this, "There is already a list member with the same ID!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else{
            Employee New= null;
            try {
                New = new Employee(temp, tfFname.getText(), tfLname.getText(), ge,
                                        selectedDate, cbDept.getSelectedItem().toString(),
                                        money, cbPos.getSelectedItem().toString());
            } catch (InvalidIDException e1) {
                e1.printStackTrace();
            }
            listProgress.addEmployeeEnd(New);




            String E= String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %20s",
                    tfID.getText(), tfFname.getText(),tfLname.getText(),
                    cbGender.getSelectedItem(), datePicker.getText(),
                    cbDept.getSelectedItem(), cbPos.getSelectedItem(), tfSal.getText());
            listModel.addElement(E);}
        }

        else if(e.getSource()==delEmp)
        {
            if(listInCreation.getSelectedIndex()!=0) {
                listModel.removeElementAt(listInCreation.getSelectedIndex());
                listProgress.deleteEmployeeAt(listInCreation.getSelectedIndex()+1);
            }
            else
                JOptionPane.showMessageDialog(this, "There are no more records to delete!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        else if(e.getSource()==finalize)
        {

        }

    }

    public static void main(String[] args)
    { new EmployeeRecordsManager(); }
}
