package com.hbnu.jdbc.vo;

/**
 * @author luanhao
 * @date 2022年11月01日 21:42
 */
public class Friend {
    private int id;
    private int mId;
    private String remark;
    private int fId;
    private String addDate;

    public Friend(int id, int mId, String remark, int fId, String addDate) {
        this.id = id;
        this.mId = mId;
        this.remark = remark;
        this.fId = fId;
        this.addDate = addDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "id=" + id +
                ", mId=" + mId +
                ", remark='" + remark + '\'' +
                ", fId=" + fId +
                ", addDate='" + addDate + '\'' +
                '}';
    }
}
