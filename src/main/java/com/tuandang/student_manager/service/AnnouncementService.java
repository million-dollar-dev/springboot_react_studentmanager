package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.AnnouncementRequest;
import com.tuandang.student_manager.dto.response.AnnouncementResponse;
import com.tuandang.student_manager.dto.response.PageResponse;
import com.tuandang.student_manager.entity.Announcement;
import com.tuandang.student_manager.exception.AppException;
import com.tuandang.student_manager.exception.ErrorCode;
import com.tuandang.student_manager.mapper.AnnouncementMapper;
import com.tuandang.student_manager.repository.AnnouncementRepository;
import com.tuandang.student_manager.repository.SearchRepository;
import com.tuandang.student_manager.repository.criteria.SearchCriteria;
import com.tuandang.student_manager.repository.specification.AnnouncementSpec;
import com.tuandang.student_manager.repository.specification.AnnouncementSpecificationBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnouncementService implements IAnnouncementService{
    AnnouncementRepository announcementRepository;
    AnnouncementMapper announcementMapper;
    SearchRepository searchRepository;

    @Override
    public AnnouncementResponse create(AnnouncementRequest request) {
        var announcement = announcementMapper.toAnnouncement(request);
        announcementRepository.save(announcement);
        return announcementMapper.toAnnouncementResponse(announcement);
    }

    @Override
    public PageResponse<List<AnnouncementResponse>> getAnnouncements(int pageNo, int pageSize, String sortBy) {
        int page = 0;
        if (pageNo > 0)
            page = pageNo - 1;
        List<AnnouncementResponse> list = new ArrayList<>();
        List<Sort.Order> orders = new ArrayList<>();
        // Kiểm tra giá trị sortBy
        if (StringUtils.hasLength(sortBy)) {
            // name:asc|desc
            //Sử dụng regex để tách chuỗi kiểm tra
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(orders));
        Page<Announcement> announcements = announcementRepository.findAll(pageable);
        announcements.forEach(announcement -> list.add(announcementMapper.toAnnouncementResponse(announcement)));
        return PageResponse.<List<AnnouncementResponse>>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .pageTotal(announcements.getTotalPages())
                .item(list)
                .build();
    }

    @Override
    public PageResponse<List<AnnouncementResponse>> getAnnouncementsWithMultipleColumns(int pageNo, int pageSize, String... sorts) {
        int page = 0;
        if (pageNo > 0)
            page = pageNo - 1;
        List<AnnouncementResponse> list = new ArrayList<>();
        List<Sort.Order> orders = new ArrayList<>();
        // Kiểm tra giá trị sortBy
        for (String sortBy:sorts) {
            // name:asc|desc
            //Sử dụng regex để tách chuỗi kiểm tra
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("asc")) {
                    orders.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                }
            }
        }
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(orders));
        Page<Announcement> announcements = announcementRepository.findAll(pageable);
        announcements.forEach(announcement -> list.add(announcementMapper.toAnnouncementResponse(announcement)));
        return PageResponse.<List<AnnouncementResponse>>builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .pageTotal(announcements.getTotalPages())
                .item(list)
                .build();
    }

    @Override
    public AnnouncementResponse update(String id, AnnouncementRequest request) {
        var announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ANNOUNCEMENT_NOT_EXISTED));
        announcementMapper.updateAnnouncement(announcement, request);
        return announcementMapper.toAnnouncementResponse(announcementRepository.save(announcement));
    }

    @Override
    public void delete(String id) {
        announcementRepository.deleteById(id);
    }

    @Override
    public PageResponse<List<AnnouncementResponse>> advanceSearchWithCriteria(int pageNo, int pageSize, String sortBy, String... search) {
        return searchRepository.advanceSearchWithCriteria(pageNo, pageSize, sortBy, search);
    }

    @Override
    public PageResponse<List<AnnouncementResponse>> advanceSearchWithSpecification(Pageable pageable, String[] announcement) {
        Page<Announcement> announcements = null;
        List<Announcement> list = new ArrayList<>();

        if (announcement != null) {
//            Specification<Announcement> spec = AnnouncementSpec.hasTitle("T");
//            Specification<Announcement> contentSpec = AnnouncementSpec.hasContent("K");
//            Specification<Announcement> finalSpec = spec.and(contentSpec);
            AnnouncementSpecificationBuilder builder = new AnnouncementSpecificationBuilder();
            for (String s: announcement) {
                Pattern pattern = Pattern.compile("(\\w+?)([:<>~!])(\\p{Punct})(.*)(\\p{Punct})");
                Matcher matcher = pattern.matcher(s);
                if (matcher.find())
                    builder.with(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5));
            }
            list = announcementRepository.findAll(builder.build());
            List<AnnouncementResponse> tempList = new ArrayList<>();
            list.stream().forEach(a -> tempList.add(announcementMapper.toAnnouncementResponse(a)));
            return PageResponse.<List<AnnouncementResponse>>builder()
                    .pageNo(pageable.getPageNumber())
                    .pageSize(pageable.getPageSize())
                    .pageTotal(10)
                    .item(tempList)
                    .build();
        } else {
            announcements = announcementRepository.findAll(pageable);
        }
        List<AnnouncementResponse> tempList = new ArrayList<>();
        announcements.stream().forEach(a -> tempList.add(announcementMapper.toAnnouncementResponse(a)));
        return PageResponse.<List<AnnouncementResponse>>builder()
                .pageNo(pageable.getPageNumber())
                .pageSize(pageable.getPageSize())
                .pageTotal(announcements.getTotalPages())
                .item(tempList)
                .build();
    }


}
