package com.pulse.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;

import com.pulse.config.EntityManagerFactoryProvider;
import com.pulse.studentits.dao.StudentAccountDAO;
import com.pulse.studentits.entity.StudentAccount;

@Path("/student-its")
@Produces(MediaType.APPLICATION_JSON)
public class StudentItsApi {

    private final EntityManagerFactory emf = EntityManagerFactoryProvider.getFactory("studentitsPU");

    @GET
    @Path("/students/{studentId}/personal-no")
    public Response getPersonalNumber(@PathParam("studentId") String studentId) {
        EntityManager em = emf.createEntityManager();
        try {
            StudentAccountDAO dao = new StudentAccountDAO(em);
            StudentAccount account = dao.findByStudentId(studentId);
            if (account == null) {
                Map<String, String> err = new HashMap<>();
                err.put("error", "Student not found");
                return Response.status(Response.Status.NOT_FOUND).entity(err).build();
            }
            Map<String, String> result = new HashMap<>();
            result.put("studentId", account.getStudentId());
            result.put("personalNo", account.getPersonalNo());
            return Response.ok(result).build();
        } catch (Exception e) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Internal server error");
            return Response.serverError().entity(err).build();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}