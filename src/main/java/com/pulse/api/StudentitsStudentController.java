package com.pulse.api;

import com.pulse.config.EntityManagerFactoryProvider;
import com.pulse.dao.StudentitsStudentAccountDAO;
import com.pulse.entity.StudentitsStudentAccountEntity;
import com.pulse.service.StudentitsStudentService;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("/student-its")
@Produces(MediaType.APPLICATION_JSON)
public class StudentitsStudentController {

    private final StudentitsStudentService studentService;

    public StudentitsStudentController() {
        EntityManager em = EntityManagerFactoryProvider
                .getFactory("studentitsPU")
                .createEntityManager();

        StudentitsStudentAccountDAO dao = new StudentitsStudentAccountDAO(em);
        this.studentService = new StudentitsStudentService(dao);
    }

    @GET
    @Path("/students/{studentId}/personal-no")
    public Response getPersonalNumber(@PathParam("studentId") String studentId) {
        try {
            String personalNo = studentService.getPersonalNumber(studentId);

            Map<String, String> result = new HashMap<>();
            result.put("studentId", studentId);
            result.put("personalNo", personalNo);

            return Response.ok(result).build();

        } catch (IllegalArgumentException e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(err).build();

        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Internal server error");
            return Response.serverError().entity(err).build();
        }
    }

    @GET
    @Path("/students/{studentId}")
    public Response getStudent(@PathParam("studentId") String studentId) {
        try {
            StudentitsStudentAccountEntity account = studentService.getStudentById(studentId);
            if (account == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Student not found"))
                        .build();
            }
            return Response.ok(account).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity(Map.of("error", "Internal server error"))
                    .build();
        }
    }
}

