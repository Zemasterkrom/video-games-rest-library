package com.rk.videogameslibrary.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    public String releasedDate;

    /**
     * Constructeur par défaut de VideoGame
     */
    public VideoGame() {
        this.id = -1;
        this.name = "";
        this.editor = "";
        this.description = "";
        this.releasedDate = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws IllegalArgumentException {
        if (id < 0) {
            throw new IllegalArgumentException("ID can't be lesser than 0");
        }

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws IllegalArgumentException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name can't be empty");
        }

        this.name = name;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) throws IllegalArgumentException {
        if (editor == null) {
            throw new IllegalArgumentException("Editor can't be null");
        }

        this.editor = editor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException {
        if (description == null) {
            throw new IllegalArgumentException("Description can't be null");
        }

        this.description = description;
    }

    public String getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(String releasedDate) throws IllegalArgumentException {
        try {
            LocalDate.parse(releasedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            this.releasedDate = releasedDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Release date can't be empty and must be formatted according to the format yyyy-MM-dd");
        }
    }

    /**
     * Vérifier si les champs auto-injectés sont bien formatés en retestant les setters
     * @return true si bien formatés, false sinon
     */
    public boolean incorrectData() {
        try {
            this.setId(this.id);
            this.setName(this.name);
            this.setEditor(this.editor);
            this.setDescription(this.description);
            this.setReleasedDate(this.releasedDate);

            return false;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            return true;
        }
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
