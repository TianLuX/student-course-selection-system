package entity;

public class Sc {

    private Integer scId;
    private Integer stuId;
    private Integer cId;
    private Double score;
    private String cName;

    public String getcTime() {
        return cTime;
    }
    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    private String cTime;

    public Integer getScId() {
        return scId;
    }
    public void setScId(Integer scId) {
        this.scId = scId;
    }
    public Integer getStuId() {
        return stuId;
    }
    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }
    public Integer getcId() {
        return cId;
    }
    public void setcId(Integer cId) {
        this.cId = cId;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }
    public String getcName() {
        return cName;
    }
    public void setcName(String cName) {
        this.cName = cName;
    }
}
