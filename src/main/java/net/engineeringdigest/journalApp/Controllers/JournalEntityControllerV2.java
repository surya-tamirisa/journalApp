package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("journal")
public class JournalEntityControllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntity> getAll() {
        System.out.println("controller handling get all");
        return journalEntryService.getAll();
    }

    @PostMapping
    public Boolean createEntity(@RequestBody @NotNull JournalEntity je){
        journalEntryService.saveEntry(je);
        return true;
    }

    @PutMapping("/{id}")
    public void updateEntry(@PathVariable Long id, @RequestBody @NotNull JournalEntity je){

    }

    @GetMapping("/{id}")
    public JournalEntity getEntityById(@PathVariable String id){
        return null;
    }

    @DeleteMapping("/{id}")
    public JournalEntity deleteEntityById(@PathVariable String id){
        return null;
    }
}
