package com.pulse.api;

import com.pulse.api.dto.LadokRegisterResultRequest;
import com.pulse.config.EntityManagerFactoryProvider;
import com.pulse.dao.LadokResultDAO;
import com.pulse.entity.LadokResultEntity;
import com.pulse.service.LadokResultService;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/ladok")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LadokResultController {

    private final LadokResultService resultService;

    public LadokResultController() {
        EntityManager em = EntityManagerFactoryProvider
                .getFactory("ladokPU")
                .createEntityManager();

        LadokResultDAO dao = new LadokResultDAO(em);
        this.resultService = new LadokResultService(dao);
    }

    // Assignment spec: reg_Resultat
    @POST
    @Path("/results")
    public Response registerResult(LadokRegisterResultRequest req) {
        try {
            LadokResultEntity saved = resultService.registerResult(
                    req.getPersonalNo(),
                    req.getCourseId(),
                    req.getModuleCode(),
                    req.getExamDate(),
                    req.getGrade()
            );

            return Response.status(Response.Status.CREATED).entity(saved).build();

        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();

        } catch (Exception e) {
            return Response.serverError()
                    .entity(Map.of("error", "Internal server error"))
                    .build();
        }
    }
}