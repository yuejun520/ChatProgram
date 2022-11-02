package com.hbnu.jdbc.vo;

import java.util.Arrays;

/**
 * @author luanhao
 * @date 2022年11月01日 21:17
 */
public class User {
    private int id;
    private String name;
    private String password;
    private String introduction;
    private String securityQuestion;
    private String classifiedAnswer;
    private byte[] headPortrait;

    public User() {
    }

    public User(int id, String name, String password, String introduction, String securityQuestion, String classifiedAnswer, byte[] headPortrait) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.introduction = introduction;
        this.securityQuestion = securityQuestion;
        this.classifiedAnswer = classifiedAnswer;
        this.headPortrait = headPortrait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getClassifiedAnswer() {
        return classifiedAnswer;
    }

    public void setClassifiedAnswer(String classifiedAnswer) {
        this.classifiedAnswer = classifiedAnswer;
    }

    public byte[] getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(byte[] headPortrait) {
        this.headPortrait = headPortrait;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", introduction='" + introduction + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", classifiedAnswer='" + classifiedAnswer + '\'' +
                ", headPortrait=" + Arrays.toString(headPortrait) +
                '}';
    }
}
