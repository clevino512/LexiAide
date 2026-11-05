package com.lexiaide.service;

import com.lexiaide.common.dto.StudentProfileDto;
import com.lexiaide.common.exception.ResourceNotFoundException;
import com.lexiaide.model.StudentProfile;
import com.lexiaide.model.User;
import com.lexiaide.repository.StudentProfileRepository;
import com.lexiaide.repository.UserRepository;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;

    public StudentProfileService(StudentProfileRepository studentProfileRepository, UserRepository userRepository) {
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public StudentProfileDto.Response create(StudentProfileDto.CreateRequest request) {
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setUser(getUser(request.userId()));
        studentProfile.setUniversity(request.university());
        studentProfile.setFieldOfStudy(request.fieldOfStudy());
        studentProfile.setStudyLevel(request.studyLevel());
        studentProfile.setBio(request.bio());
        return toResponse(studentProfileRepository.save(studentProfile));
    }

    @Transactional(readOnly = true)
    public StudentProfileDto.Response findById(UUID id) {
        return toResponse(getStudentProfile(id));
    }

    @Transactional(readOnly = true)
    public Page<StudentProfileDto.Response> findAll(Pageable pageable) {
        return studentProfileRepository.findAll(pageable).map(StudentProfileService::toResponse);
    }

    @Transactional
    public StudentProfileDto.Response update(UUID id, StudentProfileDto.UpdateRequest request) {
        StudentProfile existing = getStudentProfile(id);
        existing.setUniversity(request.university());
        existing.setFieldOfStudy(request.fieldOfStudy());
        existing.setStudyLevel(request.studyLevel());
        existing.setBio(request.bio());
        return toResponse(studentProfileRepository.save(existing));
    }

    @Transactional
    public void delete(UUID id) {
        studentProfileRepository.delete(getStudentProfile(id));
    }

    private StudentProfile getStudentProfile(UUID id) {
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profil etudiant introuvable : " + id));
    }

    private User getUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable : " + id));
    }

    private static StudentProfileDto.Response toResponse(StudentProfile studentProfile) {
        return new StudentProfileDto.Response(
                studentProfile.getId(),
                studentProfile.getUser() != null ? studentProfile.getUser().getId() : null,
                studentProfile.getUniversity(),
                studentProfile.getFieldOfStudy(),
                studentProfile.getStudyLevel(),
                studentProfile.getBio()
        );
    }
}

