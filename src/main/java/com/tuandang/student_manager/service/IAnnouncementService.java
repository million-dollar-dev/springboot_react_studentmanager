package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.AnnouncementRequest;
import com.tuandang.student_manager.dto.response.AnnouncementResponse;

import java.util.List;

public interface IAnnouncementService {
    AnnouncementResponse create(AnnouncementRequest request);
    List<AnnouncementResponse> getAnnouncements();
    AnnouncementResponse update(String id, AnnouncementRequest request);
    void delete(String id);
}
