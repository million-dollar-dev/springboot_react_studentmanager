package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.AnnouncementRequest;
import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.exception.AppException;
import com.tuandang.student_manager.exception.ErrorCode;
import com.tuandang.student_manager.mapper.AnnouncementMapper;
import com.tuandang.student_manager.repository.AnnouncementRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementService implements IAnnouncementService{
    AnnouncementRepository announcementRepository;
    AnnouncementMapper announcementMapper;

    @Override
    public AnnouncementResponse create(AnnouncementRequest request) {
        var announcement = announcementMapper.toAnnouncement(request);
        announcement.setDatePosted(LocalDate.now());
        announcementRepository.save(announcement);
        return announcementMapper.toAnnouncementResponse(announcement);
    }

    @Override
    public List<AnnouncementResponse> getAnnouncements() {
        List<AnnouncementResponse> list = new ArrayList<>();
        announcementRepository.findAll()
                .forEach(announcement -> list.add(announcementMapper.toAnnouncementResponse(announcement)));
        return list;
    }

    @Override
    public AnnouncementResponse update(String id, AnnouncementRequest request) {
        var announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ANNOUNCEMENT_NOT_EXISTED));
        announcementMapper.updateAnnouncement(announcement, request);
        announcement.setDatePosted(LocalDate.now());
        return announcementMapper.toAnnouncementResponse(announcement);
    }

    @Override
    public void delete(String id) {
        announcementRepository.deleteById(id);
    }
}
