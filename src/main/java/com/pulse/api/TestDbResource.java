package com.pulse.api;

import com.pulse.dao.TestDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/dbtest")

public class TestDbResource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return new TestDAO().testDatabase();

    }
}
