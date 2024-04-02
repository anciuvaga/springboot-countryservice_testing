package com.countryservice;

import com.restservices.countryservice.beans.Country;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class ControllerIntegrationTests {

    @Test
    @Order(1)
    public void getAllCountries() throws JSONException {

        String expectedResponse = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"countryName\": \"Moldova\",\n" +
                "        \"countryCapital\": \"Ungheni\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"countryName\": \"USA\",\n" +
                "        \"countryCapital\": \"Washington\"\n" +
                "    }\n" +
                "]";

       TestRestTemplate restTemplate = new TestRestTemplate();
       ResponseEntity<String> response =  restTemplate.getForEntity("http://localhost:8080/getcountries", String.class);
       JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @Order(2)
    public void getCountryById() throws JSONException {

        String expectedResponse = "{\n" +
                "    \"id\": 1,\n" +
                "    \"countryName\": \"Moldova\",\n" +
                "    \"countryCapital\": \"Ungheni\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String > response=  restTemplate.getForEntity("http://localhost:8080/getcountries/1", String.class);

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @Order(3)
    public void getCountryByName() throws JSONException {
        String expectedResponse = "{\n" +
                "    \"id\": 2,\n" +
                "    \"countryName\": \"USA\",\n" +
                "    \"countryCapital\": \"Washington\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String>  response = restTemplate.getForEntity("http://localhost:8080/getcountries/countryname?name=USA", String.class);

        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @Order(4)

    public void addCountry() throws JSONException {
        Country country = new Country(3, "Germany", "Berlin");

        String expectedResponse = "{\n" +
                "    \"id\": 3,\n" +
                "    \"countryName\": \"Germany\",\n" +
                "    \"countryCapital\": \"Berlin\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<>(country, headers);
        ResponseEntity<String> response =  restTemplate.postForEntity("http://localhost:8080/addcountry", request, String.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @Order(5)
    public void updateCountry() throws JSONException {

        Country country = new Country(3, "Japan", "Tokio");
        String expectedResponse = "{\n" +
                "    \"id\": 3,\n" +
                "    \"countryName\": \"Japan\",\n" +
                "    \"countryCapital\": \"Tokio\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Country> request = new HttpEntity<>(country, headers);

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/updatecountry/3", HttpMethod.PUT, request, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    @Test
    @Order(6)
    public void deleteCountry() throws JSONException {
        Country country = new Country(3, "Japan", "Tokyo");

        String expected = "{\n" +
                "    \"id\": 3,\n" +
                "    \"countryName\": \"Japan\",\n" +
                "    \"countryCapital\": \"Tokio\"\n" +
                "}";

        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Country> request = new HttpEntity<>(country, headers);
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/deletecountry/3", HttpMethod.DELETE, request, String.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }



}
