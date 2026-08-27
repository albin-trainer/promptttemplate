package com.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "emp_skill_map")
public class EmpSkillMap {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private int rating;
    
    public EmpSkillMap() {
    }

    public EmpSkillMap(Long id, Employee employee, Skill skill) {
        this.id = id;
        this.employee = employee;
        this.skill = skill;
    }

    public Long getId() {
        return id;
    }

    public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}