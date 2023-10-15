package com.questionnaire.fhir.encounter;

import com.google.gson.JsonObject;
import com.questionnaire.fhir.DbEncounter;
import com.questionnaire.fhir.Results;
import com.questionnaire.fhir.*;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EncounterServiceImpl implements EncounterService{

    private FormatterHelper formatterHelper = new FormatterHelper();


    @Override
    public Results createEncounter(DbEncounter dbEncounter) {

        String title = dbEncounter.getTitle();
        String publisher = dbEncounter.getPublisher();
        List<DbItems> dbItemsList = dbEncounter.getItem();
        boolean isPageable = true;
        boolean allowRepeat = true;

        String versionNumber = formatterHelper.generateNumber(20);
        List<String> subjectTypeList = new ArrayList<>();
        subjectTypeList.add(Standards.Encounter.name());

        //1st Level Extension
        List<Level1Extension> level1ExtensionList = new ArrayList<>();
        ValueExpression valueExpression = new ValueExpression(
                "application/x-fhir-query",
                "Encounter",
                "encounter");
        Level1Extension level1Extension = new Level1Extension(
                "http://hl7.org/fhir/uv/sdc/StructureDefinition/sdc-questionnaire-itemExtractionContext",
                valueExpression, null, null, null);

        Level1Extension level1Extension1 = new Level1Extension(
                "http://hl7.org/fhir/uv/sdc/StructureDefinition/sdc-questionnaire-entryMode",
                null,
                "prior-edit",null, null);
        level1ExtensionList.addAll(Arrays.asList(level1Extension, level1Extension1));

        List<FhirItem> fhirItemList = new ArrayList<>();

        for (DbItems dbItems: dbItemsList){

            String versionNumber1 = versionNumber + "." + formatterHelper.generateNumber(10);

            String text = dbItems.getText();
            isPageable = Boolean.TRUE.equals(dbItems.isPageable());

            //1st Level Item

            List<FhirItem> fhirItemListLevel2 = new ArrayList<>();

            //Create ValueCodeableConcept for 1st Level item
            List<Level1Extension> extensionListItem1 = getLevel1Extensions();

            List<DbQuestionItems> dbQuestionItemsList = dbItems.getQuestionItems();
            for (DbQuestionItems dbQuestionItems: dbQuestionItemsList){

                String versionNumber2 = versionNumber1 + "." + formatterHelper.generateNumber(10);

                String question = dbQuestionItems.getQuestion();
                String type = dbQuestionItems.getType();
                allowRepeat = Boolean.TRUE.equals(dbQuestionItems.getAllowRepeat());

                //2nd Level Item

                List<ValueCoding> valueCodingListLevel2 = new ArrayList<>();
                ValueCoding valueCodingLevel2 = new ValueCoding(
                        "check-box",
                        Standards.Checkbox.name(),
                        "http://hl7.org/fhir/questionnaire-item-control"
                );
                valueCodingListLevel2.add(valueCodingLevel2);
                ValueCodeableConcept valueCodeableConcept = new ValueCodeableConcept(
                        Standards.Checkbox.name(),
                        valueCodingListLevel2);
                List<Level1Extension> level1ExtensionListLevel2 = new ArrayList<>();
                Level1Extension level1ExtensionItemLevel2 = new Level1Extension(
                        "http://hl7.org/fhir/StructureDefinition/questionnaire-itemControl",
                        null,
                        null,
                        valueCodeableConcept,
                        null
                );
                level1ExtensionListLevel2.add(level1ExtensionItemLevel2);

                List<AnswerOption> answerOptionList = new ArrayList<>();
                List<ValueCoding> valueCodingList = dbQuestionItems.getAnswerOption();
                for (ValueCoding valueCoding : valueCodingList){
                    String code = valueCoding.getCode();
                    String display = valueCoding.getDisplay();
                    String system = valueCoding.getSystem();

                    //Create the questions and choices to be displayed
                    AnswerOption answerOption = new AnswerOption(valueCoding);
                    answerOptionList.add(answerOption);
                }

                FhirItem fhirItemLevel2 = new FhirItem(
                        question,
                        versionNumber2,
                        type,
                        level1ExtensionListLevel2,
                        allowRepeat,
                        answerOptionList,
                        null,
                        null,
                        null,
                        null);
                fhirItemListLevel2.add(fhirItemLevel2);

            }

            //Create Item for Questions and the choices
            FhirItem fhirItem = new FhirItem(
                    text,
                    versionNumber1,
                    Standards.group.name(),
                    extensionListItem1,
                    null,
                    null,
                    null,
                    fhirItemListLevel2,
                    null,
                    null
            );
            fhirItemList.add(fhirItem);

        }

        Root root = new Root(
                title,
                Standards.active.name(),
                versionNumber,
                publisher,
                Standards.Questionnaire.name(),
                subjectTypeList,
                level1ExtensionList,
                fhirItemList);

        JsonObject rootNew = formatterHelper.removeNullValues(root);


        return new Results(200, rootNew);
    }

    @NotNull
    private static List<Level1Extension> getLevel1Extensions() {
        List<Level1Extension> extensionListItem1 = new ArrayList<>();
        ValueCodeableConcept valueCodeableConcept = getValueCodeableConcept();
        Level1Extension level1ExtensionItem1 = new Level1Extension(
                "http://hl7.org/fhir/StructureDefinition/questionnaire-itemControl",
                null,null, valueCodeableConcept, null);
        extensionListItem1.add(level1ExtensionItem1);
        return extensionListItem1;
    }

    @NotNull
    private static ValueCodeableConcept getValueCodeableConcept() {
        List<ValueCoding> valueCodingList = new ArrayList<>();
        ValueCoding valueCoding = new ValueCoding(
                Standards.page.name(),
                Standards.page.name(),
                "http://hl7.org/fhir/questionnaire-item-control"
        );
        valueCodingList.add(valueCoding);
        ValueCodeableConcept valueCodeableConcept = new ValueCodeableConcept(
                Standards.page.name(),
                valueCodingList);
        return valueCodeableConcept;
    }



}
