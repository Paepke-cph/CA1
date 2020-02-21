package entity;
/*
 * author benjaminp
 * version 1.0
 */

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name = "Member")
@NamedNativeQuery(name = "Member.Truncate", query = "TRUNCATE TABLE Member")
@NamedQueries({
    @NamedQuery(name = "Member.deleteAllRows", query = "DELETE FROM Member"),
    @NamedQuery(name = "Member.findByName", query = "SELECT m FROM Member m WHERE m.name LIKE :name")
})
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String studentId;
    private String levelColor;

    public Member() {
    }
    public Member(String name, String studentId, String levelColor) {
        this.name = name;
        this.studentId = studentId;
        this.levelColor = levelColor;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getLevelColor() { return levelColor; }
    public void setLevelColor(String levelColor) { this.levelColor = levelColor; }
}
