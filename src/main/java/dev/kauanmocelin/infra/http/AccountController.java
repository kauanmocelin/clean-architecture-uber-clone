package dev.kauanmocelin.infra.http;

import dev.kauanmocelin.application.usecase.GetAccount;
import dev.kauanmocelin.application.usecase.Signup;
import dev.kauanmocelin.dto.SignupRequestInputDTO;
import dev.kauanmocelin.infra.gateway.MailerGateway;
import jakarta.json.Json;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class AccountController {

    Signup signup;
    GetAccount getAccount;
    MailerGateway mailerGateway;

    public AccountController(Signup signup, MailerGateway mailerGateway, GetAccount getAccount) {
        this.signup = signup;
        this.mailerGateway = mailerGateway;
        this.getAccount = getAccount;
    }

    @POST
    @Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(SignupRequestInputDTO signupRequestInputDTO) {
        try {
            var result = signup.execute(signupRequestInputDTO);
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
    public Response getAccounts(@PathParam("accountId") final String accountId) {
        var account = getAccount.execute(accountId);
        return Response.ok().entity(account).build();
    }
}