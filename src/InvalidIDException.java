import javax.swing.*;

public class InvalidIDException extends Exception
{
    private long cpr;
    private String msg;
    public long getEmpID(){return cpr;}
    public InvalidIDException(long id) { this("The ID must consist of 9 digits ",id);  }
    public InvalidIDException(String msg, long id)
    {
        super(msg);
        this.msg=msg;
        cpr=id;
    }
    public String toString()
    {
        return ("Employee ID: "+cpr);
    }
    public String getMsg()
    {
        return (this.msg);
    }
}
