package com.rk.videogameslibrary.dao;

import java.util.*;
import java.lang.String;

/**
 * Classe permettant de créer une clé multivaluée pour la recherche dans VideoGameDAO par plusieurs champs
 */
public class MultiKeyMap extends HashMap<String, Object> {

    /**
     * Constructeur de MultiKeyMap
     * @param map Map clé/valeur associant un champ à une valeur (contenu indexable pour la recherche)
     */
    public MultiKeyMap(Map<String, ?> map) {
        this.put("id", -1);
        this.put("name", "");

        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (key == null || value == null) {
                throw new IllegalArgumentException("Key/value identifier can't be null");
            }

            this.put(key, value);
        }
    }

    /**
     * Overriding de la méthode equals de HashSet.
     * On recherche parmi les couples clé/valeur des clés multivaluées : si un autre objet possède une même clé et valeur, alors il y a un match dans la recherche :
     * cela signifie que l'on peut retourner l'objet VideoGame associé.
     * @param o Objet VideoGame
     * @return true si objet VideoGame matché, false sinon
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MultiKeyMap that = (MultiKeyMap) o;

        for (String key : that.keySet()) {
            if (this.containsKey(key) && this.containsValue(that.get(key))) {
                return true;
            }
        }

        return false;
    }
}

