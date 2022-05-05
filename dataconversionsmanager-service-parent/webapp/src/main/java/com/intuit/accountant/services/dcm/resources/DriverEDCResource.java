package com.intuit.accountant.services.dcm.resources;

import com.intuit.accountant.services.common.annotation.UserRealmTicketFilter;
import com.intuit.accountant.services.dcm.services.DriverEDCService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/job")
@Api(value = "/job", description = "Endpoint provides Driver XML and EDC Key to the desktop app")
@UserRealmTicketFilter
public class DriverEDCResource {

	private static final Logger logger = LoggerFactory.getLogger(DriverEDCResource.class);

    @Autowired
	private DriverEDCService driverEDCService;

	@GET
	@Path("/driverxml")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Bad Request: Payload is not correct."),
			@ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
			@ApiResponse(code = 200, message = "Successfully processed the request.")
	})
	public Response getDriverXML(
			@QueryParam("Product") final String Product	,
			@QueryParam("CustomerNumber") final String CustomerNumber,
			@QueryParam("Batch") final String Batch,
			@QueryParam("Renumbering") final String Renumbering,
			@QueryParam("Competitor") final String Competitor,
			@QueryParam("Email") @Encoded final String Email,
			@QueryParam("FirstName") final String FirstName,
			@QueryParam("LastName") final String LastName,
			@QueryParam("Phone") final String Phone,
			@QueryParam("TaxYear") final String TaxYear,
			@QueryParam("SubmitDate") final String SubmitDate
	)
	{

		String driverStr = null;
		try{
			//TODO - Figure out encoding
			String email = UriUtils.decode(Email, "UTF-8");
			driverStr = driverEDCService.getDriverXml(Product, CustomerNumber, Batch, Renumbering, Competitor, email,  FirstName,
					LastName,  Phone,  TaxYear, SubmitDate );

		}catch(Exception ex){
			logger.error(ex.getMessage());
		}

		return  Response.ok(driverStr).header("content-disposition", "attachment; filename = Driver.xml").build();
	}

	@GET
	@Path("/edckey")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
			@ApiResponse(code = 200, message = "Successfully processed the request.")
	})
	public Response getEdcKey(){

		return  Response.ok(driverEDCService.getEdckey()).header("content-disposition", "attachment; filename = edc.key").build();
	}



}
