Feature: API workflow
  Background:
    Given a JWT bearer token is created
@api
  Scenario: create the employee from framework with rest assured

    Given a request is prepared to create an employee using api call
    When a POST call is made to create the employee
    Then the status code for this request should be 201
  And the employee created contains key "Message" and value "Employee Created"
  And the employee id "Employee.employee_id" is stored as global variable
@api
  Scenario: Get the created employee
    Given a request is prepared to get the created employee
    When a GET call is made to retrieve the employee
    Then the status code for this get request is 200
    And the employee has ID "employee.employee_id" must match with global emp id
    And the data coming from "employee" object should match with the data used in post call
    |emp_firstname|emp_lastname|emp_middle_name|emp_gender|emp_birthday|emp_status|emp_job_title|
    |Sohel       |Moazzam     |Asghar             |Male      |2004-03-03  |permanent |QA Developer  |


  @json
  Scenario: Create employee using json payload
    Given a request is prepared to create an employee using json payload
    When a POST call is made to create the employee
    Then the status code for this request should be 201
    And the employee created contains key "Message" and value "Employee Created"
    And the employee id "Employee.employee_id" is stored as global variable

  @dynamic
  Scenario: Create employee using more dynamic json payload
    Given a request is prepared using data "Sohel" , "Moazzam" , "Asghar" , "Male" , "2004-03-03" , "permanent" , "QA Developer"
    When a POST call is made to create the employee
    Then the status code for this request should be 201
    And the employee created contains key "Message" and value "Employee Created"
    And the employee id "Employee.employee_id" is stored as global variable
