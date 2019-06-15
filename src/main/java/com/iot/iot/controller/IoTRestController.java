package com.iot.iot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.iot.iot.IoTOrchestrator;
import org.springframework.web.bind.annotation.PostMapping;
import com.iot.iot.IoTHandler;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author Cristian Laiun
 */


@RestController
public class IoTRestController {

    @Autowired
    IoTOrchestrator app;


    // http://127.0.0.1:8080
    @PostMapping("/process-message")
    public String sendMessage(@RequestParam("texto") String texto) {
        //String message = String.format("{\"%s\":\"%s\",\"%s\":%s,\"%s\":%s,\"%s\":[%s]}", "geo_name",geo_name,"sickscore_coldflu",sickscore_coldflu,"rule_set_id",rule_set_id,"action_ids",action_ids);
        String message = String.format("{\"%s\":\"%s\"}", "text",texto);
        app.sendMessage(message);
        return "Message sent.";
    }

    @PostMapping("/restart")
    public String restart() {
        IoTHandler.restart();
        return "Reloading context";
    }    

}


