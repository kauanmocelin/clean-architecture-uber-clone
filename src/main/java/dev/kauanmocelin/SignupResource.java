package dev.kauanmocelin;

import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/api")
public class SignupResource {

    @Inject
    DataSource datasource;

    @POST
	@Path("/signup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response signup(SignupRequestInput signupRequestInput) throws SQLException {
		var result = "";

		List<SignupDatabase> signupsByEmailDatabase = new ArrayList<>();
		try (Connection con = datasource.getConnection();
			PreparedStatement ps = con.prepareStatement("select * from uber_clone.account where email = ?");) {
			ps.setString(1, signupRequestInput.email());
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					signupsByEmailDatabase.add(new SignupDatabase(
							rs.getString("account_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("cpf"),
							rs.getString("car_plate"),
							rs.getBoolean("is_passenger"),
							rs.getBoolean("is_driver")
					));
				}
			}
			if(signupsByEmailDatabase.isEmpty()) {
				String id = UUID.randomUUID().toString();
				if(signupRequestInput.name().matches("[a-zA-Z]+\\s[a-zA-Z]+")) {
					if(signupRequestInput.email().matches("^(.+)@(.+)$")) {
						if(new ValidateCpf().validate(signupRequestInput.cpf())) {
							if(signupRequestInput.is_driver()){
								if(signupRequestInput.car_plate().matches("[A-Z]{3}[0-9]{4}")) {
									final PreparedStatement insertStatement = con.prepareStatement("insert into uber_clone.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) values (?, ?, ?, ?, ?, ?, ?)");
									insertStatement.setObject(1, id, java.sql.Types.OTHER);
									insertStatement.setString(2, signupRequestInput.name());
									insertStatement.setString(3, signupRequestInput.email());
									insertStatement.setString(4, signupRequestInput.cpf());
									insertStatement.setString(5, signupRequestInput.car_plate());
									insertStatement.setBoolean(6, signupRequestInput.is_passenger());
									insertStatement.setBoolean(7, signupRequestInput.is_driver());
									insertStatement.executeUpdate();
									final JsonObject obj = Json.createObjectBuilder()
											.add("accountId", id)
											.build();
									result = obj.toString();
								} else {
									result = String.valueOf(-5);
								}
							} else {
								final PreparedStatement insertStatement = con.prepareStatement("insert into uber_clone.account (account_id, name, email, cpf, car_plate, is_passenger, is_driver) values (?, ?, ?, ?, ?, ?, ?)");
								insertStatement.setObject(1, id, java.sql.Types.OTHER);
								insertStatement.setString(2, signupRequestInput.name());
								insertStatement.setString(3, signupRequestInput.email());
								insertStatement.setString(4, signupRequestInput.cpf());
								insertStatement.setString(5, signupRequestInput.car_plate());
								insertStatement.setBoolean(6, signupRequestInput.is_passenger());
								insertStatement.setBoolean(7, signupRequestInput.is_driver());
								insertStatement.executeUpdate();
								final JsonObject obj = Json.createObjectBuilder()
										.add("accountId", id)
										.build();
								result = obj.toString();
							}
						} else {
							// invalid cpf
							result = String.valueOf(-1);
						}
					} else {
						// invalid email
						result = String.valueOf(-2);
					}
				} else {
					// invalid name
					result = String.valueOf(-3);
				}
			} else {
				// already exists
				result = String.valueOf(-4);
			}
		}
		if(result.matches("-?\\d+")) {
			return Response.status(422).entity(result).build();
		} else {
			return Response.ok().entity(result).build();
		}
    }

	@GET
	@Path("/accounts/{accountId}")
	public Response getAccounts(@PathParam("accountId") final UUID accountId) throws SQLException {
		SignupDatabase signupDatabase = null;
		try (Connection con = datasource.getConnection()){
			PreparedStatement ps = con.prepareStatement("select * from uber_clone.account where account_id = ?");
			ps.setObject(1, accountId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					signupDatabase = new SignupDatabase(
							rs.getString("account_id"),
							rs.getString("name"),
							rs.getString("email"),
							rs.getString("cpf"),
							rs.getString("car_plate"),
							rs.getBoolean("is_passenger"),
							rs.getBoolean("is_driver")
					);
				}
			}
		}
		return Response.ok().entity(signupDatabase).build();
    }
}