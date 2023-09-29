public class Date {
    private int year;
    private int month;
    private int day;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public boolean after(Date that) {
        if (this.year > that.year) return true;
        else if (this.year == that.year) {
            if (this.month > that.month) return true;
            else if (this.month == that.month) {
                return this.day > that.day;
            } else return false;
        } else return false;
    }
}
