package com.rk.videogameslibrary.model;

import java.util.Date;
import java.util.Objects;

/**
 * Classe représentant le bean d'un jeu vidéo.
 * Des vérifications basiques sont effectuées concernant le type des données (champs non vides et non null)
 */
public class VideoGame {

    /**
     * Identifiant du jeu vidéo
     */
    public int id;

    /**
     * Nom du jeu vidéo
     */
    public String name;

    /**
     * Editeur du jeu vidéo
     */
    public String editor;

    /**
     * Description du jeu vidéo
     */
    public String description;

    /**
     * Date de sortie du jeu
     */
    public Date releasedDate;

    /**
     * Constructeur par défaut de VideoGame
     */
    public VideoGame() {
        this.id = -1;
        this.name = "";
        this.editor = "";
        this.description = "";
        this.releasedDate = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID can't be lesser than 0");
        }

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can't be empty");
        }

        this.name = name;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        if (editor == null || editor.trim().isEmpty()) {
            throw new IllegalArgumentException("Editor can't be empty");
        }

        this.editor = editor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description can't be empty");
        }

        this.description = description;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        if (releasedDate == null) {
            throw new IllegalArgumentException("Release date can't be empty");
        }

        this.releasedDate = releasedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoGame videoGame = (VideoGame) o;
        return id == videoGame.id && name.equals(videoGame.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
