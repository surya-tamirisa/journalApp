package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("journal")
public class JournalEntityController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntity> getAll() {
        System.out.println("controller handling get all");
        return journalEntryService.getAll();
    }

    @PostMapping
    public ResponseEntity<JournalEntity> createEntity(@RequestBody @NotNull JournalEntity je){
        je.setDate(LocalDateTime.now());
        try{
            journalEntryService.saveEntry(je);
            return new ResponseEntity<JournalEntity>(je, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable @NotNull ObjectId id, @RequestBody @NotNull JournalEntity je){
        JournalEntity response = journalEntryService.editEntry(id, je);
        System.out.println(response.isPresent());
        if(response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalEntity> getEntityById(@PathVariable String id){
        Optional<JournalEntity> je = journalEntryService.getEntityById(id);

        // return je.map(journalEntity -> new ResponseEntity<>(journalEntity, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));

        if(je.isPresent()){
            return new ResponseEntity<JournalEntity>(je.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntityById(@PathVariable String id){
        journalEntryService.deleteEntityById(id);
        return new ResponseEntity<JournalEntity>(HttpStatus.NO_CONTENT);
    }
}
