package seftutlis.execlutil.bean;

/**
 * @ClassName Titles
 * @Description TODO
 * @Author liubo
 * @Date 2021/1/5 14:54
 */
public class Titles {
    private String id;
    private String name;
    private String sex;
    private String phone;

    public Titles() {
    }

    public Titles(String id, String name, String sex, String phone) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
