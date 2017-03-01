package model;

/**
 * Created by mingfei.net@gmail.com
 * 2/27/17 15:39
 */
public class Detail {
    private Integer id;
    private String detail;
    private int posId;

    public Detail() {
    }

    public Detail(Integer id, String detail, int posId) {
        this.id = id;
        this.detail = detail;
        this.posId = posId;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getPosId() {
        return posId;
    }

    public void setPosId(int posId) {
        this.posId = posId;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", detail='" + detail + '\'' +
                ", posId=" + posId +
                '}';
    }
}