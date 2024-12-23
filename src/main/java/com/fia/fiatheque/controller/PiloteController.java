package com.fia.fiatheque.controller;

import com.fia.fiatheque.model.Pilote;
import com.fia.fiatheque.model.Voiture;
import com.fia.fiatheque.repository.PiloteRepository;
import com.fia.fiatheque.repository.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pilotes")
public class PiloteController {

    @Autowired
    private PiloteRepository piloteRepository;

    @Autowired
    private VoitureRepository voitureRepository;

    // Méthode pour créer un pilote
    @PostMapping
    public ResponseEntity<Pilote> createPilote(@RequestBody Pilote pilote) {
        Pilote savedPilote = piloteRepository.save(pilote);  // Sauvegarde le pilote
        return ResponseEntity.ok(savedPilote);  // Retourne le pilote sauvegardé avec un statut 200
    }

    // Méthode pour récupérer tous les pilotes
    @GetMapping
    public ResponseEntity<List<Pilote>> getAllPilotes() {
        List<Pilote> pilotes = piloteRepository.findAll();  // Récupère tous les pilotes
        return ResponseEntity.ok(pilotes);  // Retourne la liste des pilotes avec un statut 200
    }

    // Méthode pour récupérer un pilote par ID
    @GetMapping("/{id}")
    public ResponseEntity<Pilote> getPiloteById(@PathVariable Long id) {
        Optional<Pilote> piloteOptional = piloteRepository.findById(id);

        if (piloteOptional.isPresent()) {
            return ResponseEntity.ok(piloteOptional.get());  // Retourne le pilote avec un statut 200
        } else {
            return ResponseEntity.notFound().build();  // Retourne un statut 404 si le pilote n'est pas trouvé
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePilote(@PathVariable Long id) {
        Optional<Pilote> piloteOptional = piloteRepository.findById(id);
        
        if (piloteOptional.isPresent()) {
            Pilote pilote = piloteOptional.get();
            
            // Détacher la voiture du pilote si elle existe
            if (pilote.getVoiture() != null) {
                Voiture voiture = pilote.getVoiture();
                voiture.setPilote(null);  // Détache la voiture du pilote
                voitureRepository.save(voiture);  // Sauvegarde de la voiture sans pilote
            }
            
            // Supprimer le pilote
            piloteRepository.delete(pilote);
            
            // Réponse avec un message personnalisé de succès
            return ResponseEntity.ok("Pilote avec ID " + id + " supprimé avec succès.");
        } else {
            return ResponseEntity.notFound().build();  // Retourne un status 404 si le pilote n'est pas trouvé
        }
    }

}
