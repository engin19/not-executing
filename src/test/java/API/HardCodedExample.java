package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HardCodedExample {
    String baseURI= RestAssured.baseURI="http://hrm.syntaxtechs.net/syntaxapi/api";
    static String employee_id;
String token="Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTA4ODgwNDUsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTcxMDkzMTI0NSwidXNlcklkIjoiNjQ5MCJ9.OKGpdVxdKKPsuDGvKg2_zcOI1K9M9qU391pVIXnu1xY";

@Test
    public void acreateEmployee(){
        RequestSpecification request=given().header("Content-Type","application/json").
                header("Authorization",token).
                body("{\n" +
                        "  \"emp_firstname\": \"Sohel\",\n" +
                        "  \"emp_lastname\": \"Moazzam\",\n" +
                        "  \"emp_middle_name\": \"Asghar\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2004-03-03\",\n" +
                        "  \"emp_status\": \"permanent\",\n" +
                        "  \"emp_job_title\": \"QA Developper\"\n" +
                        "}");
        // it will give us the response after hitting the endpoint
        Response response = request.when().post("/createEmployee.php");

        //assertThat  is the method we use to validate the response
        response.then().assertThat().statusCode(201);
        response.prettyPrint();
        // hamcrest matchers
    response.then().assertThat().body("Message",equalTo("Employee Created"));
    response.then().assertThat().body("Employee.emp_firstname",equalTo("Sohel"));
    response.then().assertThat().body("Employee.emp_lastname",equalTo("Moazzam"));
    response.then().assertThat().header("Connection",equalTo("Keep-Alive"));
// to fetch the employee id from response body we need response variable
    employee_id=response.jsonPath().getString("Employee.employee_id");
    }
    @Test
    public void bgetCreatedEmployee(){
    //prepare the request
RequestSpecification request = given().header("Content-Type","application/json").header("Authorization",token).
        queryParam("employee_id",employee_id);
// hitting the endpoint
        Response response = request.when().get("/getOneEmployee.php");
        //validate the response
        response.then().assertThat().statusCode(200);
        response.prettyPrint();
        String temporaryEmpId=response.jsonPath().getString("employee.employee_id");
        // here we are comparing both emp id's from get and post call
        Assert.assertEquals(temporaryEmpId,employee_id);
        // validate the body from get call
        response.then().assertThat().body("employee.emp_lastname",equalTo("Moazzam"));
        response.then().assertThat().body("employee.emp_firstname",equalTo("Sohel"));
    }
    @Test
public void cUpdateEmployee(){
    // prepare the request
        RequestSpecification request = given().header("Content-Type","application/json").header("Authorization",token).
                body("{\n" +
                        "  \"employee_id\": \""+employee_id+"\",\n" +
                        "  \"emp_firstname\": \"Moazzam\",\n" +
                        "  \"emp_lastname\": \"Asghar\",\n" +
                        "  \"emp_middle_name\": \"Sohel\",\n" +
                        "  \"emp_gender\": \"M\",\n" +
                        "  \"emp_birthday\": \"2004-10-10\",\n" +
                        "  \"emp_status\": \"doubtful\",\n" +
                        "  \"emp_job_title\": \"Teacher\"\n" +
                        "}\n");
        //hitting the enpoint
        Response response = request.when().put("/updateEmployee.php");
        // validation of response
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("Message",equalTo("Employee record Updated"));
        response.prettyPrint();

}
@Test
public void dGetUpdatedEmployee(){
    //prepare the request
    RequestSpecification request= given().header("Content-Type","application/json").header("Authorization",token).
            queryParam("employee_id",employee_id);
    Response response=request.when().get("/getOneEmployee.php");
    //validation
    response.prettyPrint();
    response.then().assertThat().statusCode(200);
}
@Test
public void eGetAllEmployees(){
    RequestSpecification request= given().header("Content-Type","application/json").header("Authorization",token);
    Response response= request.when().get("/getAllEmployees.php");
    response.prettyPrint();
    response.then().assertThat().statusCode(200);
}
@Test
public void fGetJobTitle(){
    RequestSpecification request= given().header("Content-Type","application/json").header("Authorization",token);
    Response response= request.when().get("/jobTitle.php");
    response.prettyPrint();
    response.then().assertThat().statusCode(200);
}
@Test
public void gUpdatePartialEmployeeDetails(){
    RequestSpecification request= given().header("Content-Type","application/json").header("Authorization",token).body("{\n" +
            "  \"employee_id\": \""+employee_id+"\",\n" +
            "    \"emp_middle_name\": \"ms\",\n" +
            "  \"emp_status\": \"confirmed\",\n" +
            "  \"emp_job_title\": \"Doctor\"\n" +
            "}");
    Response response = request.when().put("/updatePartialEmplyeesDetails.php");
    response.prettyPrint();
    response.then().assertThat().statusCode(201);
    response.then().assertThat().body("employee.emp_middle_name",equalTo("Sohel"));
    response.then().assertThat().body("employee.emp_job_title",equalTo("doctor"));
    response.then().assertThat().body("employee.emp_status",equalTo("doubtful"));
    response.prettyPrint();
}
}
