package com.parking.controller;

import static com.parking.controller.utils.ResponseCreator.fromList;

import com.parking.dto.OfficeDTO;
import com.parking.exceptions.NotFoundException;
import com.parking.service.OfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/offices")
@Api("Office controller")
public class OfficeController {

    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping()
    @ApiOperation("create office")
    public ResponseEntity<OfficeDTO> createOffice(@RequestBody OfficeDTO officeDTO) {
        return new ResponseEntity<>(officeService.createOffice(officeDTO), HttpStatus.CREATED);
    }

    @GetMapping(params = "title")
    @ApiOperation("Get an office by its title or all offices if the title is not provided")
    public ResponseEntity<?> getAllOfficesOrOfficeByTitle(@RequestParam(required = false) String title) {
        return Strings.isBlank(title)
                ? fromList(officeService.getAllOffices())
                : officeService.getOfficeByTitle(title)
                .map(office -> new ResponseEntity<>(office, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException(
                        String.format("Office with title %s cannot be found", title)
                ));
    }

    @GetMapping("/{id}")
    @ApiOperation("get office by id")
    public ResponseEntity<OfficeDTO> getOfficeById(@PathVariable Long id) {
        return officeService.getOfficeById(id)
                .map(office -> new ResponseEntity<>(office, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException(
                        String.format("Office with id %d cannot be found", id)
                ));
    }

    @PutMapping("/{id}")
    @ApiOperation("update office")
    public ResponseEntity<OfficeDTO> updateOffice(@PathVariable Long id, @RequestBody OfficeDTO updateOfficeData) {
        return officeService.updateOffice(id, updateOfficeData)
                .map(updatedOffice -> new ResponseEntity<>(updatedOffice, HttpStatus.OK))
                .orElseThrow(() -> new NotFoundException(
                        String.format("Office with id %d cannot be found", id)
                ));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("delete office")
    public ResponseEntity<OfficeDTO> deleteOffice(@PathVariable Long id) {
        officeService.deleteOffice(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping()
    @ApiOperation("delete all offices")
    public ResponseEntity<OfficeDTO> deleteAllOffices() {
        officeService.deleteAllOffices();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
