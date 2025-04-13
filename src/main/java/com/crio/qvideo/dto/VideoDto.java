package com.crio.qvideo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Director is required")
    private String director;
    
    @NotBlank(message = "Genre is required")
    private String genre;
    
    private boolean isAvailable = true;
}