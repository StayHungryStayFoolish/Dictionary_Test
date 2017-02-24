package model;

/**
 * Created by mingfei.net@gmail.com
 * 2/24/17 16:42
 */
public class Pos {
    private Integer id;
    private String pos;
    private int wordId;

    public Pos() {
    }

    public Pos(Integer id, String pos, int wordId) {

        this.id = id;
        this.pos = pos;
        this.wordId = wordId;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }
}
