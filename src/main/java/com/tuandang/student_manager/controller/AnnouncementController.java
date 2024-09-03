package com.tuandang.student_manager.controller;

import com.tuandang.student_manager.dto.request.AnnouncementRequest;
import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.dto.response.ApiResponse;
import com.tuandang.student_manager.service.IAnnouncementService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/posts")
public class AnnouncementController {
    IAnnouncementService announcementService;

    @PostMapping
    public ApiResponse<AnnouncementResponse> create(@RequestBody AnnouncementRequest request) {
        return ApiResponse.<AnnouncementResponse>builder()
                .result(announcementService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<AnnouncementResponse>> getPosts() {
        return ApiResponse.<List<AnnouncementResponse>>builder()
                .result(announcementService.getAnnouncements())
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<AnnouncementResponse> updatePost(
            @PathVariable String id,
            @RequestBody AnnouncementRequest request) {
        return ApiResponse.<AnnouncementResponse>builder()
                .result(announcementService.update(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<AnnouncementResponse> deletePost(@PathVariable String id) {
        announcementService.delete(id);
        return ApiResponse.<AnnouncementResponse>builder()
                .message("Delete successfully!")
                .build();
    }

}
