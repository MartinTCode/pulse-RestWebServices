package com.pulse.service;

import com.pulse.dao.EpokModuleDAO;
import com.pulse.entity.EpokModuleEntity;

import java.util.List;

public class EpokModuleService {

    private final EpokModuleDAO moduleDAO;

    public EpokModuleService(EpokModuleDAO moduleDAO) {
        this.moduleDAO = moduleDAO;
    }

    public List<EpokModuleEntity> getModulesByCourseId(String courseId) {
        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("Course ID cannot be empty.");
        }

        return moduleDAO.findModulesByCourseId(courseId);
    }
}
