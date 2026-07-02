package com.S3.Bucket.Demo.controller;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.S3.Bucket.Demo.service.S3Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import DTO.ClientStateMaster;
import DTO.Merchant;
import DTO.StateMaster;

@RestController
public class S3Controller {

    @Autowired
    private S3Service s3Service;
    
    @Autowired
     ObjectMapper objectMapper;
    
    private Map<String, byte[]> fileMap = new HashMap<>();
   
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
    	return s3Service.saveFile(file);     
    }

    @GetMapping("download/{filename}")
    public  Map<String, Merchant> download(@PathVariable("filename") String filename) {
        byte[] s3File = s3Service.downloadFile(filename);

       
        InputStream inputStream = new ByteArrayInputStream(s3File);

        Map<String, Merchant> merchantMap = new HashMap<>();

        try {
            // Read the content of the InputStream and convert it to a list of Merchants
            List<Merchant> merchantList = objectMapper.readValue(inputStream, new TypeReference<List<Merchant>>() {});
            
            // Convert the list of Merchants to a Map
            merchantMap = merchantList.stream().collect(Collectors.toMap(Merchant::getClientId, dto -> dto));
        } catch (IOException e) {
            // Handle any IO exception
            e.printStackTrace();
        }

        return merchantMap;
    }
    
//    @GetMapping("/StateMaster")
//    public Map<String,StateMaster> downloadData()
//    {
//    	  byte[] s3File = s3Service.downloadFile("StateMaster");
//
//          
//          InputStream inputStream = new ByteArrayInputStream(s3File);
//
//          Map<String, StateMaster> StateMap = new HashMap<>();
//
//          try {
//              List<StateMaster> stateList = objectMapper.readValue(inputStream, new TypeReference<List<StateMaster>>() {});
//              
//              // Convert the list of State to a Map
//              StateMap = stateList.stream().collect(Collectors.toMap(StateMaster::getStateCode, dto1 -> dto1));
//          } catch (IOException e) {
//              e.printStackTrace();
//          }
//
//          return StateMap;
//    }
    
    
    @GetMapping("/ClientStateMaster")
    public Map <String,ClientStateMaster> downloadData()
    {

    	 byte[] s3File = s3Service.downloadFile("statemaster.json");

         
         InputStream inputStream = new ByteArrayInputStream(s3File);

         Map<String, ClientStateMaster> stateMap = new HashMap<>();

         try {
             
             List<ClientStateMaster> clientStateList = objectMapper.readValue(inputStream, new TypeReference<List<ClientStateMaster>>() {});
             
             // Convert the list of Merchants to a Map
             stateMap = clientStateList.stream().collect(Collectors.toMap(ClientStateMaster::getClientCode, dto1 -> dto1));
         } catch (IOException e) {
             // Handle any IO exception
             e.printStackTrace();
         }

         return stateMap;
    	
    }
    
    @DeleteMapping("{filename} ")
    public String deleteFile(@PathVariable("filename")String filename) 
    {
    	return s3Service.deletefile(filename);
    }
    
}
