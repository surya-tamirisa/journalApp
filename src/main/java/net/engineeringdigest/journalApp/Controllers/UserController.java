package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    private List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    private ResponseEntity<?> createUser(@RequestBody @NotNull User user) {
        User createdUser = userService.createUser(user);
        if(createdUser != null){
            return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    private ResponseEntity<?> updateUser(@RequestBody @NotNull User user){
        User userInDb = userService.findByUsername(user.getUsername());
        if(userInDb != null) {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.updateUser(userInDb);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{username}")
    private ResponseEntity<?> updateUser(@PathVariable @NotNull String username, @RequestBody @NotNull User user){
        User userInDb = userService.findByUsername(username);
        if(userInDb != null) {
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.updateUser(userInDb);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{username}")
    private ResponseEntity<?> getUser(@PathVariable @NotNull String username){
        User user = userService.findByUsername(username);
        if(user!= null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{username}")
    private ResponseEntity<?> deleteUser(@PathVariable @NotNull String username) {
        User user = userService.findByUsername(username);
        if(user!= null) {
            userService.deleteUser(user);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
