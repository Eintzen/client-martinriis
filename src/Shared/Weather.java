package Shared;

public class Weather {

    private String date;
    private String celsius;
    private String desc;

    // Function which sets date, degrees and describes the forecast object
    public Weather(String date, String celsius, String desc) {
        this.date = date;
        this.celsius = celsius;
        this.desc = desc;
    }
    
    // Settere og gettere for the Forecast class
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCelsius() {
        return celsius;
    }

    public void setCelsius(String celsius) {
        this.celsius = celsius;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    
    // returns weatherdata as JSON string
    public String toString() {
        return "Forecast{" +
                "date='" + date + '\'' +
                ", celsius='" + celsius + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}

