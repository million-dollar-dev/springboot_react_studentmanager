package com.tuandang.student_manager.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    int pageNo;
    int pageSize;
    int pageTotal;
    T item;
}
