package entity.dto;

import entity.Member;

public class MemberDTO {
    private Long id;
    private String name;
    private Long studentId;
    private String colorLevel;

    public MemberDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.studentId = member.getStudentId();
        this.colorLevel = member.getLevelColor();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getColorLevel() {
        return colorLevel;
    }

    public void setColorLevel(String colorLevel) {
        this.colorLevel = colorLevel;
    }
}
