package com.pulse.ladok.api;

import com.pulse.config.EntityManagerFactoryProvider;
import com.pulse.ladok.api.dto.RegisterResultRequest;
import com.pulse.ladok.dao.ResultDAO;
import com.pulse.ladok.entity.Result;
import com.pulse.ladok.service.ResultService;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/ladok")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResultController {

    private final ResultService resultService;

    public ResultController() {
        EntityManager em = EntityManagerFactoryProvider
                .getFactory("ladokPU")
                .createEntityManager();

        ResultDAO dao = new ResultDAO(em);
        this.resultService = new ResultService(dao);
    }

    // Assignment spec: reg_Resultat
    @POST
    @Path("/results")
    public Response registerResult(RegisterResultRequest req) {
        try {
            Result saved = resultService.registerResult(
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