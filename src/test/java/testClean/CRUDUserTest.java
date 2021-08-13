package testClean;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInformation;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import utils.ConfigAPI;
import utils.ConfigEnv;
import utils.GetProperties;
import utils.GetToken;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CRUDUserTest {
    String fullName = "Grupo5";

    @BeforeEach
    public void before() throws IOException {
        new GetToken().token();
        new GetProperties().leerPropiedades();

    }

    @Test
    @Order(1)
    public void verifyCreateUser(){

        JSONObject body = new JSONObject();
        body.put("FullName",fullName);
        body.put("Email",ConfigEnv.user);
        body.put("Password",ConfigEnv.password);

        RequestInformation request = new RequestInformation(ConfigAPI.CREATE_USER,body.toString());
        Response response = FactoryRequest.make(FactoryRequest.POST).send(request);
        response.then()
                .statusCode(200)
                .body("FullName", equalTo(fullName));

    }

    @Test
    @Order(2)
    public void verifyUpdateUser(){
        RequestInformation request = new RequestInformation(ConfigAPI.READ_USER.replace("USER","user"),"");
        Response  response = FactoryRequest.make(FactoryRequest.GET).send(request);

        String id = response.then().extract().path("Id")+"";

        JSONObject body = new JSONObject();
        body.put("FullName","UPDATEGrupo4");

        request = new RequestInformation(ConfigAPI.UPDATE_USER.replace("ID",id),body.toString());
        response = FactoryRequest.make(FactoryRequest.PUT).send(request);

        response.then()
                .statusCode(200)
                .body("FullName", equalTo("UPDATEGrupo4"));

    }

    @Test
    @Order(3)
    public void verifyGetUser(){
        RequestInformation request = new RequestInformation(ConfigAPI.READ_USER.replace("USER","user"),"");
        Response  response = FactoryRequest.make(FactoryRequest.GET).send(request);
        response.then()
                .statusCode(200)
                .body("Email", equalTo(ConfigEnv.user));

    }


}