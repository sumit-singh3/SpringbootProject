package com.crudproject.controller;

import com.crudproject.entity.Tutorial;
import com.crudproject.model.Request;
import com.crudproject.model.Response;
import com.crudproject.repository.TutorialRepository;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapping {
    public Tutorial requestMappingToEntityModel(Request request){
        Tutorial tutorial = new Tutorial();
        tutorial.setDescription(request.getDescription());
        tutorial.setPublished(request.getPublished());
        tutorial.setTitle(request.getTitle());
        return tutorial;
    }
    public Response entityModelMappingToResponse(Tutorial tutorial){
        Response response = new Response();
        response.setDescription(tutorial.getDescription());
        response.setPublished(tutorial.getPublished());
        response.setTitle(tutorial.getTitle());
        response.setId(tutorial.getId());
        return response;
    }
}
