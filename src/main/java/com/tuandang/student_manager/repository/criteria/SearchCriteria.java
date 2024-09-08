package com.tuandang.student_manager.repository.criteria;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SearchCriteria {
    String key; // tên các cột(vd: id, name, email,...)
    String operation;   // phương thức so sánh(vd: =, like, greater than, ...)
    Object value;   // kiểu dữ liệu
}
