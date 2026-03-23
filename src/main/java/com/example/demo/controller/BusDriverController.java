package com.example.demo.controller;

import static com.example.demo.session.manager.SessionManager.adminSessions;
import static com.example.demo.session.manager.SessionManager.busSessions;
import static com.example.demo.session.manager.SessionManager.busSubscribers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.demo.entity.BusDriver;
import com.example.demo.entity.BusDriverResponse;
import com.example.demo.service.BusDriverService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
public class BusDriverController {
	
	
	@Autowired
	private BusDriverService busDriverService;
	
	
	private String extractBusId(String payload) throws Exception {
	    ObjectMapper mapper = new ObjectMapper();
	    Map<String, Object> map = mapper.readValue(payload, Map.class);
	    return (String) map.get("busId");
	}
	
	@PostMapping("/location")
	public void receiveLocation(@RequestBody String payload) throws Exception {
	    System.out.println("📍 Received from Controller For BackGGround when Screen Locked!!!!!!!: " + payload);
	    String busId = extractBusId(payload); // parse JSON

	    
	    List<WebSocketSession> parents = busSubscribers.get(busId);

	    if (parents != null) {
       	 System.out.println(":::when Screen Locked:::::found parent!!!!!");
           for (WebSocketSession parent : parents) {
               if (parent.isOpen()) {
               	 System.out.println(":::when Screen Locked:::::parent connection is opened!!!!!!!!!!!");
                   parent.sendMessage(new TextMessage(payload));
                   System.out.println(":::when Screen Locked:::::successfully send meaasge--->");
                   System.out.println(new TextMessage(payload));
               }
           }
       }// parent
	    
	    for (WebSocketSession admin : adminSessions) {
            if (admin.isOpen()) {
            	 System.out.println(":::when Screen Locked:::::admin connection is opened!!!!!!!!!!!");
                 
                admin.sendMessage(new TextMessage(payload));
                System.out.println(":::when Screen Locked:::::successfully send meaasge:::: from Admin--->");
            }
        }
	}
	
	
	@GetMapping("/bus/driver/update")
	public String   updateBusDriver(@RequestParam String busId,@RequestParam String mobileNo,
			@RequestParam String password) {
		
		
		busDriverService.updateBusDriver(busId,mobileNo,password);
		
		return "Successfully saved Driver ---->"+mobileNo+":::::::for bus ID--->"+busId;
		
	}
	
	@GetMapping("/bus/driver/busId")
	public BusDriverResponse getBusIdForDriver(@RequestParam String mobileNo,@RequestParam String password) {
		
		System.out.println("mobileNo--------->"+mobileNo);
		System.out.println("password--------->"+password);
		
		return busDriverService.getBusId(mobileNo,password);
		
	}
	
	@GetMapping("/bus/get/all/drivers")
	public List<BusDriver> getAllBusDrivers() {
		
		return busDriverService.getAllBusDrivers();
		
	}
	
	

}
