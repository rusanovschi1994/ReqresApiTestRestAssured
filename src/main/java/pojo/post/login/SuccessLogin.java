package pojo.post.login;

public class SuccessLogin {

    private String token;

    public SuccessLogin(){

    }

    public SuccessLogin(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
