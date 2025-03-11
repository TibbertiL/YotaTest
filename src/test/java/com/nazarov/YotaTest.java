package com.nazarov;

import com.nazarov.TestXML.CustomerIdXML;
import com.nazarov.TestXML.Envelope;
import com.nazarov.TestXML.FindCustomerBodyXML;
import com.nazarov.TestXML.FindCustomerHeadXML;
import com.nazarov.specification.Specification;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class YotaTest {

    private static final String URL = "http://localhost:8090/";
    private static String token = "0b646ed69189422597c98130f039d6a5";
    private static Long phone = 79282812634L;
    private static String id = "3bdc7ce1-9b06-4fc4-882c-9a0647657f2b";

    @Before
    public void setup(){

        // getAdminToken
        if (token == null) {
            Specification.installSpecification(Specification.requestSpec(URL),
                    Specification.responseSpecOK200());

            String login = "admin";

            UserData userData = new UserData(login, "password");

            Response response = given()
                    .body(userData)
                    .when()
                    .post("login")
                    .then().log().all()
                    .body("token", notNullValue())
                    .extract().response();

            JsonPath jsonPath = response.jsonPath();
            token = jsonPath.get("token");
        }

        // getAvailablePhoneNumbersTest
        if (phone == null) {
            Specification.installSpecification(Specification.requestSpec(URL),
                    Specification.responseSpecOK200());

            List<Phone> phones = given()
                    .header("authToken", token)
                    .when()
                    .get("simcards/getEmptyPhone")
                    .then().log().all()
                    .extract().body().jsonPath().getList("phones" ,Phone.class);

            Assert.assertTrue(!phones.isEmpty());
            phone = phones.get(0).getPhone();
        }

        // createCustomerTest
        if (id == null){
            Specification.installSpecification(Specification.requestSpec(URL),
                    Specification.responseSpecOK200());

            Map<String, String> additionalParameters = new HashMap<>();
            additionalParameters.put("string", "пусто");
            Customer newCustomer = new Customer("Pavel", phone, additionalParameters);

            Response response = given()
                    .header("authToken", token)
                    .body(newCustomer)
                    .log().all()
                    .when()
                    .post("/customer/postCustomer")
                    .then().log().all()
                    .body("id", notNullValue())
                    .extract().response();

            JsonPath jsonPath = response.jsonPath();
            id = jsonPath.get("id");
        }
    }

    //@Test
    public void getCustomerByIdTest(){
        Specification.installSpecification(Specification.requestSpec(URL),
                Specification.responseSpecOK200());

        Response response = given()
                .when()
                .header("authToken", token)
                .param("customerId", id)
                .log().all()
                .get("customer/getCustomerById")
                .then().log().body()
                .body("return.customerId", notNullValue())
                .body("return.name", notNullValue())
                .body("return.status", notNullValue())
                .body("return.phone", notNullValue())
                .body("return.pd", notNullValue())
                .extract().response();
    }

    @Test
    public void getCustomerByPhoneNumberTest() throws JAXBException {
        Specification.installSpecification(Specification.requestSpecXML(URL),
                Specification.responseSpecOK200());

        String requestBody = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<ns3:Envelope xmlns:ns2=\"soap\" xmlns:ns3=\"http://schemas.xmlsoap.org/soap/envelope\">\n" +
                "    <ns2:Header>\n" +
                "        <authToken>" + token + "</authToken>\n" +
                "    </ns2:Header>\n" +
                "    <ns2:Body>\n" +
                "        <phoneNumber>" + phone + "</phoneNumber>\n" +
                "    </ns2:Body>\n" +
                "</ns3:Envelope>";
        RestAssured.registerParser("text/plain", Parser.XML);
//        // Создаем объект Header и Body
//        FindCustomerHeadXML header = new FindCustomerHeadXML(token);
//        FindCustomerBodyXML body = new FindCustomerBodyXML(phone);
//
//        Envelope request = new Envelope(header, body);
//
//        JAXBContext context = JAXBContext.newInstance(Envelope.class);
//        Marshaller marshaller = context.createMarshaller();
//        StringWriter writer = new StringWriter();
//        marshaller.marshal(request, writer);
//
//        String xmlString = writer.toString();
//        System.out.println(xmlString);

        CustomerIdXML response = given()
                //.body(xmlString)
                .header("Content-Type", "application/xml; charset=UTF-8")
                .body(requestBody)
                .log().all()
                .when()
                .post("customer/findByPhoneNumber")
                .then().log().ifError()
                .body("customerId", notNullValue())
                .extract().as(CustomerIdXML.class);
    }

    //@Test
    public void changeStatusAdminTest(){
        Specification.installSpecification(Specification.requestSpec(URL),
                Specification.responseSpecOK200());

        String customStatus = "finally work";
        String customerId = id;
        HashMap<String, String> status = new HashMap<>();
        status.put("status", customStatus);

        given()
                .header("authToken", token)
                .body(status)
                .when()
                .post("customer/" + customerId + "/changeCustomerStatus")
                .then().log().all();
    }

    //@Test
    public void changeStatusUserTest(){
        Specification.installSpecification(Specification.requestSpec(URL),
                Specification.responseSpecErrorCustom(401));

        String customStatus = "finally work";
        String customerId = id;
        HashMap<String, String> status = new HashMap<>();
        status.put("status", customStatus);

        given()
                .header("authToken", token)
                .body(status)
                .when()
                .post("customer/" + customerId + "/changeCustomerStatus")
                .then().log().all();
    }
}
