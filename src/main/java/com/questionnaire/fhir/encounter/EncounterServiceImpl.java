package com.questionnaire.fhir.encounter;

import com.questionnaire.fhir.DbEncounter;
import com.questionnaire.fhir.Results;
import com.questionnaire.fhir.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

        List<Extension> extensionList = new ArrayList<>();
        List<Item> itemList = new ArrayList<>();

        for (DbItems dbItems: dbItemsList){

            String text = dbItems.getText();
            isPageable = Boolean.TRUE.equals(dbItems.isPageable());
            List<DbQuestionItems> dbQuestionItemsList = dbItems.getQuestionItems();
            for (DbQuestionItems dbQuestionItems: dbQuestionItemsList){
                String question = dbQuestionItems.getQuestion();
                String type = dbQuestionItems.getType();
                allowRepeat = Boolean.TRUE.equals(dbQuestionItems.getAllowRepeat());
                List<ValueCoding> valueCodingList = dbQuestionItems.getAnswerOption();
                for (ValueCoding valueCoding : valueCodingList){
                    String code = valueCoding.getCode();
                    String display = valueCoding.getDisplay();
                    String system = valueCoding.getSystem();

                }
            }
        }

        String versionNumber = formatterHelper.generateNumber(2);
        List<String> subjectTypeList = new ArrayList<>();
        subjectTypeList.add(Standards.Encounter.name());

        //Create Root
        Root root = new Root(
                title,
                Standards.ACTIVE.name(),
                versionNumber,
                publisher,
                Standards.Questionnaire.name(),
                subjectTypeList,
                extensionList,
                itemList);

        return new Results(200, root);
    }
}
