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
import java.util.*;

public class EmployeeRecordsManager extends JFrame implements ActionListener
{
    private EmployeeList listProgress;
    /**
     * @param fileChooser it GUI object that let the user choose file from the computer
     */
    private final JFileChooser fileChooser= new JFileChooser(System.getProperty("user.dir"));
    private File opened;
    private Formatter writer;
    private int globalUpdateIndex; //what for?
    private JButton createNew, importExisting, addEmp, delEmp, finalize, updateEmp, updateInfo, countRows;
    private JLabel lblID, lblFname, lblLname, lblDOB, lblDept, lblGender, lblPos, lblSal;
    private JTextField tfID, tfFname, tfLname, tfSal;
    private DatePicker datePicker;
    /**
     *
     */
    private DefaultListModel listModel;
    private JList listInCreation;
    private JComboBox cbGender, cbDept, cbPos;
    private GregorianCalendar selectedDate;
    private JPanel lowerPanel, personalPanel, proPanel;
    private static final String []gender={"Male","Female"};
    private static final String []departments={"Production", "Marketing", "Store-and-Purchase",
            "Finance", "Customer-Service", "Overall-Management"};
    private static final String []positions={"General-Manager", "Manager", "Engineer", "Supervisor", "Accountant",
            "Technician", "Mechanic", "Secretary", "Clerk", "Labor"};

    private FDate BDate=new FDate();
    private JComboBox days=new JComboBox(BDate.getDays());
    private JComboBox months=new JComboBox(BDate.getMonths());
    private JComboBox years=new JComboBox(BDate.getYears());
    private JLabel daylbl=new JLabel("Day    ");
    private JLabel monthlbl=new JLabel("Month    ");
    private JLabel yearlbl=new JLabel("Year");
    private JDialog updateDialog;

