package com.rk.videogameslibrary.dao;

import com.rk.videogameslibrary.model.VideoGame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe représentant le DAO de la base de données statique de jeux vidéo (la librairie)
 */
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
     * @param keys Clé multivaluée de recherche
     * @return Jeu vidéo si existant, un jeu vide sinon
     */
    @Override
    public VideoGame find(MultiKeyMap keys) {
        try {
            return VIDEO_GAMES_LIBRARY.containsKey(keys) ? VIDEO_GAMES_LIBRARY.get(keys) : new VideoGame();
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
            int maxId = 0;

            if (this.videoGameExists(vg)) {
                System.err.println("Video game already exists");
                return false;
            }

            keys = new MultiKeyMap(new HashMap<String, Object>() {{
                put("name", vg.getName());
            }});


            allKeys = VIDEO_GAMES_LIBRARY.keySet();

            for (MultiKeyMap key:allKeys) {
                int id = Integer.parseInt(key.get("id").toString());

                if (id > maxId) {
                    maxId = id;
                }
            }

            keys.put("id", maxId);
            VIDEO_GAMES_LIBRARY.put(keys, vg);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Modifier un jeu vidéo existant
     * @param vg Jeu vidéo à modifier
     * @return true si jeu modifié avec succès (existant et correctement formaté), false sinon
     */
    @Override
    public boolean modify(VideoGame vg) {
        try {
            if (!this.videoGameExists(vg)) {
                return false;
            }

            VIDEO_GAMES_LIBRARY.put(new MultiKeyMap(new HashMap<String, Object>() {{
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
     * @param vg Jeu vidéo à supprimer
     * @return true si supprimé (existant), false sinon
     */
    @Override
    public boolean delete(VideoGame vg) {
        try {
            if (this.videoGameExists(vg)) {
                VIDEO_GAMES_LIBRARY.remove(new MultiKeyMap(new HashMap<String, Object>() {{
                    put("name", vg.getName());
                }}));

                return true;
            }
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    /**
     * Permet de vérifier si un jeu vidéo existe
     * @param vg Jeu vidéo
     * @return true si existant, false sinon
     */
    public boolean videoGameExists(VideoGame vg) {
        MultiKeyMap keys;

        if (vg == null) {
            System.err.println("Video game can't be null");
            return false;
        }

        keys = new MultiKeyMap(new HashMap<String, Object>() {{
            put("name", vg.getName());
        }});

        return VIDEO_GAMES_LIBRARY.containsKey(keys);
    }
}
