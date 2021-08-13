package testClean;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInformation;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.ConfigAPI;
import utils.GetProperties;
import utils.GetToken;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;


public class CRUDProjectTest {

    @BeforeEach
    public void before() throws IOException {
        new GetToken().token();
    }

    @Test
    public void verifyCreateProject(){
        JSONObject body = new JSONObject();
        body.put("Content","Project_Grupo4");
        body.put("Icon",4);
        System.out.println("***********************CREATE PROJECT***************************");
        RequestInformation request = new RequestInformation(ConfigAPI.CREATE_PROJECT,body.toString());
        Response response = FactoryRequest.make(FactoryRequest.POST).send(request);

        response.then()
                .statusCode(200)
                .body("Content", equalTo("Project_Grupo4"));
     }

    @Test
    public void verifyUpdateProject(){
        JSONObject body = new JSONObject();
        body.put("Content","Project_Grupo");
        body.put("Icon",4);

        RequestInformation request = new RequestInformation(ConfigAPI.CREATE_PROJECT,body.toString());
        Response response = FactoryRequest.make(FactoryRequest.POST).send(request);
        response.then()
                .statusCode(200)
                .body("Content", equalTo("Project_Grupo"));
        String id = response.then().extract().path("Id")+"";

        body.put("Content","UpdateProject");
              System.out.println("***********************UPDATE PROJECT****************************");
        request = new RequestInformation(ConfigAPI.UPDATE_PROJECT.replace("ID",id),body.toString());
        response = FactoryRequest.make(FactoryRequest.PUT).send(request);

        response.then()
                .statusCode(200)
                .body("Content", equalTo("UpdateProject"));

    }

    @Test
    public void verifyGetProject(){
        JSONObject body = new JSONObject();
        body.put("Content","GetProject");
        body.put("Collapsed",true);

        RequestInformation request = new RequestInformation(ConfigAPI.CREATE_PROJECT,body.toString());
        Response response = FactoryRequest.make(FactoryRequest.POST).send(request);
        response.then()
                .statusCode(200)
                .body("Content", equalTo("GetProject"));
        String id = response.then().extract().path("Id")+"";


        System.out.println("*************************GET PROJECT******************************");
        request = new RequestInformation(ConfigAPI.READ_PROJECT.replace("ID",id),"");
        response = FactoryRequest.make(FactoryRequest.GET).send(request);

        response.then()
                .statusCode(200)
                .body("Content", equalTo("GetProject"))
                .body("Collapsed", equalTo(true));
    }

    @Test
    public void verifyDeleteProject(){
        JSONObject body = new JSONObject();
        body.put("Content","DELETEProject");

        RequestInformation request = new RequestInformation(ConfigAPI.CREATE_PROJECT,body.toString());
        Response response = FactoryRequest.make(FactoryRequest.POST).send(request);
        response.then()
                .statusCode(200)
                .body("Content", equalTo("DELETEProject"));
        String id = response.then().extract().path("Id")+"";

        System.out.println("***********************DELETE PROJECT*****************************");
        request = new RequestInformation(ConfigAPI.DELETE_PROJECT.replace("ID",id),"");
        response = FactoryRequest.make(FactoryRequest.DELETE).send(request);

        response.then()
                .statusCode(200)
                .body("Deleted", equalTo(true));

    }

}