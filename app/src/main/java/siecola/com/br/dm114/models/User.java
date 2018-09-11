package siecola.com.br.dm114.models;

import java.io.Serializable;

public class User implements Serializable {

    // Atributos do usuario do provedor de vendas
    private long id;
    private String email;
    private String password;
    private String gcmRegId;
    private String lastLogin;
    private String lastGCMRegister;
    private String role;
    private boolean enabled;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGcmRegId() {
        return gcmRegId;
    }

    public void setGcmRegId(String gcmRegId) {
        this.gcmRegId = gcmRegId;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastGCMRegister() {
        return lastGCMRegister;
    }

    public void setLastGCMRegister(String lastGCMRegister) {
        this.lastGCMRegister = lastGCMRegister;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /*
        "id": 5730192894984192,
        "email": "tbsouza@outlook.com",
        "password": "thiagosouza",
        "gcmRegId": null,
        "lastLogin": null,
        "lastGCMRegister": null,
        "role": "USER",
        "enabled": true
     */
}
