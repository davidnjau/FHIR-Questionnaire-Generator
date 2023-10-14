package com.questionnaire.fhir.encounter;

import com.questionnaire.fhir.DbEncounter;
import com.questionnaire.fhir.FormatterHelper;
import com.questionnaire.fhir.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/encounter/api/v1/")
public class EncounterController {

    private final FormatterHelper formatterHelper = new FormatterHelper();
    private final EncounterService encounterService;

    @PostMapping(path = "create-encounter")
    public ResponseEntity<?> createEncounter(@RequestBody DbEncounter dbEncounter){
        Results results = encounterService.createEncounter(dbEncounter);
        return formatterHelper.getResponseEntity(results);
    }
}
