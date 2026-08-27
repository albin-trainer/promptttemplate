package com.example.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.entity.EmpSkillMap;
import com.example.entity.Employee;
import com.example.entity.Skill;
import com.example.repository.EmpSkillMapRepository;
import com.example.repository.EmployeeRepository;
import com.example.repository.SkillRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final SkillRepository skillRepository;
private final EmpSkillMapRepository empSkillRepository;
    public EmployeeService(EmployeeRepository employeeRepository,
                           SkillRepository skillRepository,EmpSkillMapRepository empSkillRepository) {
        this.employeeRepository = employeeRepository;
        this.skillRepository = skillRepository;
        this.empSkillRepository=empSkillRepository;
    }

    public Employee addEmployee(Employee employee) {
       
        return employeeRepository.save(employee);
    }

    public Skill addSkill(Skill skill) {

        
        return  skillRepository.save(skill);
    }
    public EmpSkillMap assignSkillService(Long employeeId, long skillId, int rating) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Skill skill=skillRepository.findById(skillId). orElseThrow(() -> new RuntimeException("Skill not found"));
        EmpSkillMap empskill=new EmpSkillMap();
        empskill.setEmployee(employee);
        empskill.setSkill(skill);
        empskill.setRating(rating);
        return  empSkillRepository.save(empskill);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
    public List<Employee> getEmployeesBySkill(String skillName) {

        return empSkillRepository.findBySkillName(skillName)
                         .stream()
                         .map(map -> map.getEmployee())
                         .collect(Collectors.toList());
    }

}