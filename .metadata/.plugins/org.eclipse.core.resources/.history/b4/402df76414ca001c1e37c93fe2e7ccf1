package com.intuit.accountant.services.dcm.resources;

import com.intuit.accountant.services.common.annotation.UserRealmTicketFilter;
import com.intuit.accountant.services.dcm.services.ApplicationConfigurationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/configurations")
@Api(value = "/configurations", description = "Endpoint provides desktop configurations")
@UserRealmTicketFilter
public class ApplicationConfigurationResource {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigurationResource.class);

    @Autowired
	private ApplicationConfigurationService applicationConfigurationService;

	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
			@ApiResponse(code = 200, message = "Successfully processed the request.")
	})
	public Response getConfigurations(@QueryParam("PRODUCT_NAME") final String productName , @QueryParam("SOURCE") final  String source){

		if(StringUtils.isNotBlank(productName) && StringUtils.isBlank(source)) {
			return Response.ok().type(MediaType.APPLICATION_XML).entity(applicationConfigurationService.getDesktopConfiguration(productName)).build();
		}

		else if(StringUtils.isNotBlank(productName) && StringUtils.isNoneBlank(source)){
			return Response.ok().type(MediaType.APPLICATION_JSON).entity(applicationConfigurationService.getDesktopConfigurationAsJson(productName,source)).build();
		}

		return Response.ok().type(MediaType.APPLICATION_XML).entity(applicationConfigurationService.getDesktopConfiguration()).build();
	}

	@GET
	@Path("/externalsystems")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
			@ApiResponse(code = 200, message = "Successfully processed the request.")
	})
	public Response getExternalSystemsConfigurations(){

		return Response.ok().type(MediaType.APPLICATION_JSON).entity(applicationConfigurationService.getExternalSystemConfig()).build();
	}

	@GET
	@Path("/dropdowns")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
			@ApiResponse(code = 200, message = "Successfully processed the request.")
	})
	public Response getDesktopDropDowns(){

		return Response.ok().type(MediaType.APPLICATION_JSON).entity(applicationConfigurationService.getDesktopDropDowns()).build();
	}



}
