package com.intuit.accountant.services.dcm.resources;

import com.intuit.accountant.services.common.annotation.UserRealmTicketFilter;
import com.intuit.accountant.services.dcm.services.AutomationCriteriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/conversionjobs")
@Api(value = "/conversionjobs", description = "Service providing the list of automatable softwares.")
@UserRealmTicketFilter
public class AutomationCriteriaResource {
    private static final Logger logger = LoggerFactory.getLogger(AutomationCriteriaResource.class);

    @Autowired
	private AutomationCriteriaService automationCriteriaService;

	@GET
	@Path("/automatablesoftwares")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
			@ApiResponse(code = 200, message = "Successfully processed the request.")
	})
	public Response getAutomatableSoftwares(@QueryParam("PRODUCT_NAME") final String productName){

		if(StringUtils.isNotBlank(productName)) {
			return Response.ok().type(MediaType.APPLICATION_JSON).entity(automationCriteriaService.getAutomatableSoftwares(productName.toLowerCase())).build();
		}

		return Response.ok().type(MediaType.APPLICATION_JSON).entity(automationCriteriaService.getAutomatableSoftwares()).build();
	}



}
