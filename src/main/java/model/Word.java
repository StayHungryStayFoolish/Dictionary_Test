package model;

import java.util.List;

/**
 * Created by mingfei.net@gmail.com
 * 2/24/17 15:22
 */
public class Word {
    private Integer id;
    private String english;
    private String phoneticUk;
    private String phoneticUs;

    private List<Pos> poss;

    public Word() {
    }

    public Word(Integer id, String english, String phoneticUk, String phoneticUs) {

        this.id = id;
        this.english = english;
        this.phoneticUk = phoneticUk;
        this.phoneticUs = phoneticUs;
    }

    public Word(Integer id, String english, String phoneticUk, String phoneticUs, List<Pos> poss) {
        this.id = id;
        this.english = english;
        this.phoneticUk = phoneticUk;
        this.phoneticUs = phoneticUs;
        this.poss = poss;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getPhoneticUk() {
        return phoneticUk;
    }

    public void setPhoneticUk(String phoneticUk) {
        this.phoneticUk = phoneticUk;
    }

    public String getPhoneticUs() {
        return phoneticUs;
    }

    public void setPhoneticUs(String phoneticUs) {
        this.phoneticUs = phoneticUs;
    }

    public List<Pos> getPoss() {
        return poss;
    }

    public void setPoss(List<Pos> poss) {
        this.poss = poss;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", english='" + english + '\'' +
                ", phoneticUk='" + phoneticUk + '\'' +
                ", phoneticUs='" + phoneticUs + '\'' +
                ", poss=" + poss +
                '}';
    }
}
