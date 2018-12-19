public class FDate {
    public String []months=new String[12];
    public String []days=new String[31];
    public String []years=new String[65];
    public int month;
    public int day;
    public int year;

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
