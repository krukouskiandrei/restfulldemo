package by.krukouski.restfulldemo;

import by.krukouski.restfulldemo.models.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

public class MyRestClient {

    private static Logger log = LogManager.getLogger(MyRestClient.class);

    private static final String REST_SERVICE_URI = "http://localhost:8081/restfullservicedemo";

    private static final RestTemplate restTemplate = new RestTemplate();

    private static void listAllUsers() {
        log.info("Testing list all users API");

        List<LinkedHashMap<String, Object>> userList =
                restTemplate.getForObject(REST_SERVICE_URI + "/user/",
                                    List.class);

        if(userList != null) {
            for (LinkedHashMap<String, Object> user : userList) {
                log.info("User: id = " + user.get("id")
                + ", name = " + user.get("name")
                + ", age = " + user.get("age")
                + ", salary = " + user.get("salary"));
            }
        } else {
            log.warn("no user exist");
        }
    }

    private static void getUser(long id) {
        log.info("Testing get user API");

        User user = restTemplate.getForObject(REST_SERVICE_URI
                    + "/user/" + id, User.class);

        log.info(user.toString());
    }

    private static void createUser() {
        log.info("Testing create user API");

        User user = new User("Alex", 33, 3300);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI
                    + "/user/", user, User.class);

        log.info("Location: " + uri);
    }

    private static void updateUser(long id) {
        log.info("Testing update user API");

        User user = new User(1, "Tim", 44, 4400);
        restTemplate.put(REST_SERVICE_URI + "/user/" + id, user);
        log.info(user);
    }

    private static void deleteUser(long id) {
        log.info("Testing delete user API");
        restTemplate.delete(REST_SERVICE_URI + "/user/" + id);
        log.info("User with id = " + id + " was deleted");
    }

    private static void deleteAllUsers() {
        log.info("Testing delete all users API");
        restTemplate.delete(REST_SERVICE_URI + "/user/");
        log.info("All users were deleted");
    }

    public static void main(String[] args) {
        listAllUsers();
        getUser(1);

        createUser();
        listAllUsers();

        updateUser(1);
        listAllUsers();

        deleteUser(3);
        listAllUsers();

        deleteAllUsers();
        listAllUsers();
    }



}
