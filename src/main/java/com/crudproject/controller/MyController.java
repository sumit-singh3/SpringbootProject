package com.crudproject.controller;

import com.crudproject.entity.Tutorial;
import com.crudproject.model.Request;
import com.crudproject.model.Response;
import com.crudproject.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MyController {

    @Autowired
    TutorialRepository tutorialRepository;
    @Autowired
    ObjectMapping objectMapping;


    @GetMapping("/tutorials/{id}")
    public Tutorial getTutorialsId(@PathVariable(value = "id") Long tutorialsId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialsId).get();
        return tutorial;
    }


    @PostMapping("/tutorials")
    public ResponseEntity<Response> createTutorial(@RequestBody Request request) {
        try {
            Tutorial tutorial = objectMapping.requestMappingToEntityModel(request);
            Tutorial _tutorial = tutorialRepository.save(tutorial);
            Response response = objectMapping.entityModelMappingToResponse(_tutorial);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials")
    public ResponseEntity<List<Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        List<Tutorial> tutorials = new ArrayList<Tutorial>();

        try {
            if (title == null)
                tutorialRepository.findAll().forEach(tutorials::add);
            else
                tutorialRepository.findByTitle(title).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/tutorials/{id}")

    public String deleteTutorials(@PathVariable(value = "id") Long tutorialId) {

        Tutorial tutorial = tutorialRepository.findById(tutorialId).get();
        tutorialRepository.delete(tutorial);
        return  "Done";
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<Tutorial> updateTutorials(@PathVariable(value = "id") Long tutorialsId,
                                                         @RequestBody Tutorial tutorialDetails) {
        Tutorial tutorial1 = tutorialRepository.findById(tutorialsId).get();
         tutorial1.setTitle(tutorialDetails.getTitle());
         tutorial1.setPublished(tutorialDetails.getPublished());
         tutorial1.setDescription(tutorialDetails.getDescription());
        final Tutorial update = tutorialRepository.save(tutorial1);
        return ResponseEntity.ok(tutorial1);
    }


}

