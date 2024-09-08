package com.tuandang.student_manager.controller;

import com.tuandang.student_manager.dto.request.AnnouncementRequest;
import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.dto.response.ApiResponse;
import com.tuandang.student_manager.dto.response.PageResponse;
import com.tuandang.student_manager.service.IAnnouncementService;
import jakarta.validation.constraints.Min;
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

    // @RequestParam là ?pageNo=
    @GetMapping
    public ApiResponse<PageResponse> getPosts(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String sortBy) {
        return ApiResponse.<PageResponse>builder()
                .result(announcementService.getAnnouncements(pageNo, pageSize, sortBy))
                .build();
    }

    @GetMapping
    public ApiResponse<PageResponse> getPostsWithSortMultipleColumns(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(10) @RequestParam(defaultValue = "20", required = false) int pageSize,
            @RequestParam(required = false) String... sorts) {
        return ApiResponse.<PageResponse>builder()
                .result(announcementService.getAnnouncementsWithMultipleColumns(pageNo, pageSize, sorts))
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

    @GetMapping("/advance-search-with-criteria")
    public ApiResponse<PageResponse> advanceSeachWithCriteria(@RequestParam(defaultValue = "0", required = false) int pageNo,
                                                   @RequestParam(defaultValue = "20", required = false) int pageSize,
                                                   @RequestParam(required = false) String sortBy,
                                                   @RequestParam(required = false) String... search
                                                   ) {
        return ApiResponse.<PageResponse>builder()
                .result(announcementService.advanceWithCriteria(pageNo, pageSize, sortBy, search))
                .build();
    }

}
