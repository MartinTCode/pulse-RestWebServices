package com.pulse.service;

import com.pulse.dao.EpokModuleDAO;
import com.pulse.entity.EpokModuleEntity;

import java.util.List;

public class EpokModuleService {

    private final EpokModuleDAO moduleDAO;

    /**
     * Service that exposes read operations for modules belonging to a course.
     *
     * Validation is performed at the service layer and persistence is delegated
     * to the injected DAO. This keeps the service easy to unit-test by
     * supplying a stub/mock DAO.
     *
     * Contract:
     * - Inputs: courseId (non-null, non-blank)
     * - Outputs: List of EpokModuleEntity (may be empty)
     * - Errors: IllegalArgumentException for invalid input
     */
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
