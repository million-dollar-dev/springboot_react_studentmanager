package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.PermissionRequest;
import com.tuandang.student_manager.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getPermissions();
    void deletePermission(String name);
}
