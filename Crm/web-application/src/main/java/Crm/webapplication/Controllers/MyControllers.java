package Crm.webapplication.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import Crm.modelmodule.Model.FirstClass;





@RestController
public class MyControllers {

	@GetMapping("/name")
	public String gg() {
		FirstClass nn = new FirstClass(23,4);
		int fo = 90;
		String message = "dsdsds";
		for(int i = 0;i<= 10;i++) {
			fo = fo + i;
		}
		return message + " fsdf nnnnnnnn "+nn.numH();
	}
	@GetMapping("/name2")
	public String ggg() {
		FirstClass nn = new FirstClass(23,4);
		int fo = 90;
		String message = "dsdsds";
		for(int i = 0;i<= 10;i++) {
			fo = fo + i;
		}
		return message + " fsdf nnnnnnnn "+nn.numH() + nn.toString();	
		
	}
}
