public class FDate {
    private static String []months=new String[12];
    private static String []days=new String[31];
    private static String []years=new String[65];
    private String month;
    private String day;
    private String year;

    FDate()
    {
        for(int i=0;i<12;i++)
            months[i]=(i+1)+"";

        for(int i=0;i<31;i++)
            days[i]=(i+1)+"";

        for(int i=0;i<65;i++) {
            int X = 65;
            years[i] = (2018 - (X - i)) + "";
        }

        this.month="0";
        this.day="0";
        this.year="0";
    }

    public void set(String y, String m, String d) {
        this.year = y;
        this.month = m;
        this.day = d;
    }

    public String get() {
        return(this.day+"/"+this.month+"/"+this.year);
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String[] getDays() {
        return days;
    }

    public String[] getMonths() {
        return months;
    }

    public String[] getYears() {
        return years;
    }
}
