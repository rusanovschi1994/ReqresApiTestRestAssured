package pojo.post.register;

public class UnsuccessfulReg {

    private String error;

    public UnsuccessfulReg() {

    }

    public UnsuccessfulReg(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
