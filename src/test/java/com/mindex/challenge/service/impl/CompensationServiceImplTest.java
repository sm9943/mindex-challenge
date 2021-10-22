package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
    private String createCompensationUrl;
    private String readCompensationUrl;

    @Autowired
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        createCompensationUrl = "http://localhost:" + port + "/compensation";
        readCompensationUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @Test
    public void testCreateReadUpdate() {
        //initialize test object
        Compensation testCompensation = new Compensation();
        testCompensation.setEmployee(employeeRepository.findByEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f"));
        testCompensation.setSalary("1850 USD");
        testCompensation.setEffectiveDate(new Date(1634881578));

        //create checks
        ResponseEntity<Compensation> createdCompensationResponse = restTemplate.postForEntity(createCompensationUrl, testCompensation, Compensation.class);
        assertEquals(HttpStatus.OK, createdCompensationResponse.getStatusCode());

        Compensation createdCompensation = createdCompensationResponse.getBody();
        assertNotNull(createdCompensation);
        assertEquals(testCompensation.getEmployee().getEmployeeId(), createdCompensation.getEmployee().getEmployeeId()); //compare employee id
        assertEquals(testCompensation.getSalary(), createdCompensation.getSalary());                                     //compare salary
        assertEquals(testCompensation.getEffectiveDate(), testCompensation.getEffectiveDate());                          //compare effective date

        //read checks
        ResponseEntity<Compensation> readCompensationResponse = restTemplate.getForEntity(readCompensationUrl, Compensation.class, createdCompensation.getEmployee().getEmployeeId());
        assertEquals(HttpStatus.OK, readCompensationResponse.getStatusCode());

        Compensation readCompensation = readCompensationResponse.getBody();
        assertNotNull(readCompensation);
        assertEquals(readCompensation.getEmployee().getEmployeeId(), createdCompensation.getEmployee().getEmployeeId()); //compare employee id
        assertEquals(readCompensation.getSalary(), createdCompensation.getSalary());                                     //compare salary
        assertEquals(readCompensation.getEffectiveDate(), testCompensation.getEffectiveDate());                          //compare effective date
    }

    @After
    public void teardown() {
        createCompensationUrl = null;
        readCompensationUrl = null;
    }
}
