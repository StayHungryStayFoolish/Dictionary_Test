package model;

/**
 * Created by mingfei.net@gmail.com
 * 2/27/17 15:39
 */
public class Concise {
    private Integer id;
    private String chinese;
    private int posId;

    public Concise() {
    }

    public Concise(Integer id, String chinese, int posId) {

        this.id = id;
        this.chinese = chinese;
        this.posId = posId;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    @Override
    public String toString() {
        return "Concise{" +
                "id=" + id +
                ", chinese='" + chinese + '\'' +
                ", posId=" + posId +
                '}';
    }
}
