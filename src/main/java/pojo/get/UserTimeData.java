package pojo.get;

public class UserTimeData {

    private String name;
    private String job;

    public UserTimeData(){

    }
    public UserTimeData(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
