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

    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        if (oldValue == null || newValue == null) {
            throw new IllegalArgumentException("Can't replace with null values");
        }

        return super.replace(key, oldValue, newValue);
    }

    @Override
    public Object put(String key, Object value) {
        if (key == null || value== null) {
            throw new IllegalArgumentException("Can't put with null values");
        }

        return super.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        if (m == null) {
            throw new IllegalArgumentException("Can't put with null values");
        }

        for (Object o:m.keySet()) {
            String key = o == null ? null : o.toString();
            Object value = key == null ? null : m.get(o.toString());

            if (key == null || value == null) {
                throw new IllegalArgumentException("Can't put with null values");
            }

            this.put(key, value);
        }

        super.putAll(m);
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

    /**
     * Puisque le hashCode ne peut pas être généré étant donné la structure dynamique de la Map, nous devons calculer le hashCode manuellement.
     * Le HashCode est calculé selon le hashCode de chaque objet héritant de Object.
     * @return HashCode de la MultiKeyMap
     */
    @Override
    public int hashCode() {
        int hash = 0;

        for (String s:this.keySet()) {
            hash += s.hashCode();
        }

        return hash;
    }
}

