package org.acme.dataservice.resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

import org.acme.dataservice.data.dataService;

import org.bson.Document;

@ApplicationScoped
@Path("/data")
public class DataServiceResource {

    @Inject
    dataService dataService;

    // Get data by city
    @GET
    @Path("/city/{cityName}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Document> getDataByCity(@PathParam("cityName") String cityName) {
        return dataService.getDataByCity(cityName);
    }

    // Get data by temperature range
    @GET
    @Path("/temperature/{minTemp}/{maxTemp}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Document> getDataByTemperatureRange(@PathParam("minTemp") double minTemp, @PathParam("maxTemp") double maxTemp) {
        return dataService.getDataByTemperatureRange(minTemp, maxTemp);
    }

    // Get latest data by city
    @GET
    @Path("/city/{cityName}/latest")
    @Produces(MediaType.APPLICATION_JSON)
    public Document getLatestDataByCity(@PathParam("cityName") String cityName) {
        return dataService.getLatestDataByCity(cityName);
    }

    // Update data by city 
    @PUT
    @Path("/city/{cityName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateDataByCity(@PathParam("cityName") String cityName, Document newData) {
        dataService.updateDataByCity(cityName, newData);
    }

    // Delete old data 
    @DELETE
    @Path("/old")
    public void deleteOldData() {
        dataService.deleteOldData();
    }
}
