package com.gmu.swe645.surveyapp.service;

import com.gmu.swe645.surveyapp.model.Survey;
import com.gmu.swe645.surveyapp.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    public Survey saveSurvey(Survey survey) {
        return surveyRepository.save(survey);
    }

    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    public Survey updateSurvey(Long id, Survey updatedSurvey) {
        if (surveyRepository.existsById(id)) {
            updatedSurvey.setId(id);
            return surveyRepository.save(updatedSurvey);
        }
        return null;
    }

    public boolean deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
