package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    private String reportingStructureUrl;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";
    }

    @Test
    public void testRead() {
        Employee employeeOne = employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");

        //initialize test object
        ReportingStructure testReportingStructure = new ReportingStructure(employeeOne, 4);

        //read checks
        ResponseEntity<ReportingStructure> readResponse = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employeeOne.getEmployeeId());
        assertEquals(HttpStatus.OK, readResponse.getStatusCode());

        ReportingStructure readReportingStructure = readResponse.getBody();
        assertNotNull(readReportingStructure);
        assertEquals(readReportingStructure.getEmployee().getEmployeeId(), testReportingStructure.getEmployee().getEmployeeId()); //compare employee id
        assertEquals(readReportingStructure.getNumberOfReports(), testReportingStructure.getNumberOfReports());                   //compare number of reports
    }

    @After
    public void teardown() {
        reportingStructureUrl = null;
    }

}
