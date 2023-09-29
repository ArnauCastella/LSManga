public class Serie {
    private Title title;
    private int popularity;
    private int averageScore;
    private int favourites;
    private String[] genres;
    private Date startDate;
    private float combinedPriorities;

    public int getAverageScore() {
        return averageScore;
    }

    public int getPopularity() {
        return popularity;
    }

    public int getFavourites() {
        return favourites;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String getStartDateString() {
        return (startDate.getDay() + "-" + startDate.getMonth() + "-" + startDate.getYear());
    }

    public void setCombinedPriorities(float combinedPriorities) {
        this.combinedPriorities = combinedPriorities;
    }

    public Title getTitle() {
        return title;
    }

    public float getCombinedPriorities() {
        return combinedPriorities;
    }
}
