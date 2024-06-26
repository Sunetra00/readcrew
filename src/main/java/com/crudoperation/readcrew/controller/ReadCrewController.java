package com.crudoperation.readcrew.controller;


import com.crudoperation.readcrew.model.Crew;
import com.crudoperation.readcrew.model.ProcessedRequestDTO;
import com.crudoperation.readcrew.model.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ReadCrewController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;
    String url = "http://dynamodbupdater/ddboperation";


    @GetMapping("/getallcrew")
    public ResponseEntity<Response> readCrew() throws JsonProcessingException {
        ProcessedRequestDTO requestDTO = createRequest(null);
        String jsonRequest =mapper.writeValueAsString(requestDTO);
        System.out.println(jsonRequest);
        HttpEntity<ProcessedRequestDTO> request = new HttpEntity<>(requestDTO);
        ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST,request, Response.class);
        System.out.println(response);

        return response;
    }
    
    @GetMapping("/getcrew/{crewid}")
    public ResponseEntity<Response> readSingleCrew(@PathVariable String crewid) throws JsonProcessingException {
        ProcessedRequestDTO requestDTO = createRequest(crewid);
        String jsonRequest = mapper.writeValueAsString(requestDTO);
        System.out.println(jsonRequest);
        HttpEntity<ProcessedRequestDTO> request = new HttpEntity<>(requestDTO);
        ResponseEntity<Response> response = restTemplate.exchange(url, HttpMethod.POST, request, Response.class);
        System.out.println(response);
        
        return response;
    }

    private ProcessedRequestDTO createRequest(String crewid){
        ProcessedRequestDTO requestDTO = new ProcessedRequestDTO();
        if (crewid == null) {
            requestDTO.setOperationType("READALL");
        } else {
            requestDTO.setOperationType("READ");
            requestDTO.setCrewid(crewid); // Assuming ProcessedRequestDTO has a crewid field
        }
        return requestDTO;
    }
}
