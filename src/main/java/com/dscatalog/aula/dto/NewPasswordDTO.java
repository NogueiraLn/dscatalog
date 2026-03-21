package com.dscatalog.aula.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

    @NotBlank(message = "Campo obrigatorio")
    private String token;

    @NotBlank(message = "Campo obrigatorio")
    @Size(min = 8, message = "Deve ter no minimo 8 caracteres")
    private String password;

    public NewPasswordDTO(){
    }

    public NewPasswordDTO(String password, String token) {
        this.password = password;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
