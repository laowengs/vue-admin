package com.laowengs.vuedashboard.vo;

public class LoginOutput {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginOutput{" +
                "token='" + token + '\'' +
                '}';
    }

    public static LoginOutput getInstance(String token){
        LoginOutput loginOutput = new LoginOutput();
        loginOutput.setToken(token);
        return loginOutput;
    }
}
