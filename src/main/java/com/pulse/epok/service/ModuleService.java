package com.pulse.epok.service;

import com.pulse.epok.dao.ModuleDAO;
import com.pulse.epok.entity.Module;

import java.util.List;

public class ModuleService {

    private final ModuleDAO moduleDAO;

    public ModuleService(ModuleDAO moduleDAO) {
        this.moduleDAO = moduleDAO;
    }

    public List<Module> getModulesByCourseId(String courseId) {
        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("Course ID cannot be empty.");
        }

        return moduleDAO.findModulesByCourseId(courseId);
    }
}
