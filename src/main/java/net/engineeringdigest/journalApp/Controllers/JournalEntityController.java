package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalEntityController {

    private HashMap<Long, JournalEntity> journalEntities = new HashMap<>();

    @GetMapping
    public List<JournalEntity> getAllJournals() {
        return new ArrayList<JournalEntity>(journalEntities.values());
    }

    @PostMapping
    public boolean createEntity(@RequestBody @NotNull JournalEntity je){
        System.out.println("Received: " + je.getTitle());
        journalEntities.put(je.getId(), je);
        return true;
    }

    @GetMapping("test")
    public String welcomePageText(){
        return "Not Implemented as of now... Stay Tuned!";
    }

    @PutMapping
    public boolean updateEntry(@RequestBody @NotNull JournalEntity je){
        long id = je.getId();
        if(journalEntities.containsKey(id)){
            journalEntities.replace(id, je);
            return true;
        }
        return false;
    }

    @GetMapping("/{id}")
    public JournalEntity getEntityById(@PathVariable long id){
        return journalEntities.get(id);
    }
}
