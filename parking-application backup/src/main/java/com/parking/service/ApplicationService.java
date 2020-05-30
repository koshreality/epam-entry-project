package com.parking.service;

import com.parking.dto.ApplicationDTO;
import com.parking.dto.UserDTO;
import com.parking.exceptions.*;
import com.parking.model.*;
import com.parking.repository.ApplicationRepository;
import com.parking.repository.OfficeRepository;
import com.parking.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService extends AbstractService {

    private final ApplicationRepository applicationRepository;
    private final OfficeRepository officeRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              UserRepository userRepository,
                              OfficeRepository officeRepository) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.officeRepository = officeRepository;
    }

    public ApplicationDTO createApplication(ApplicationDTO applicationDTO) {
        User user = userRepository.findByUsername(applicationDTO.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found: " + applicationDTO.getUsername()));

        UserDTO currentUser = getCurrentUser();
        if (!user.getUsername().equals(currentUser.getUsername())) {
            if (currentUser.getRole() == User.Role.USER) {
                throw new UnauthorizedException("User with role 'USER' is allowed to create applications only for himself");
            }
        }

        Office office = officeRepository.findByTitle(applicationDTO.getOfficeTitle())
                .orElseThrow(() -> new NotFoundException("Office not found: " + applicationDTO.getOfficeTitle()));

        Application application = new Application(office, user, Application.Status.OPEN,
                userRepository.findByUsername(currentUser.getUsername())
                        .orElseThrow(() -> new NotFoundException("User not found: " + currentUser.getUsername())));

        return new ApplicationDTO(applicationRepository.save(application));
    }

    @Transactional(readOnly = true)
    public List<ApplicationDTO> getAllApplications() {

        return applicationRepository.findAll().stream()
                .map(ApplicationDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ApplicationDTO> getApplicationById(Long id) {

        UserDTO currentUser = getCurrentUser();

        return applicationRepository.findById(id)
                .filter(app -> app.getUser().getUsername().equals(currentUser.getUsername()) ||
                                currentUser.getRole() != User.Role.USER)
                .map(ApplicationDTO::new);
    }

    public ApplicationDTO updateApplication(Long id, ApplicationDTO applicationDTO) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Application not found: " + id));
        UserDTO currentUser = getCurrentUser();

        if (applicationDTO.getOfficeTitle() != null) {
            application.setOffice(officeRepository.findByTitle(applicationDTO.getOfficeTitle())
                    .orElseThrow(() -> new NotFoundException("Office not found: " + applicationDTO.getOfficeTitle())));
        }
        if (applicationDTO.getUsername() != null) {
            application.setUser(userRepository.findByUsername(applicationDTO.getUsername())
                    .orElseThrow(() -> new NotFoundException("User not found: " + currentUser.getUsername())));
        }
        if (applicationDTO.getStatus() != null) {
            application.setStatus(applicationDTO.getStatus());
        }

        application.setUpdatedBy(userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new NotFoundException("User not found: " + currentUser.getUsername())));

        return new ApplicationDTO(applicationRepository.save(application));
    }

    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    public void deleteAllApplications() {
        applicationRepository.deleteAll();
    }
}
