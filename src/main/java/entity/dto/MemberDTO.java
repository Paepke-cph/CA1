package entity.dto;

import entity.Member;

public class MemberDTO {
    private Long id;
    private String name;
    private String studentId;
    private String colorLevel;

    public MemberDTO(Member member) {
        if(member != null) {
            this.id = member.getId();
            this.name = member.getName();
            this.studentId = member.getStudentId();
            this.colorLevel = member.getLevelColor();
        }
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getColorLevel() {
        return colorLevel;
    }

    public void setColorLevel(String colorLevel) {
        this.colorLevel = colorLevel;
    }
}
