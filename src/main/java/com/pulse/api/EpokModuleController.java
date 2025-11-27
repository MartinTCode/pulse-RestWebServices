package com.pulse.api;

import com.pulse.service.EpokModuleService;
import com.pulse.api.dto.EpokModuleDTO;
import com.pulse.config.EntityManagerFactoryProvider;
import com.pulse.dao.EpokModuleDAO;
import com.pulse.entity.EpokModuleEntity;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
/**
 * Controller for handling Epok module-related API requests.
 */
public class EpokModuleController {

    private final EpokModuleService moduleService;

    /**
     * Constructor initializing the EpokModuleService with the appropriate DAO.
     */
    public EpokModuleController() {

        EntityManager em = EntityManagerFactoryProvider
                .getFactory("epokPU")
                .createEntityManager();
        EpokModuleDAO moduleDAO = new EpokModuleDAO(em);
        this.moduleService = new EpokModuleService(moduleDAO);
    }

    /**
     * Gets modules by course ID and returns them as DTOs.
     * @param courseId The chosen ID of the course.
     * @return Response containing a list of Epok Module Data Transfer Objects.
     * @throws Exception if an error occurs during retrieval.
     */
    @GET
    @Path("/{courseId}")
    public Response getModulesByCourseId(@PathParam("courseId") String courseId) {
        try {
            List<EpokModuleEntity> modules = moduleService.getModulesByCourseId(courseId);
            
            List<EpokModuleDTO> dtoList = modules.stream()
                    .map(m -> new EpokModuleDTO(
                            m.getModuleId(),
                            m.getModuleCode(),
                            m.getModuleName(),
                            m.getCourse().getCourseId(),
                            null
                    ))
                    .toList();

            return Response.ok(dtoList).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}