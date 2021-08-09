package com.student.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.student.entity.Student;
import com.student.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;
	
	// for fetching one student
	@GetMapping("/student/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id){
		Optional<Student> student = studentRepository.findById(id);
		return ResponseEntity.ok(student);
	}
	// for fetching all students
	@GetMapping("/student")
	public ResponseEntity<?> getAllStudents(){
		List<Student> students = studentRepository.findAll();
		return ResponseEntity.ok(students);
	}

	// for adding student
	@PostMapping("/student")
	public ResponseEntity<?> addStudent(@RequestBody Student s){
		Student student = studentRepository.save(s);
		return ResponseEntity.ok(student);
	}

	// for updating student
	@PutMapping("/student/{id}")
	public ResponseEntity<?> updateStudentById(@PathVariable("id") Long id, @RequestBody Student studentData) throws Exception{
		 /*
		 checking whether the student exists or not
		if yes then return student object
		if no then throw exception
		*/
		Student student = studentRepository.findById(id).orElseThrow(() -> new Exception("Student not found"));
		
		// updating the student details
		student.setFirstName(studentData.getFirstName());
		student.setLastName(studentData.getLastName());
		student.setDepartment(studentData.getDepartment());
		student.setPercentage(studentData.getPercentage());
		
		// saving the student data
		Student updateStudent = studentRepository.save(student);
		
		return ResponseEntity.ok(updateStudent);
	}

	// deleting student
	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteStudentById(@PathVariable("id") Long id) throws Exception{
		Student student = studentRepository.findById(id).orElseThrow(() -> new Exception("Student not found"));
		studentRepository.deleteById(id);
		Map<String,Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}	

	// sort students in descending order of percentage
	@GetMapping("/student-high-percent")
	public ResponseEntity<?> getAllStudentsByPercentageDesc(){
		List<Student> students = studentRepository.findAll(Sort.by(org.springframework.data.domain.Sort.Direction.DESC,"percentage"));
		return ResponseEntity.ok(students);
	}

	// sort students in ascending order of percentage
	@GetMapping("/student-low-percent")
	public ResponseEntity<?> getAllStudentsByPercentageAsc(){
		List<Student> students = studentRepository.findAll(Sort.by(org.springframework.data.domain.Sort.Direction.ASC,"percentage"));
		return ResponseEntity.ok(students);
	}

	// sort students in descending order of percentage
	@GetMapping("/student-high-percent/{department}")
	public ResponseEntity<?> getAllStudentsByPercentageByDeptDesc(@PathVariable("department") String department){
		List<Student> students = studentRepository.findAllByDepartment(department,Sort.by(Sort.Direction.DESC,"percentage"));
		return ResponseEntity.ok(students);
	}

	// sort students in descending order of percentage
	@GetMapping("/student-low-percent/{department}")
	public ResponseEntity<?> getAllStudentsByPercentageByDeptAsc(@PathVariable("department") String department){
		List<Student> students = studentRepository.findAllByDepartment(department,Sort.by(Sort.Direction.ASC,"percentage"));
		return ResponseEntity.ok(students);
	}
}
