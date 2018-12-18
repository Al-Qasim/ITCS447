public class Date {
    private static int []months=new int[12];
    private static int []days=new int[31];
    private static int []years=new int[65];
    private int month;
    private int day;
    private int year;

    Date()
    {
        for(int i=0;i<12;i++)
            months[i]=i+1;

        for(int i=0;i<31;i++)
            days[i]=i+1;

        for(int i=0;i<65;i++)
            years[i]=2018-i;

        this.month=0;
        this.day=0;
        this.year=0;
    }

    public void set(int y, int m, int d) {
        this.year = y;
        this.month = m;
        this.day = d;
    }

    public String get() {
        return(this.day+"/"+this.month+"/"+this.year);
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
