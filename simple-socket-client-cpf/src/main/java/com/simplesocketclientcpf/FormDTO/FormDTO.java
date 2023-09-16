package com.simplesocketclientcpf.FormDTO;

public class FormDTO {
    
    String name;
    int age;
    String email;
    String cpf;

    public FormDTO(String name, int age, String email, String cpf) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.cpf = cpf;
    }

    public FormDTO() {
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return name + "," + age + "," + email + "," + cpf;
    }   
}
