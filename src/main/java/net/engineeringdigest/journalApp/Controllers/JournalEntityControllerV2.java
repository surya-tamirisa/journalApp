package net.engineeringdigest.journalApp.Controllers;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return journalEntryService.saveEntry(je);
    }

    @PutMapping("/{id}")
    public Boolean updateEntry(@PathVariable String id, @RequestBody @NotNull JournalEntity je){
        return journalEntryService.editEntry(je);
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
