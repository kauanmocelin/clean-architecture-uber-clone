package dev.kauanmocelin;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/api")
public class SignupApi {

    SignupApplication signupApplication;

    @Inject
    public SignupApi(SignupApplication signupApplication) {
        this.signupApplication = signupApplication;
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(SignupRequestInputDTO signupRequestInputDTO) {
        try {
            var result = signupApplication.signup(signupRequestInputDTO);
            return Response.ok().entity(result).build();
        } catch(RuntimeException rte) {
            String messageError = Json.createObjectBuilder()
                    .add("message", rte.getMessage())
                    .build()
                    .toString();
            return Response.status(422).entity(messageError).build();
        }
    }

    @GET
    @Path("/accounts/{accountId}")
    public Response getAccounts(@PathParam("accountId") final UUID accountId) {
        var account = signupApplication.getAccount(accountId);
        return Response.ok().entity(account).build();
    }
}