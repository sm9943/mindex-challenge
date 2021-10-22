package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Shubhang Mehrotra
 * @date-created 10/20/2021
 * @project Mindex Java Coding Challenge
 **/

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService{

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);
    @Autowired
    private EmployeeService employeeService;
    private int count = 0;

    @Override
    public ReportingStructure read(String employeeId) {
        LOG.debug("Reading reportingStructure with employeeId: [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);
        int totalReports = getTotalReports(employeeId, count);
        return new ReportingStructure(employee, totalReports);
    }

    /**
     * method to get the total number of reporters to a given employee.
     * Method uses tail-recursion to go over the list of direct reporters and count them
     * in each iteration.
     **/
    private int getTotalReports(String employeeId, int count){
        LOG.debug("counting reporters for employeeId: [{}]", employeeId);

        Employee employee = employeeService.read(employeeId);
        if(employee == null){
            throw new RuntimeException("Employee not found! Who ARE you?");
        }

        List<Employee> directReports =  employee.getDirectReports();
        if (directReports != null) {
            for (Employee empl : directReports) {
                    count += employee.getDirectReports().size();
                    getTotalReports(empl.getEmployeeId(), count);
            }
        }
        return count;
    }
}
