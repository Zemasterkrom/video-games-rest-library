package com.rk.videogameslibrary.web.controller;

import com.rk.videogameslibrary.dao.IVideoGameDAO;
import com.rk.videogameslibrary.model.VideoGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

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
     * Trouver tous les jeux vidéo
     * @return Liste de tous les jeux vidéo existants
     */
    @ResponseBody
    @GetMapping(value = "/video-games/all")
    public Set<VideoGame> getAllVideoGames() {
        return dao.findAll();
    }

    /**
     * Trouver un jeu vidéo spécifique
     * @param identifier Identifiant (ID ou nom) du jeu à chercher
     * @return Jeu vidéo trouvé (si trouvé), sinon jeu vide
     */
    @ResponseBody
    @GetMapping(value = "/video-games/{identifier}")
    public VideoGame getVideoGame(@DefaultValue("") @PathVariable String identifier) {
        return dao.find(identifier);
    }

    /**
     * Ajouter un jeu vidéo
     * @param vg Jeu vidéo à ajouter
     * @return true si succèes, false sinon
     */
    @PostMapping(value = "/video-games/add")
    public boolean addVideoGame(@RequestBody VideoGame vg) {
        return dao.add(vg);
    }

    /**
     * Modifier un jeu vidéo existant
     * @param vg Jeu vidéo à modifier
     * @return true si succès, false sinon
     */
    @PutMapping(value = "/video-games/modify")
    public boolean modifyVideoGame(@RequestBody VideoGame vg) {
        return dao.modify(vg);
    }

    /**
     * Supprimer un jeu vidéo existant
     * @param identifier Identifiant (ID ou nom) du jeu à supprimer
     * @return true si succès, false sinon
     */
    @DeleteMapping(value = "/video-games/delete/{identifier}")
    public boolean deleteVideoGame(@DefaultValue("") String identifier) {
        return dao.delete(identifier);
    }
}
