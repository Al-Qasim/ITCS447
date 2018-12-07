public class InvalidIDException extends Exception
{
    private long cpr;
    public long getEmpID(){return cpr;}
    public InvalidIDException(long id)
    {
        this("Invalid Employee ID format!",id);
    }
    public InvalidIDException(String msg, long id)
    {
        super(msg);
        cpr=id;
    }
    public String toString()
    {
        return ("Employee ID: "+cpr);
    }
}
