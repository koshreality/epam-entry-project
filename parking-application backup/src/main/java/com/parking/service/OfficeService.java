package com.parking.service;

import com.parking.model.Office;
import com.parking.dto.OfficeDTO;
import com.parking.repository.OfficeRepository;
import com.parking.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class OfficeService extends AbstractService {

    private final OfficeRepository officeRepository;

    public OfficeService(OfficeRepository officeRepository) {
        this.officeRepository = officeRepository;
    }

    public OfficeDTO createOffice(OfficeDTO officeDTO) {
        return new OfficeDTO(officeRepository.save(new Office(officeDTO)));
    }

    @Transactional(readOnly = true)
    public List<OfficeDTO> getAllOffices() {
        return officeRepository.findAll().stream()
                .map(OfficeDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<OfficeDTO> getOfficeByTitle(String title) {
        return officeRepository.findByTitle(title).map(OfficeDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<OfficeDTO> getOfficeById(Long id) {
        return officeRepository.findById(id).map(OfficeDTO::new);
    }

    public Optional<OfficeDTO> updateOffice(Long id, OfficeDTO updateOfficeData) {
        return officeRepository.findById(id).map(foundOffice -> {
            if (updateOfficeData.getTitle() != null)
                foundOffice.setTitle(updateOfficeData.getTitle());
            return new OfficeDTO(officeRepository.save(foundOffice));
        });
    }

    public void deleteOffice(Long id) {
        officeRepository.deleteById(id);
    }

    public void deleteAllOffices() {
        officeRepository.deleteAll();
    }
}
