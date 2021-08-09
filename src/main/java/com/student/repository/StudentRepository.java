package com.student.repository;

import java.util.List;
import com.student.entity.Student;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student,Long>{

    // find students by department and sort them
    public List<Student> findAllByDepartment(String department, Sort sort);
    
}
