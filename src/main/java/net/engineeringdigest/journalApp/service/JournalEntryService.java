package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntity;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public Boolean saveEntry(JournalEntity journalEntity){
        if(!journalEntryRepository.existsById(journalEntity.getId())){
            journalEntryRepository.save(journalEntity);
            return true;
        } else return false;
    }

    public List<JournalEntity> getAll() {
        return journalEntryRepository.findAll();
    }

    public Boolean editEntry(JournalEntity je){
        if(journalEntryRepository.existsById(je.getId())){
            journalEntryRepository.save(je);
            return true;
        } else return false;
    }

    public Optional<JournalEntity> getEntityById(String id){
        return journalEntryRepository.findById(id);
    }

    public void deleteEntityById(String id){
        journalEntryRepository.deleteById(id);
    }

}