    /**
     * Default constructor without parameter to let the use choose between create list or import
     * existing list from the computer
     */

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
        setLocationRelativeTo(null);
    }

    /**
     * the constructor with string parameter that constructor to create new list
     * @param create string to know if the user press create new list or not
     */
    public EmployeeRecordsManager(String create)
    {
        super("Create New List");
        setLayout(new FlowLayout());

        /**
         * create some Box to control the layout
         * @param h1 it is VerticalBox to control the layout of the Personal information Jpanel
         * @param hDate1b1 it horizontalBox that add to 'h1' to control the date as horizontal style
         * @param hDate it same as 'hDate1b1' but for the JComboBox if date
         * @param h2 it to control the layout of Professional Information
         * @param h3 it to control the layout of add Employee button
         */
        Box h1= Box.createVerticalBox();
        Box h2= Box.createVerticalBox();
        Box h3= Box.createVerticalBox();
        Box hDatelbl= Box.createHorizontalBox();
        Box hDate= Box.createHorizontalBox();

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

        hDatelbl.add(daylbl);
        hDatelbl.add(monthlbl);
        hDatelbl.add(yearlbl);

        h1.add(hDatelbl);

        hDate.add(days);
        hDate.add(months);
        hDate.add(years);

        h1.add(hDate);

//        Properties p = new Properties();
//        p.put("text.today", "Today");
//        p.put("text.month", "Month");
//        p.put("text.year", "Year");
//        datePicker = new DatePicker();
//        h1.add(datePicker);

//        selectedDate= new GregorianCalendar();

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

        /**
         * @param listModel
         * @param ListInCreation
         * @param lowerBox
         *
         * all of them to print the list with fixed format and layout
         */
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
        updateEmp= new JButton("Update Employee");
        updateEmp.addActionListener(this);
        btnBox.add(updateEmp);
        countRows= new JButton("Record Count");
        countRows.addActionListener(this);
        finalize= new JButton("Finalize List");
        finalize.addActionListener(this);
        btnBox.add(countRows);
        btnBox.add(finalize);
        lowerBox.add(btnBox);
        lowerPanel.add(lowerBox);
        add(lowerPanel);

        listProgress= new EmployeeList(100);
        //listSize= 0;

        setSize(1000, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * this constructor with file parameter to get file from the user "after press 'import Existing' in the first Gui "
     * @param f file parameter contain the file
     */
    public EmployeeRecordsManager(File f)
    {
        super("Edit Existing List");
        setLayout(new FlowLayout());


        Box h1= Box.createVerticalBox();
        Box h2= Box.createVerticalBox();
        Box h3= Box.createVerticalBox();
        Box hDatelbl= Box.createHorizontalBox();
        Box hDate= Box.createHorizontalBox();

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

        hDatelbl.add(daylbl);
        hDatelbl.add(monthlbl);
        hDatelbl.add(yearlbl);

        h1.add(hDatelbl);

        hDate.add(days);
        hDate.add(months);
        hDate.add(years);

        h1.add(hDate);

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

        System.out.println(f.getAbsolutePath());
        //EmployeeList
        /**
         * Create new list by passing absolute path to the constructor of employee list.
         * then create for loop  to print the list by object of "DefaultListModel " named listModel
         * and get the value from object of " EmployeeList " named listProgress
         *
         */
        listProgress= new EmployeeList(f.getAbsolutePath()); //
        for(int i=0; i<listProgress.size(); i++)
        {
            String NDate=Integer.toString(listProgress.list[i].getBirthDate().get(Calendar.DAY_OF_MONTH))
                    + "/" + Integer.toString(listProgress.list[i].getBirthDate().get(Calendar.MONTH))
                    + "/" + Integer.toString(listProgress.list[i].getBirthDate().get(Calendar.YEAR));

            listModel.addElement(String.format("%-25s %-25s %-25s %-25s %-25s %-25s %-25s %-25s",
                    listProgress.list[i].getEmpID(), listProgress.list[i].getFirstName(),
                    listProgress.list[i].getLastName(),listProgress.list[i].getGender(),
                    NDate, listProgress.list[i].getDepartment(),
                    listProgress.list[i].getPosition(), listProgress.list[i].getSalary()));
        }
        //listProgress= imported;
        listInCreation.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listInCreation.setVisibleRowCount(8);
        lowerBox.add(new JScrollPane(listInCreation));
        lowerPanel.add(lowerBox);

        Box btnBox= Box.createHorizontalBox();
        delEmp= new JButton("Remove Employee");
        delEmp.addActionListener(this);
        btnBox.add(delEmp);
        finalize= new JButton("Finalize List");
        finalize.addActionListener(this);
        updateEmp= new JButton("Update Employee");
        updateEmp.addActionListener(this);
        countRows= new JButton("Record Count");
        countRows.addActionListener(this);
        btnBox.add(updateEmp);
        btnBox.add(countRows);
        btnBox.add(finalize);
        lowerBox.add(btnBox);
        lowerPanel.add(lowerBox);
        add(lowerPanel);




        setSize(1000, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    /**
     * function to get the user option to edit the information for one of the employee in the list by create dialog
     * contain the information of employee where parameter 'updateIndex' = his index in the list
     * @param updateIndex integer has the index of the employee we want to edit his information
     */
    public void updatePanel(int updateIndex)
    {

        updateDialog= new JDialog();
        updateDialog.setLayout(new FlowLayout());


        Box h1= Box.createVerticalBox();
        Box hVert= Box.createVerticalBox();
        Box h2= Box.createVerticalBox();
        Box hDatelbl= Box.createHorizontalBox();
        Box hDate= Box.createHorizontalBox();

        personalPanel= new JPanel();
        // proPanel.setSize(800,300);
        personalPanel.setBorder(new TitledBorder("Personal Information"));

        lblID= new JLabel("Employee ID: ");
        h1.add(lblID);
        tfID=new JTextField(listProgress.list[updateIndex].getEmpID()+"", 15);
        h1.add(tfID);
        tfID.setEditable(false);

        lblFname= new JLabel("First Name: ");
        h1.add(lblFname);
        tfFname=new JTextField(listProgress.list[updateIndex].getFirstName()+"", 15);
        h1.add(tfFname);

        lblLname= new JLabel("Last Name: ");
        h1.add(lblLname);
        tfLname=new JTextField(listProgress.list[updateIndex].getLastName()+"", 15);
        h1.add(tfLname);



        lblGender= new JLabel("Gender: ");
        h1.add(lblGender);
        cbGender= new JComboBox(gender);
        cbGender.setSelectedItem(listProgress.list[updateIndex].getGender());
        h1.add(cbGender);



        lblDOB= new JLabel("Date Of Birth: ");
        h1.add(lblDOB);

        hDatelbl.add(daylbl);
        hDatelbl.add(monthlbl);
        hDatelbl.add(yearlbl);

        h1.add(hDatelbl);

        hDate.add(days);
        days.setSelectedItem(listProgress.list[updateIndex].getBirthDate().get(Calendar.DAY_OF_MONTH)+"");
        hDate.add(months);
        months.setSelectedItem(listProgress.list[updateIndex].getBirthDate().get(Calendar.MONTH)+"");
        hDate.add(years);
        years.setSelectedItem(listProgress.list[updateIndex].getBirthDate().get(Calendar.YEAR)+"");

        h1.add(hDate);

        personalPanel.add(h1);

        proPanel= new JPanel();
        proPanel.setBorder(new TitledBorder("Professional Information"));


        lblDept= new JLabel("Department: ");
        h2.add(lblDept);
        cbDept= new JComboBox(departments);
        h2.add(cbDept);
        cbDept.setSelectedItem(listProgress.list[updateIndex].getDepartment());


        lblPos= new JLabel("Position: ");
        h2.add(lblPos);
        cbPos= new JComboBox(positions);
        h2.add(cbPos);
        cbPos.setSelectedItem(listProgress.list[updateIndex].getPosition());


        lblSal= new JLabel("Salary: ");
        h2.add(lblSal);
        tfSal=new JFormattedTextField();
        tfSal.setText(listProgress.list[updateIndex].getSalary()+"");
        h2.add(tfSal);
        proPanel.add(h2);
        updateDialog.add(proPanel);

        hVert.add(personalPanel);
        hVert.add(proPanel);
        Box lowerBox= Box.createVerticalBox();
        lowerPanel= new JPanel();

        globalUpdateIndex=updateIndex;
        Box btnBox= Box.createVerticalBox();
        updateInfo= new JButton("Update Info");
        updateInfo.addActionListener(this);
        btnBox.add(updateInfo);
        lowerBox.add(btnBox);
        lowerPanel.add(lowerBox);
        hVert.add(lowerPanel);
        updateDialog.add(hVert);



        updateDialog.setSize(300, 500);
        updateDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        updateDialog.setVisible(true);
        updateDialog.setAlwaysOnTop(true);
        updateDialog.setFocusableWindowState(true);
        //this.setEnabled(false);
        updateDialog.setLocationRelativeTo(this);
    }

    public void actionPerformed(ActionEvent e)
    {
        /**
         * this if statement to run the program with his click 'Create NewList' or 'Import Existing'
         */
        if(e.getSource()==createNew)
        {
            new EmployeeRecordsManager("");
        }
        else if(e.getSource()==importExisting) // if import button is pressed.
        {
            /**
             * @param filter to add extension filter for filechooser.
             * 'fileChooser.addChoosableFileFilter(filter)' to add filer to filechooser.
             * @param status to save the value of selection
             * the if statement (status== fileChooser.APPROVE_OPTION) it to open filechooser dialog when import button is pressed.
             */
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt"); //extension filter for filechooser.
            fileChooser.addChoosableFileFilter(filter);//add filer to filechooser.
            int status= fileChooser.showOpenDialog(this);//save value of selection.
            if(status== fileChooser.APPROVE_OPTION)// open filechooser dialog when import button is pressed.
            {
                opened= fileChooser.getSelectedFile();
                new EmployeeRecordsManager(opened);
            }
        }

        /**
         * when the user press add Employee this statement will be true and the employee will add it
         */
        else if(e.getSource()==addEmp) {

            this.BDate.year = Integer.parseInt(this.BDate.years[this.years.getSelectedIndex()]);
            this.BDate.day = Integer.parseInt(this.BDate.days[this.days.getSelectedIndex()]);
            this.BDate.month = Integer.parseInt(this.BDate.months[this.months.getSelectedIndex()]);

            selectedDate = new GregorianCalendar(this.BDate.year,this.BDate.month,this.BDate.day);
            /**
             * this if statement to check no text field is empty
             */
            if (tfID.getText().equals("") ||
                    tfFname.getText().equals("") ||
                    tfLname.getText().equals("") ||
                    tfSal.getText().equals(""))
                JOptionPane.showMessageDialog(this, "You must fill in all required fields!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else
            {
                boolean found;
                String ge;
                long temp;
                boolean T1=false,T2=false;


                if(!tfID.getText().isEmpty()){
                    char tid[]= tfID.getText().toCharArray();
                    for(int i=0;i<tfID.getText().length();i++)
                    {
                        if(!Character.isDigit(tid[i]))
                        {
                            T1=true;
                            break;
                        }
                    }
                    System.out.println("ID "+!T1);
                }

                if(!tfSal.getText().isEmpty()){
                    char sa[]= tfSal.getText().toCharArray();
                    for(int i=0;i<tfSal.getText().length();i++)
                    {
                        if(!Character.isDigit(sa[i]) && sa[i]!='.')
                        {
                            T2=true;
                            break;
                        }
                    }
                    System.out.println("Sal "+!T2);
                }


                if (cbGender.getSelectedItem().toString().equals("Male"))
                    ge = "Male";
                else
                    ge = "Female";



                if(!T1&&!T2) {
                    temp = Long.parseLong(tfID.getText());
                    found = listProgress.searchByEmpId(temp);
                    float money = Float.parseFloat(tfSal.getText());

                    if (found)
                        JOptionPane.showMessageDialog(this, "There is already a list member with the same ID!",
                                "ERROR", JOptionPane.ERROR_MESSAGE);
                    else {
                        Employee New = null; //To New it in try
                        try {
                            New = new Employee(Long.parseLong(tfID.getText()), tfFname.getText(), tfLname.getText(), ge,
                                    selectedDate, cbDept.getSelectedItem().toString(),
                                    money, cbPos.getSelectedItem().toString());
                            listProgress.addEmployeeEnd(New);

                            String NDate=Integer.toString(New.getBirthDate().get(Calendar.DAY_OF_MONTH))
                                    + "/" + Integer.toString(New.getBirthDate().get(Calendar.MONTH))
                                    + "/" + Integer.toString(New.getBirthDate().get(Calendar.YEAR));

                            String E = String.format("%-25s %-25s %-25s %-25s %-25s %-25s %-25s %-25s",
                                    tfID.getText(), tfFname.getText(), tfLname.getText(),
                                    cbGender.getSelectedItem(), NDate,
                                    cbDept.getSelectedItem(), cbPos.getSelectedItem(), tfSal.getText());
                            listModel.addElement(E);
                        } catch (InvalidIDException e1) {
                            // e1.printStackTrace();
                            JOptionPane.showMessageDialog(this, e1.getMsg(),
                                    "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                else {
                    if(T1)
                        JOptionPane.showMessageDialog(null, "The ID Should consist of integers");
                    if(T2)
                        JOptionPane.showMessageDialog(null, "The Salary Should be a float number");
                }}
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
        else if(e.getSource()==updateEmp)
        {
            if (listInCreation.isSelectionEmpty())
                JOptionPane.showMessageDialog(this, "You must select a record to update!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else if(listInCreation.getSelectedIndex()!=0) {
                updatePanel(listInCreation.getSelectedIndex()-1);
            }
            else
                JOptionPane.showMessageDialog(this, "This is not a record of the list!",
                        "ERROR", JOptionPane.WARNING_MESSAGE);
        }
        else if(e.getSource()==updateInfo)
        {
            if (tfID.getText().equals("") ||
                    tfFname.getText().equals("") ||
                    tfLname.getText().equals("") ||
                    tfSal.getText().equals(""))
                JOptionPane.showMessageDialog(this, "You must fill in all required fields!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else
            {
                String ge;
                long temp = Long.parseLong(tfID.getText());
                this.BDate.year = Integer.parseInt(this.BDate.years[this.years.getSelectedIndex()]);
                this.BDate.day = Integer.parseInt(this.BDate.days[this.days.getSelectedIndex()]);
                this.BDate.month = Integer.parseInt(this.BDate.months[this.months.getSelectedIndex()]);

                if (cbGender.getSelectedItem().toString().equals("Male"))
                    ge = "Male";
                else
                    ge = "Female";
                float money = Float.parseFloat(tfSal.getText());
                selectedDate = new GregorianCalendar(BDate.year,BDate.month,BDate.day);
                listProgress.updateRecord(temp, tfFname.getText(), tfLname.getText(), ge,
                        selectedDate, cbDept.getSelectedItem().toString(),
                        money, cbPos.getSelectedItem().toString());


                String NDate= selectedDate.get(Calendar.DAY_OF_MONTH)
                        + "/" + selectedDate.get(Calendar.MONTH)
                        + "/" + selectedDate.get(Calendar.YEAR);


                String E = String.format("%-25s %-25s %-25s %-25s %-25s %-25s %-25s %-25s",
                        tfID.getText(), tfFname.getText(), tfLname.getText(),
                        cbGender.getSelectedItem(), NDate,
                        cbDept.getSelectedItem(), cbPos.getSelectedItem(), tfSal.getText());
                listModel.setElementAt(E, globalUpdateIndex+1);
                updateDialog.dispose();
                updateDialog=null;
                //this.setEnabled(true);

            }
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
                            String NDate=Integer.toString(listProgress.list[i].getBirthDate().get(Calendar.DAY_OF_MONTH))
                                    + "/" + Integer.toString(listProgress.list[i].getBirthDate().get(Calendar.MONTH))
                                    + "/" + Integer.toString(listProgress.list[i].getBirthDate().get(Calendar.YEAR));

                            writer.format("%-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s\n",
                                    listProgress.list[i].getEmpID(), listProgress.list[i].getFirstName(), listProgress.list[i].getLastName(),
                                    listProgress.list[i].getGender(), NDate,
                                    listProgress.list[i].getDepartment(), listProgress.list[i].getPosition(), listProgress.list[i].getSalary());
                        }
                        writer.close();
                    }
                }catch(FileNotFoundException e1){
                    e1.printStackTrace();
                }


            }
        }
        else if(e.getSource()==countRows) {
            //do this now.
            JOptionPane.showMessageDialog(this, "There are "+listProgress.size()+" records in the list!",
                    "Record Count", JOptionPane.INFORMATION_MESSAGE);

        }

    }

    public static void main(String[] args)
    { new EmployeeRecordsManager(); }
}
