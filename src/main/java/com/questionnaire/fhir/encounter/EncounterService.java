package com.questionnaire.fhir.encounter;

import com.google.gson.JsonObject;
import com.questionnaire.fhir.DbEncounter;
import com.questionnaire.fhir.Results;

public interface EncounterService {

    Results createEncounter(DbEncounter dbEncounter);

}
