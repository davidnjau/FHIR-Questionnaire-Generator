package com.questionnaire.fhir.encounter;

import com.google.gson.JsonObject;
import com.questionnaire.fhir.DbEncounter;
import com.questionnaire.fhir.FormatterHelper;
import com.questionnaire.fhir.Results;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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

    @PostMapping(path = "create-encounter",
            produces= MediaType.APPLICATION_JSON_VALUE)
    public String createEncounter(@RequestBody DbEncounter dbEncounter){
        Results results = encounterService.createEncounter(dbEncounter);
        if (results.getStatusCode() == 200){
            return results.getDetails().toString();
        }

        return "There was an issue with the request";
//        return formatterHelper.getResponseEntity(results);
    }
}
