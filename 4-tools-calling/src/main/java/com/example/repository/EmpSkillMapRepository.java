package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.EmpSkillMap;
import com.example.entity.Skill;

public interface EmpSkillMapRepository extends JpaRepository<EmpSkillMap, Long>  {
    List<EmpSkillMap> findBySkillName(String name);

}
