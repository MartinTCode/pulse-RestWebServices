package com.pulse.api;

import com.pulse.api.dto.StudentItsDTO;
import com.pulse.config.EntityManagerFactoryProvider;
import com.pulse.dao.StudentitsStudentAccountDAO;
import com.pulse.entity.StudentitsStudentAccountEntity;
import com.pulse.service.StudentitsStudentService;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    @Path("/students/{studentId}")
    public Response getStudent(@PathParam("studentId") String studentId) {
        try {
            StudentitsStudentAccountEntity account = studentService.getStudentById(studentId);
            
            if (account == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(Map.of("error", "Student not found"))
                        .build();
            }
            
            // Convert entity to DTO
            StudentItsDTO dto = new StudentItsDTO(account.getStudentId(), account.getPersonalNo());
            return Response.ok(dto).build();
            
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

