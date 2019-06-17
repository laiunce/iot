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

/*
    // http://127.0.0.1:8080
    @PostMapping("/process-message")
    public String sendMessage(@RequestParam("texto") String texto) {
        //String message = String.format("{\"%s\":\"%s\",\"%s\":%s,\"%s\":%s,\"%s\":[%s]}", "geo_name",geo_name,"sickscore_coldflu",sickscore_coldflu,"rule_set_id",rule_set_id,"action_ids",action_ids);
        String message = String.format("{\"%s\":\"%s\"}", "text",texto);
        app.sendMessage(message);
        return "Message sent.";
    }
*/

    @PostMapping("/process-message")
    public String sendMessage(@RequestParam("imei") String imei, @RequestParam("momsn") String momsn, @RequestParam("transmit_time") String transmit_time, @RequestParam("iridium_latitude") String iridium_latitude, @RequestParam("iridium_longitude") String iridium_longitude, @RequestParam("iridium_cep") String iridium_cep, @RequestParam("data") String data) {
            String message = String.format("{\"%s\":\"%s\",\"%s\":%s,\"%s\":%s,\"%s\":%s,\"%s\":%s,\"%s\":%s,\"%s\":%s}", "imei", imei,"momsn", momsn,"transmit_time", transmit_time,"iridium_latitude", iridium_latitude,"iridium_longitude", iridium_longitude,"iridium_cep", iridium_cep,"data", data);
            app.sendMessage(imei,momsn,transmit_time,iridium_latitude,iridium_longitude,iridium_cep,data);
        return "Message sent.";
    }



    @PostMapping("/restart")
    public String restart() {
        IoTHandler.restart();
        return "Reloading context";
    }    

}


