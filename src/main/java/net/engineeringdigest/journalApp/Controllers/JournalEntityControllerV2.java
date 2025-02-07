package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        je.setDate(LocalDateTime.now());
        return journalEntryService.saveEntry(je);
    }

    @PutMapping("/{id}")
    public Boolean updateEntry(@PathVariable @NotNull ObjectId id, @RequestBody @NotNull JournalEntity je){
        return journalEntryService.editEntry(id, je);
    }

    @GetMapping("/{id}")
    public Optional<JournalEntity> getEntityById(@PathVariable String id){
        return journalEntryService.getEntityById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteEntityById(@PathVariable String id){
        journalEntryService.deleteEntityById(id);
    }
}
