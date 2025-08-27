package org.dandroid.jwtapp.vo;

public class TokenResponseVO {
    private String token;

    public TokenResponseVO(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
