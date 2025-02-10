package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;

    @Transactional
    public void saveEntry(JournalEntity journalEntity, @NotNull String username){
        try {
            User user = userService.findByUsername(username);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepository.save(journalEntity);
            user.getJournalEntries().add(saved);
            userService.updateUser(user);
        } catch (Exception e){
            log.error("e: ", e);
            throw new RuntimeException("An error occurred while saving", e);
        }
    }

    public void saveEntry(JournalEntity journalEntity){
        journalEntryRepository.save(journalEntity);
    }

    public List<JournalEntity> getAll() {
        return journalEntryRepository.findAll();
    }

    public JournalEntity editEntry(ObjectId id, JournalEntity je){

        if(journalEntryRepository.existsById(id)){
            JournalEntity old = journalEntryRepository.findById(id).orElse(null);
            je.setDate(LocalDateTime.now());
            je.setId(id);
            je.setTitle(je.getTitle() != null? je.getTitle() : Objects.requireNonNull(old).getTitle());
            je.setContent(je.getContent() != null? je.getContent() : Objects.requireNonNull(old).getContent());
            journalEntryRepository.save(je);
            return je;
        } else return null;
    }

    public Optional<JournalEntity> getEntityById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntityById(ObjectId id, String username){
        User user = userService.findByUsername(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.updateUser(user);
        journalEntryRepository.deleteById(id);
    }

}
