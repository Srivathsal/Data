package com.intuit.accountant.services.dcm.resources;

import com.intuit.accountant.services.common.annotation.UserRealmTicketFilter;
import com.intuit.accountant.services.dcm.services.ConversionBlackoutService;
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
@Path("/blackout")
@Api(value = "/blackout", description = "Endpoint to check the blackout period of Data Conversions")
@UserRealmTicketFilter
public class ConversionBlackoutResource {
    private static final Logger logger = LoggerFactory.getLogger(ConversionBlackoutResource.class);

    @Autowired
	private ConversionBlackoutService conversionBlackoutService;


	@GET
	@Path("/")
	@Produces({MediaType.APPLICATION_JSON})
	@ApiResponses(value = {
			@ApiResponse(code = 500, message = "Server encountered an unexpected condition that prevented it from fulfilling this request"),
			@ApiResponse(code = 200, message = "Successfully processed the request.")
	})
	public Response checkBlackout(@QueryParam("CUSTOMER_ACCOUNT_NUMBER") final String can,
								  @QueryParam("PRODUCT_TAX_YEAR") final String productTaxYear,
								  @QueryParam("DC_APP_VERSION") final String dcAppVersion,
								  @QueryParam("PRODUCT_NAME") final  String productName){

		try{
			if((StringUtils.isNotBlank(productName) && conversionBlackoutService.isProtaxProduct(productName)) || StringUtils.isNotEmpty(dcAppVersion)){
				return Response.ok().type(MediaType.APPLICATION_JSON).entity(conversionBlackoutService.validateBlackout(productName,can, productTaxYear, dcAppVersion)).build();
			} else {
				return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity("Query param: DC_APP_VERSION is either null or missing").build();
			}
		}
		catch (Exception e){
			logger.error("Failed to get blackout details from server: " + e.getStackTrace());
			throw new RuntimeException(e);
		}

	}



}
