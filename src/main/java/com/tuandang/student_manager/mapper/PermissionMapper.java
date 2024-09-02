package com.tuandang.student_manager.mapper;

import com.tuandang.student_manager.dto.request.PermissionRequest;
import com.tuandang.student_manager.dto.response.PermissionResponse;
import com.tuandang.student_manager.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);

}
