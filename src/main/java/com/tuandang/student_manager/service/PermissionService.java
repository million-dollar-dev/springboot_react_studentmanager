package com.tuandang.student_manager.service;

import com.tuandang.student_manager.dto.request.PermissionRequest;
import com.tuandang.student_manager.dto.response.PermissionResponse;
import com.tuandang.student_manager.mapper.PermissionMapper;
import com.tuandang.student_manager.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService implements IPermissionService{
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    @Override
    public PermissionResponse create(PermissionRequest request) {
        var permission = permissionMapper.toPermission(request);
        permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getPermissions() {
        List<PermissionResponse> list = new ArrayList<>();
        permissionRepository.findAll().forEach(permission -> list.add(permissionMapper.toPermissionResponse(permission)));
        return list;
    }

    @Override
    public void deletePermission(String name) {
        permissionRepository.deleteById(name);
    }
}
