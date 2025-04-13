package com.crio.qvideo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.qvideo.entity.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findByIsAvailable(boolean isAvailable);
}
