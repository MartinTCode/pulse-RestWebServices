package com.pulse.api;

import com.pulse.api.dto.LadokResultDTO;
import com.pulse.api.dto.LadokResponseDTO;
import com.pulse.config.EntityManagerFactoryProvider;
import com.pulse.dao.LadokResultDAO;
import com.pulse.entity.LadokResultEntity;
import com.pulse.service.LadokResultService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/ladok")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LadokResultController {

    private final EntityManagerFactory emf;

    public LadokResultController() {
        this.emf = EntityManagerFactoryProvider.getFactory("ladokPU");
    }

    // Assignment spec: reg_Resultat
    @POST
    @Path("/results")
    public Response registerResult(LadokResultDTO req) {
        EntityManager em = emf.createEntityManager();
        try {
            LadokResultDAO dao = new LadokResultDAO(em);
            LadokResultService service = new LadokResultService(dao);
            
            LadokResultEntity saved = service.registerResult(
                    req.personalNo(),
                    req.courseId(),
                    req.moduleCode(),
                    req.examDate(),
                    req.grade()
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
        } finally {
            em.close();
        }
    }

    // Batch transfer endpoint
    @POST
    @Path("/transfer")
    public Response transferResults(List<LadokResultDTO> requests) {
        EntityManager em = emf.createEntityManager();
        List<LadokResponseDTO> responses = new ArrayList<>();

        try {
            LadokResultDAO dao = new LadokResultDAO(em);
            LadokResultService service = new LadokResultService(dao);

            for (LadokResultDTO req : requests) {
                try {
                    // Service handles all validation
                    LadokResultEntity saved = service.registerResult(
                            req.personalNo(),
                            req.courseId(),
                            req.moduleCode(),
                            req.examDate(),
                            req.grade()
                    );

                    responses.add(new LadokResponseDTO(
                            req.personalNo(),
                            "SUCCESS",
                            "Resultat registrerat med ID: " + saved.getResultId()
                    ));

                } catch (IllegalArgumentException e) {
                    // Service validation failed - return user-friendly message
                    responses.add(new LadokResponseDTO(
                            req.personalNo(),
                            "FAILED",
                            e.getMessage()
                    ));

                } catch (Exception e) {
                    // Unexpected error (database, etc.)
                    responses.add(new LadokResponseDTO(
                            req.personalNo(),
                            "FAILED",
                            "Serverfel: " + e.getMessage()
                    ));
                }
            }

            return Response.ok(responses).build();
            
        } finally {
            em.close();
        }
    }
}