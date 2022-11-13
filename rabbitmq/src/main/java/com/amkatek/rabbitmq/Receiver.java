package com.amkatek.rabbitmq;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
//import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1) ;
    private RestTemplate restTemplate = new RestTemplate();
    private ChatAPI chatAPI = new ChatAPI();
    //TODO - This is using another endpoint to actually send a message. FIX and send from this function instead
    public void receiveMessage(String message) throws URISyntaxException {
        System.out.println("Received < "+ message + ">");
       // String[] msg = message.split("KY_");

        // notes - https://spring.io/guides/gs/consuming-rest/
        final String baseUrl = "https://massapi.amkatek.com/amkatek/whatsapp/api/v1.0/clickatell/messages/sending";
        final String baseUrl2 = "https://platform.clickatell.com/v1/message";
        //add a mapping for items
         /*HashMap<String,String> messageContent = new HashMap<>();
         messageContent.put("channel","whatsapp");
         messageContent.put("to","254712122743");
         messageContent.put("message","This is a test to clickatell");*/
        /////////////////////////////
        ObjectMapper mapper1 = new ObjectMapper() ;
        ObjectNode objectNode1 = mapper1.createObjectNode();
        objectNode1.put("channel", "whatsapp");
        objectNode1.put("to",  "254712122743");
        objectNode1.put("content", "Hi, Format TEXT SENT >> 2:3.0Py >> 1:2.6Py >> KY_254712122743");
//System.out.println(objectNode1);
        //create a list
        List mylist = new ArrayList<>();
        mylist.add(objectNode1.toString().replaceAll("\\\\",""));
        //System.out.println(mylist);
        //HashMap<String,String> map = new HashMap<>();
 /*map.put("chatid","455538") ;
 map.put("phone_no","254712122743");
 map.put("text","This is a test message to chatapi");*/
        ////////////////////////////////////////

        ////////////////////////////////////////
// map.put("message", mylist.toString());
        //set headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type","application/json");
        headers.add("Accept","application/json");
        headers.add("Authorization","epZEKmDMRBant9idyhWH0Q==");
        //customization of requests sending
        ObjectMapper mapper = new ObjectMapper() ;
        ObjectNode objectNode = mapper.createObjectNode();

//
        //System.out.println("Debug:: Map to String >>>>>>>> ");
        //System.out.println(objectNode);
        String jsonString = null;
        try{
            jsonString = mapper.writeValueAsString(mylist);
        } catch (Exception e) {
            System.out.println(e);
        }

        // jackson converted json object

        String mycustomList = mylist.toString().replaceAll("\\\\","") ;
        //TODO
        objectNode.put("messages", mycustomList);
        //objectNode.put("message", "test");
        // we will fix this
        String newObjectToSend = objectNode.toString().replaceAll("\\\\","").replaceAll("\"\\[","[").replaceAll("\\]\"","]") ;
        //System.out.println("Debug:: We send :::: newObjectToSend ");
        //System.out.println(newObjectToSend.replaceAll(">>","\n"));
        // you have to escape the \n character to - https://stackoverflow.com/questions/70785485/matcher-replaceall-removes-backslash-even-when-i-escape-it-java
        String new1 = newObjectToSend.replaceAll(">>","\\\\n") ;
        String new2 = new1;
        HttpEntity<Object> entity2 = new HttpEntity<>(new2,headers);
        System.out.println("Debug::: Formatted data >>>>>> ");
        System.out.println(entity2);
        HttpEntity<Object> entity = new HttpEntity<>(objectNode,headers);
        //
        try {
            // var dataString = map.toString();
            //var data2String = {"message": [{"channel":"whatsapp", "to":"254712122743", "message":"This is a test to clickatell"} ]} ;
            // System.out.println(entity);
            //String json = new Gson().toJson(mylist );

            URI uri = new URI(baseUrl2);
            // commented out temporarily for now
            //ResponseEntity<Void> response = restTemplate.postForEntity(uri,entity,Void.class);
            //we use amkatek api
            ResponseEntity<Void> response = restTemplate.postForEntity(uri,entity2,Void.class);
            // check response
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Request Successful");
            } else {
                System.out.println("Request Failed");
                System.out.println(response);
            }
            //System.out.println("Debug::: post_to_chatapi >>>>> ");
            // System.out.println(post_to_chatapi);
        } catch (Exception e){
            System.out.println("Error: There was an error experienced!");
            System.out.println(e);
        }


    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return  builder.build();
    }
    public CountDownLatch getLatch(){
        return latch;
    }
}
