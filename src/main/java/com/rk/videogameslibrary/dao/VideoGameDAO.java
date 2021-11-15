package com.rk.videogameslibrary.dao;

import com.rk.videogameslibrary.model.VideoGame;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe représentant le DAO de la base de données statique de jeux vidéo (la librairie)
 */
@Component
public class VideoGameDAO implements IVideoGameDAO{

    /**
     * Base de données statique de jeux vidéo associant une clée multivaluée à un jeu vidéo
     */
    private static final Map<MultiKeyMap, VideoGame> VIDEO_GAMES_LIBRARY = new HashMap<>();

    /**
     * Retourner tous les jeux vidéo
     * @return Liste unique de jeux vidéo
     */
    @Override
    public Set<VideoGame> findAll() {
        return new HashSet<>(VIDEO_GAMES_LIBRARY.values());
    }

    /**
     * Trouver et retourner un jeu vidéo existant
     * @param identifier Identifiant de recherche (ID ou nom)
     * @return Jeu vidéo si existant, un jeu vide sinon
     */
    @Override
    public VideoGame find(String identifier) {
        try {
            MultiKeyMap multiKeys = this.constructMultiKeyMap(identifier);
            return VIDEO_GAMES_LIBRARY.containsKey(multiKeys) ? VIDEO_GAMES_LIBRARY.get(multiKeys) : new VideoGame();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return new VideoGame();
        }
    }

    /**
     * Ajouter un jeu vidéo
     * @param vg Jeu vidéo à ajouter
     * @return true si ajouté (non existant et correctement formaté), false sinon
     */
    @Override
    public boolean add(VideoGame vg) {
        try {
            MultiKeyMap keys;
            Set<MultiKeyMap> allKeys;
            int maxId;

            if (this.videoGameExists(vg.getName())) {
                System.err.println("Video game already exists");
                return false;
            }

            maxId = vg.getId();

            keys = new MultiKeyMap(new HashMap<String, Object>() {{
                put("name", vg.getName());
            }});

            allKeys = VIDEO_GAMES_LIBRARY.keySet();

            for (MultiKeyMap key:allKeys) {
                int id = Integer.parseInt(key.get("id").toString()) + 1;

                if (id > maxId) {
                    maxId = id;
                }
            }

            if (maxId == -1) {
                maxId = 0;
            }

            keys.put("id", maxId);
            vg.setId(maxId);

            if (vg.incorrectData()) {
                System.err.println("Video game data is not well formatted");
                return false;
            }

            VIDEO_GAMES_LIBRARY.put(keys, vg);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Modifier un jeu vidéo existant
     * @param identifier Identifiant (ID ou nom) du jeu vidéo à modifier
     * @param vg Jeu vidéo à modifier
     * @return true si jeu modifié avec succès (existant et correctement formaté), false sinon
     */
    @Override
    public boolean modify(String identifier, VideoGame vg) {
        try {
            if (!this.videoGameExists(identifier)) {
                return false;
            }

            vg.setId(this.find(identifier).getId());

            if (vg.incorrectData()) {
                System.err.println("Video game data is not well formatted");
                return false;
            }

            VIDEO_GAMES_LIBRARY.put(new MultiKeyMap(new HashMap<String, Object>() {{
                put("id", vg.getId());
                put("name", vg.getName());
            }}), vg);

            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Supprimer un jeu vidéo existant
     * @param identifier Jeu vidéo à supprimer
     * @return true si supprimé (existant), false sinon
     */
    @Override
    public boolean delete(String identifier) {
        try {
            return VIDEO_GAMES_LIBRARY.remove(this.constructMultiKeyMap(identifier)) != null;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de vérifier si un jeu vidéo existe
     * @param identifier Identifiant (ID ou nom) du jeu vidéo
     * @return true si existant, false sinon
     */
    public boolean videoGameExists(String identifier) {
        return VIDEO_GAMES_LIBRARY.containsKey(this.constructMultiKeyMap(identifier));
    }

    /**
     * Construire un MultiKeyMap selon le format de la donnée
     * @param info Information (actuellement ID ou nom du jeu)
     * @return Clé multivaluée correspondante
     */
    private MultiKeyMap constructMultiKeyMap(String info) {
        Map<String, Object> keys = new HashMap<>();

        if (info != null) {
            if (info.matches("^[0-9]+$")) {
                keys.put("id", Integer.parseInt(info));
            } else {
                keys.put("name", info);
            }
        }

        return new MultiKeyMap(keys);
    }
}
