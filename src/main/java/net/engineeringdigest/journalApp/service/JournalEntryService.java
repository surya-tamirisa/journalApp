package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntity journalEntity){
        journalEntryRepository.save(journalEntity);
    }

    public List<JournalEntity> getAll() {
        return journalEntryRepository.findAll();
    }

    public JournalEntity editEntry(ObjectId id, JournalEntity je){

        if(journalEntryRepository.existsById(id.toHexString())){
            JournalEntity old = journalEntryRepository.findById(id.toHexString()).orElse(null);
            je.setDate(LocalDateTime.now());
            je.setId(id);
            je.setTitle(je.getTitle() != null? je.getTitle() : Objects.requireNonNull(old).getTitle());
            je.setContent(je.getContent() != null? je.getContent() : Objects.requireNonNull(old).getContent());
            journalEntryRepository.save(je);
            return je;
        } else return null;
    }

    public Optional<JournalEntity> getEntityById(String id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntityById(String id){
        journalEntryRepository.deleteById(id);
    }

}
