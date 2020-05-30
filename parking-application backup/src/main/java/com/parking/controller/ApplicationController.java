package com.parking.controller;

import static com.parking.controller.utils.ResponseCreator.fromList;

import com.parking.exceptions.*;
import com.parking.dto.ApplicationDTO;
import com.parking.service.ApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/applications")
@Api("Application Controller")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping()
    @ApiOperation("create application")
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationDTO applicationDTO) {
        applicationDTO = applicationService.createApplication(applicationDTO);
        if (applicationDTO == null)
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        return new ResponseEntity<>(applicationDTO, HttpStatus.CREATED);
    }

    @GetMapping()
    @ApiOperation("get all applications")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications() {
        return fromList(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    @ApiOperation("get application by id")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long id) {
        return applicationService.getApplicationById(id)
                .map(application -> new ResponseEntity<>(application, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException(
                        String.format("Application with id %d cannot be found", id)
                ));
    }

    @PutMapping("/{id}")
    @ApiOperation("update application")
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long id,
                                                            @RequestBody ApplicationDTO applicationDTO) {
        return new ResponseEntity<>(applicationService.updateApplication(id, applicationDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete application")
    public ResponseEntity<ApplicationDTO> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    @ApiOperation("delete all applications")
    public ResponseEntity<ApplicationDTO> deleteAllApplications() {
        applicationService.deleteAllApplications();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
