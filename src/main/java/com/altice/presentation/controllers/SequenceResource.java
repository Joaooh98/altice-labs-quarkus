package com.altice.presentation.controllers;

import java.util.Map;
import com.altice.domain.modal.SequenceValue;
import com.altice.service.SequenceService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/labseq")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "sequence")
public class SequenceResource {
    
    @Inject
    SequenceService sequenceService;
    
    @GET
    @Path("/{n}")
    @Operation(
        summary = "Calculates the value of the labseq sequence",
        description = "Returns the sequence defined as follows:\n" + //
                        "n=0 => l(0) = 0\n" + //
                        "n=1 => l(1) = 1\n" + //
                        "n=2 => l(2) = 0\n" + //
                        "n=3 => l(3) = 1\n" + //
                        "n>3 => l(n) = l(n-4) + l(n-3)"
    )
    @APIResponse(
        responseCode = "200",
        description = "Successful operation",
        content = @Content(mediaType = MediaType.APPLICATION_JSON, 
                          schema = @Schema(implementation = SequenceValue.class))
    )
    @APIResponse(
        responseCode = "400",
        description = "Invalid index"
    )
    public Response getValue(
            @Parameter(description = "Sequence index (must be non-negative)", required = true)
            @PathParam("n") int n) {
        try {
            SequenceValue result = sequenceService.getSequenceValue(n);
            return Response.ok(result).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }
}