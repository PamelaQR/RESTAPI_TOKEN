package utils;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInformation;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.CoreMatchers.equalTo;

public class GetToken {
    public void token() throws IOException {
        RequestInformation request = new RequestInformation(ConfigAPI.GET_TOKEN,"");
        Response response = FactoryRequest.make(FactoryRequest.GET_BASIC).send(request);
        ConfigEnv.token = response.then().extract().path("TokenString")+"";

        System.out.println("*****************************************");
        System.out.println("ConfigEnv.token: "+ConfigEnv.token);
        System.out.println("*****************************************");
    }
}
