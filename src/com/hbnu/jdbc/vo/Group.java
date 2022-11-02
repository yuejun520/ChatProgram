package com.hbnu.jdbc.vo;

/**
 * @author luanhao
 * @date 2022年11月01日 21:39
 */
public class Group {
    private int id;
    private String name;
    private byte[] headPortrait;
    private String introduction;
    private String createDate;

    public Group() {
    }

    public Group(int id, String name, byte[] headPortrait, String introduction, String createDate) {
        this.id = id;
        this.name = name;
        this.headPortrait = headPortrait;
        this.introduction = introduction;
        this.createDate = createDate;
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

    public byte[] getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(byte[] headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", headPortrait=" + headPortrait +
                ", introduction='" + introduction + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
