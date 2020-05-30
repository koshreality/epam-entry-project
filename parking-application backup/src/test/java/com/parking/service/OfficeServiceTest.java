package com.parking.service;

import com.parking.dto.OfficeDTO;
import com.parking.repository.OfficeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.parking.model.Office;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class OfficeServiceTest {

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private OfficeService officeService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public static List<Office> offices = Arrays.asList(
            constructOffice(1, "title1"),
            constructOffice(2, "title2")
    );

    static Office constructOffice(long id, String title) {
        Office office = new Office();
        office.setId(id);
        office.setTitle(title);
        return office;
    }

    @Test
    public void GetAllOffices() {
        when(officeRepository.findAll()).thenReturn(offices);
        List<OfficeDTO> officeList = officeService.getAllOffices();

        Assert.assertArrayEquals(officeList.toArray(), offices.stream()
                .map(OfficeDTO::new).toArray());
    }

    @Test
    public void GetOfficeByTitle() {
        when(officeRepository.findByTitle("title1")).thenReturn(Optional.of(offices.get(0)));
        OfficeDTO officeDTO = officeService.getOfficeByTitle("title1").get();

        Assert.assertEquals(officeDTO.getTitle(), offices.get(0).getTitle());
        Assert.assertEquals(officeDTO.getId().longValue(), 1L);
    }

    @Test
    public void GetOfficeById() {
        when(officeRepository.findById(1L)).thenReturn(Optional.of(offices.get(0)));
        OfficeDTO officeDTO = officeService.getOfficeById(offices.get(0).getId()).get();

        Assert.assertEquals(officeDTO.getTitle(), offices.get(0).getTitle());
        Assert.assertEquals(officeDTO.getId().longValue(), 1L);
    }

    @Test
    public void UpdateOffice() {
        OfficeDTO officeDTO = new OfficeDTO();
        officeDTO.setTitle("title6");

        when(officeRepository.save(offices.get(0))).thenReturn(offices.get(0));
        when(officeRepository.findById(1L)).thenReturn(Optional.of(offices.get(0)));

        OfficeDTO updateOfficeData = officeService.updateOffice(1L, officeDTO).get();

        Assert.assertEquals(updateOfficeData.getTitle(), "title6");
        Assert.assertEquals(updateOfficeData.getId().longValue(), 1L);
    }

}