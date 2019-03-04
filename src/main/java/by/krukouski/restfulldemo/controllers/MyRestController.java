package by.krukouski.restfulldemo.controllers;

import by.krukouski.restfulldemo.models.User;
import by.krukouski.restfulldemo.services.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class MyRestController {

    private static Logger log = LogManager.getLogger(MyRestController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        log.info("Getting user be id");
        User user = userService.findById(id);
        if (user == null) {
            log.debug("user with id " + id + " not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder uriComponentsBuilder) {
        log.info("Creating user " + user.getName());

        if (userService.isUserExist(user)) {
            log.debug("user with name " + user.getName() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/user/{id}")
                                .buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable long id,
                                           @RequestBody User user) {
        log.info("Updating User " + id);

        User currentUser = userService.findById(id);

        if(currentUser == null) {
            log.warn("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());

        userService.updateUser(currentUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
