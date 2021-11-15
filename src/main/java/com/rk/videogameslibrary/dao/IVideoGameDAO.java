package com.rk.videogameslibrary.dao;

import com.rk.videogameslibrary.model.VideoGame;

import java.util.Set;

/**
 * Interface des opérations du DAO de jeux vidéo
 */
public interface IVideoGameDAO {

    Set<VideoGame> findAll();

    VideoGame find(String identifier);

    boolean add(VideoGame vg);

    boolean modify(VideoGame vg);

    boolean delete(String identifier);
}
