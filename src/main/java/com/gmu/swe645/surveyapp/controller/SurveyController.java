package com.gmu.swe645.surveyapp.controller;

import com.gmu.swe645.surveyapp.model.Survey;
import com.gmu.swe645.surveyapp.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/surveys")
@CrossOrigin(origins = "*") // Allow Postman / frontend access
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @PostMapping
    public Survey submitSurvey(@RequestBody Survey survey) {
        return surveyService.saveSurvey(survey);
    }

    @GetMapping
    public List<Survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    @GetMapping("/{id}")
    public Optional<Survey> getSurvey(@PathVariable Long id) {
        return surveyService.getSurveyById(id);
    }

    @PutMapping("/{id}")
    public Survey updateSurvey(@PathVariable Long id, @RequestBody Survey survey) {
        return surveyService.updateSurvey(id, survey);
    }

    @DeleteMapping("/{id}")
    public String deleteSurvey(@PathVariable Long id) {
        boolean deleted = surveyService.deleteSurvey(id);
        return deleted ? "Survey deleted successfully." : "Survey not found.";
    }
}
