package pojo.post.login;

import pojo.post.register.UnsuccessfulReg;

public class UnsuccessfulLogin extends UnsuccessfulReg {

    public UnsuccessfulLogin(){

    }

    public UnsuccessfulLogin(String error){
        super(error);
    }
}
