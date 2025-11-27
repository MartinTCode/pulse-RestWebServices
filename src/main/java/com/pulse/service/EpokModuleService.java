package com.pulse.service;

import com.pulse.dao.EpokModuleDAO;
import com.pulse.entity.EpokModuleEntity;

import java.util.List;

/**
 * Service class for managing Epok modules. Handles business logic related to
 * retrieving modules associated with courses.
 */
public class EpokModuleService {

    private final EpokModuleDAO moduleDAO;

    /**
     * Constructor that initializes the EpokModuleService with the given EpokModuleDAO.
     * @param moduleDAO EpokModuleDAO instance
     */
    public EpokModuleService(EpokModuleDAO moduleDAO) {
        this.moduleDAO = moduleDAO;
    }

    /**
     * Retrieves modules associated with a specific course ID.
     * @param courseId The ID of the course whose modules to retrieve
     * @return List of EpokModuleEntity instances associated with the given course ID
     * @throws IllegalArgumentException if courseId is null or empty
     */
    public List<EpokModuleEntity> getModulesByCourseId(String courseId) {
        if (courseId == null || courseId.isBlank()) {
            throw new IllegalArgumentException("Course ID cannot be empty.");
        }

        return moduleDAO.findModulesByCourseId(courseId);
    }
}
