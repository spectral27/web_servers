package spc;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.PrintWriter;
import java.io.StringWriter;

@Path("/test")
public class TestController {

    private final TestRepository repository = new TestRepository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response selectRecords() {
        return Response.status(Response.Status.OK).entity(repository.selectRecords()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertRecord(RecordObject record) {
        repository.insertRecord(record);
        return Response.status(Response.Status.OK).entity("Record added").build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRecord(@PathParam("id") String id, RecordObject record) {
        try {
            repository.updateRecord(id, record);
            return Response.status(Response.Status.OK).entity("Record updated").build();
        } catch (NullPointerException e) {
            String stackTrace = stackTrace(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(stackTrace).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRecord(@PathParam("id") String id) {
        try {
            repository.deleteRecord(id);
            return Response.status(Response.Status.OK).entity("Record deleted").build();
        } catch (NullPointerException e) {
            String stackTrace = stackTrace(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(stackTrace).build();
        }
    }

    public String stackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }

}
