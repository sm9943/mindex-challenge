package com.mindex.challenge.data;

import java.util.Date;
import java.util.Objects;

/**
 * @author Shubhang Mehrotra
 * @date-created 10/21/2021
 * @project Mindex Java Coding Challenge
 **/
public class Compensation {

    private Employee employee;
    private String salary;
    private Date effectiveDate;

    public Compensation() {
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Compensation that = (Compensation) o;
        return employee.equals(that.employee) && salary.equals(that.salary) && effectiveDate.equals(that.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, salary, effectiveDate);
    }

    @Override
    public String toString() {
        return "Compensation{" +
                "employee=" + employee +
                ", salary='" + salary + '\'' +
                ", effectiveDate=" + effectiveDate +
                '}';
    }
}
