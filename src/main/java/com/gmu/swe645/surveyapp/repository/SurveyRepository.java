package com.gmu.swe645.surveyapp.repository;

import com.gmu.swe645.surveyapp.model.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
