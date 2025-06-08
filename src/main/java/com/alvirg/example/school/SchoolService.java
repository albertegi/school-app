package com.alvirg.example.school;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final SchoolMapper schoolMapper;

    public SchoolService(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    public SchoolDto saveSchool(SchoolDto schoolDto){

        var school = schoolMapper.toSchool(schoolDto);
        School saveSchool = schoolRepository.save(school);
        return schoolMapper.toSchoolDto(saveSchool);

    }

    public List<SchoolDto> findAllSchool(){

        return schoolRepository.findAll()
                .stream()
                .map(schoolMapper::toSchoolDto)
                .collect(Collectors.toList());
    }
}
