package pojo.get;

public class UserTimeResponse extends UserTimeData{

    private String updatedAt;

    public UserTimeResponse(){

    }

    public UserTimeResponse(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
