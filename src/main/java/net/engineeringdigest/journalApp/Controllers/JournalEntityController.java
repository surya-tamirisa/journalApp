package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntityController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("user/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable @NotNull String username) {
        User user = userService.findByUsername(username);
        List<JournalEntity> all = user.getJournalEntries();
        if(all!= null && !all.isEmpty()){
            return new ResponseEntity<List<JournalEntity>>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("user/{username}")
    public ResponseEntity<JournalEntity> createEntity(@PathVariable @NotNull String username, @RequestBody @NotNull JournalEntity je){
        try {
            journalEntryService.saveEntry(je, username);
            return new ResponseEntity<>(je, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("id/{username}/{id}")
    public ResponseEntity<?> updateEntry(
            @PathVariable @NotNull ObjectId id,
            @RequestBody @NotNull JournalEntity je,
            @PathVariable String username
    ){
//        JournalEntity response = journalEntryService.editEntry(id, je);
//        if(response != null) {
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        JournalEntity old = journalEntryService.getEntityById(id).orElse(null);
        if(old != null){
            old.setTitle(je.getTitle() != null? je.getTitle() : Objects.requireNonNull(old).getTitle());
            old.setContent(je.getContent() != null? je.getContent() : Objects.requireNonNull(old).getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntity> getEntityById(@PathVariable ObjectId id){
        Optional<JournalEntity> je = journalEntryService.getEntityById(id);

        // return je.map(journalEntity -> new ResponseEntity<>(journalEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

        if(je.isPresent()){
            return new ResponseEntity<JournalEntity>(je.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("id/{username}/{id}")
    public ResponseEntity<?> deleteEntityById(@PathVariable ObjectId id, @PathVariable String username){
        journalEntryService.deleteEntityById(id, username);
        return new ResponseEntity<JournalEntity>(HttpStatus.NO_CONTENT);
    }
}
