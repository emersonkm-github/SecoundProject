package com.example.secondProject.service;

import com.example.secondProject.entity.Department;
import com.example.secondProject.repository.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DepartmentServiceTest {

    @Autowired
    private DepartmentService departmentService;
    @MockBean
    private DepartmentRepository departmentRepository;

    @BeforeEach
    void setUp() {
        Department department = Department.builder()
                .departmentName("IT")
                .departmentAddress("HKNT")
                .departmentCode("IT-123")
                .departmentId(1L)
                .build();
        Mockito.when(departmentRepository.findByDepartmentNameIgnoreCase("IT"))
                .thenReturn(department);
    }
    @Test
    @DisplayName("Get Data Base On Valid Department Name")
    public void whenValidDepartment_thenDepartmentShouldFound(){
        String departmentName = "IT";
        Department found = departmentService.fetchDepartmentByName(departmentName);
        assertEquals(departmentName,found.getDepartmentName());
        System.out.println("found = " + found);
    }

    @Test
    @DisplayName("Test updating department by ID")
    void testUpdateDepartmentById() {
        Long departmentId = 1L;
        Department existingDepartment = Department.builder()
                .departmentName("IT")
                .departmentAddress("HKNT")
                .departmentCode("IT-123")
                .departmentId(departmentId)
                .build();
        Department updatedDepartment = Department.builder()
                .departmentName("IT")
                .departmentAddress("HK")
                .departmentCode("IT-456")
                .build();

        Mockito.when(departmentRepository.findById(departmentId))
                .thenReturn(java.util.Optional.of(existingDepartment));
        Mockito.when(departmentRepository.save(existingDepartment))
                .thenReturn(updatedDepartment);

        Department actualDepartment = departmentService.updateDepartmentById(departmentId, updatedDepartment);

        assertEquals(updatedDepartment.getDepartmentAddress(), actualDepartment.getDepartmentAddress());
        assertEquals(updatedDepartment.getDepartmentCode(), actualDepartment.getDepartmentCode());

    }
}
