package com.rk.videogameslibrary.web.controller;

import com.rk.videogameslibrary.dao.IVideoGameDAO;
import com.rk.videogameslibrary.model.VideoGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * Controller permettant de modifier la base statique de jeux vidéo présente
 */
@Controller
public class VideoGameLibraryController {

    /**
     * Lien autowired vers le DAO VideoGameDAO
     */
    @Autowired
    IVideoGameDAO dao;

    /**
     * Trouver tous les jeux vidéo triés par ID
     * @return Liste de tous les jeux vidéo existants
     */
    @ResponseBody
    @GetMapping(value = "/video-games/all")
    public Set<VideoGame> getAllVideoGames() {
        Set<VideoGame> vgs = new TreeSet<>((vgOne, vgTwo) -> {
            if (vgOne.getId() == vgTwo.getId()) {
                return 0;
            } else if (vgOne.getId() > vgTwo.getId()) {
                return 1;
            }
            return -1;
        });
        vgs.addAll(dao.findAll());

        return vgs;
    }

    /**
     * Trouver un jeu vidéo spécifique
     * @param identifier Identifiant (ID ou nom) du jeu à chercher
     * @return Jeu vidéo trouvé (si trouvé), sinon code 204
     */
    @ResponseBody
    @GetMapping(value = "/video-games/{identifier}")
    public ResponseEntity<?> getVideoGame(@DefaultValue("") @PathVariable String identifier) {
        VideoGame vg = dao.find(identifier);
        return vg.getId() != -1 ? new ResponseEntity<>(vg, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Ajouter un jeu vidéo
     * @param vg Jeu vidéo à ajouter
     * @return Réponse HTTP : 409 si déjà existant, 400 si requête mal formée, 201 si créé
     */
    @PostMapping(value = "/video-games/add")
    public ResponseEntity<?> addVideoGame(@RequestBody VideoGame vg) {
        if (!dao.add(vg)) {
            if (dao.videoGameExists(vg.getName())) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * Modifier un jeu vidéo existant
     * @param identifier Identifiant (ID ou nom) du jeu à modifier
     * @param vg Jeu vidéo à modifier
     * @return Réponse HTTP : 204 si non existant, 400 si requête mal formée, 200 si existant et retourné
     */
    @PutMapping(value = "/video-games/modify/{identifier}")
    public ResponseEntity<?> modifyVideoGame(@DefaultValue("") @PathVariable("identifier") String identifier, @RequestBody VideoGame vg) {
        if (!dao.modify(identifier, vg)) {
            if (!dao.videoGameExists(identifier)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Supprimer un jeu vidéo existant
     * @param identifier Identifiant (ID ou nom) du jeu à supprimer
     * @return Réponse HTTP : 204 si non existant, 400 si requête mal formée, 200 si supprimé
     */
    @DeleteMapping(value = "/video-games/delete/{identifier}")
    public ResponseEntity<?> deleteVideoGame(@DefaultValue("") @PathVariable("identifier") String identifier) {
        if (!dao.delete(identifier)) {
            if (!dao.videoGameExists(identifier)) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
