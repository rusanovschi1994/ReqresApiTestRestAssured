package pojo.post.register;

public class SuccessReg {

    private Integer id;
    private String token;

    public SuccessReg() {
    }

    public SuccessReg(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
