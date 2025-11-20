package com.pulse.epok.api;

import com.pulse.epok.dao.ModuleDAO;
import com.pulse.epok.entity.Module;
import com.pulse.epok.service.ModuleService;
import com.pulse.config.EntityManagerFactoryProvider;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/modules")
@Produces(MediaType.APPLICATION_JSON)
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController() {

        EntityManager em = EntityManagerFactoryProvider
                .getFactory("epokPU")
                .createEntityManager();

        ModuleDAO moduleDAO = new ModuleDAO(em);
        this.moduleService = new ModuleService(moduleDAO);
    }

    @GET
    @Path("/{courseId}")
    public Response getModulesByCourseId(@PathParam("courseId") String courseId) {
        try {
            List<Module> modules = moduleService.getModulesByCourseId(courseId);
            return Response.ok(modules).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
