package com.crio.qvideo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.crio.qvideo.dto.VideoDto;
import com.crio.qvideo.entity.Video;
import com.crio.qvideo.repository.VideoRepository;
import com.crio.qvideo.service.VideoService;

import jakarta.persistence.EntityNotFoundException;

class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideoService videoService;

    private Video testVideo;
    private VideoDto testVideoDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testVideo = new Video();
        testVideo.setVideoId(1L);
        testVideo.setTitle("Test Movie");
        testVideo.setDirector("Test Director");
        testVideo.setGenre("Action");
        testVideo.setIsAvailable(true);

        testVideoDto = new VideoDto();
        testVideoDto.setTitle("Test Movie");
        testVideoDto.setDirector("Test Director");
        testVideoDto.setGenre("Action");
        testVideoDto.setAvailable(true);
    }

    @Test
    void getAllVideos_ShouldReturnAllVideos() {
        // Arrange
        List<Video> expectedVideos = Arrays.asList(testVideo);
        when(videoRepository.findAll()).thenReturn(expectedVideos);

        // Act
        List<Video> actualVideos = videoService.getAllVideos();

        // Assert
        assertEquals(expectedVideos, actualVideos);
        verify(videoRepository, times(1)).findAll();
    }

    @Test
    void getVideoById_WithValidId_ShouldReturnVideo() {
        // Arrange
        when(videoRepository.findById(1L)).thenReturn(Optional.of(testVideo));

        // Act
        Video foundVideo = videoService.getVideoById(1L);

        // Assert
        assertNotNull(foundVideo);
        assertEquals(testVideo.getVideoId(), foundVideo.getVideoId());
        assertEquals(testVideo.getTitle(), foundVideo.getTitle());
        verify(videoRepository, times(1)).findById(1L);
    }

    @Test
    void getVideoById_WithInvalidId_ShouldThrowException() {
        // Arrange
        when(videoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> videoService.getVideoById(99L));
        verify(videoRepository, times(1)).findById(99L);
    }

    @Test
    void createVideo_ShouldSaveAndReturnVideo() {
        // Arrange
        when(videoRepository.save(any(Video.class))).thenReturn(testVideo);

        // Act
        Video createdVideo = videoService.createVideo(testVideoDto);

        // Assert
        assertNotNull(createdVideo);
        assertEquals(testVideo.getTitle(), createdVideo.getTitle());
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    @Test
    void updateVideo_WithValidId_ShouldUpdateAndReturnVideo() {
        // Arrange
        when(videoRepository.findById(1L)).thenReturn(Optional.of(testVideo));
        when(videoRepository.save(any(Video.class))).thenReturn(testVideo);

        // Modify the DTO
        testVideoDto.setTitle("Updated Title");

        // Act
        Video updatedVideo = videoService.updateVideo(1L, testVideoDto);

        // Assert
        assertNotNull(updatedVideo);
        assertEquals(testVideoDto.getTitle(), updatedVideo.getTitle());
        verify(videoRepository, times(1)).findById(1L);
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    @Test
    void deleteVideo_WithValidId_ShouldDeleteVideo() {
        // Arrange
        when(videoRepository.findById(1L)).thenReturn(Optional.of(testVideo));
        doNothing().when(videoRepository).delete(any(Video.class));

        // Act
        videoService.deleteVideo(1L);

        // Assert
        verify(videoRepository, times(1)).findById(1L);
        verify(videoRepository, times(1)).delete(any(Video.class));
    }
}