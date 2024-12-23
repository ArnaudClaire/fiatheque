package com.fia.fiatheque.controller;

import com.fia.fiatheque.model.Voiture;
import com.fia.fiatheque.model.Pilote;
import com.fia.fiatheque.repository.VoitureRepository;
import com.fia.fiatheque.repository.PiloteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;  // Import pour List

@RestController
@RequestMapping("/api/voitures")
public class VoitureController {

    @Autowired
    private VoitureRepository voitureRepository;

    @Autowired
    private PiloteRepository piloteRepository;

    @PostMapping
    public ResponseEntity<Voiture> createVoiture(@RequestBody Voiture voiture) {
        // Vérification de l'existence du pilote via son id
        Long piloteId = voiture.getPilote() != null ? voiture.getPilote().getId() : null;
        
        if (piloteId == null) {
            return ResponseEntity.badRequest().body(null);  // Le pilote est requis
        }
        // Récupération du pilote via son id
        Pilote pilote = piloteRepository.findById(piloteId).orElse(null);
        if (pilote == null) {
            return ResponseEntity.badRequest().body(null);  // Pilote non trouvé
        }
        voiture.setPilote(pilote);  // Associer la voiture au pilote
        Voiture savedVoiture = voitureRepository.save(voiture);  // Sauvegarde de la voiture
        return ResponseEntity.ok(savedVoiture);
    }


    // Récupérer toutes les voitures
    @GetMapping
    public List<Voiture> getAllVoitures() {
        return voitureRepository.findAll();
    }

    // Récupérer une voiture par son id
    @GetMapping("/{id}")
    public ResponseEntity<Voiture> getVoitureById(@PathVariable Long id) {
        return voitureRepository.findById(id)
                .map(voiture -> ResponseEntity.ok(voiture))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Supprimer une voiture
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiture(@PathVariable Long id) {
        if (voitureRepository.existsById(id)) {
            voitureRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
