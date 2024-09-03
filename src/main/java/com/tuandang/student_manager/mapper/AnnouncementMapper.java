package com.tuandang.student_manager.mapper;

import com.tuandang.student_manager.dto.request.AnnouncementRequest;
import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.entity.Announcement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AnnouncementMapper {
    Announcement toAnnouncement(AnnouncementRequest request);
    AnnouncementResponse toAnnouncementResponse(Announcement announcement);
    void updateAnnouncement(@MappingTarget Announcement announcement, AnnouncementRequest request);
}
