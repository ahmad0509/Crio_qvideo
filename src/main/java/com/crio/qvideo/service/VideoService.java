package com.crio.qvideo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.crio.qvideo.dto.VideoDto;
import com.crio.qvideo.entity.Video;
import com.crio.qvideo.repository.VideoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VideoService {

    private final VideoRepository videoRepository;

    public VideoService(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }

    public List<Video> getAvailableVideos() {
        return videoRepository.findByIsAvailable(true);
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Video not found with id: " + id));
    }

    public Video createVideo(VideoDto videoDto) {
        Video video = new Video();
        video.setTitle(videoDto.getTitle());
        video.setDirector(videoDto.getDirector());
        video.setGenre(videoDto.getGenre());
        video.setIsAvailable(videoDto.isAvailable());
        
        return videoRepository.save(video);
    }

    public Video updateVideo(Long id, VideoDto videoDto) {
        Video video = getVideoById(id);
        
        video.setTitle(videoDto.getTitle());
        video.setDirector(videoDto.getDirector());
        video.setGenre(videoDto.getGenre());
        video.setIsAvailable(videoDto.isAvailable());
        
        return videoRepository.save(video);
    }

    public void deleteVideo(Long id) {
        Video video = getVideoById(id);
        videoRepository.delete(video);
    }
}