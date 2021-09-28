package pro.jiefzz.demo.uowdemo.expose.http;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pro.jiefzz.demo.uowdemo.service.UserEntranceService;

@RestController
@RequestMapping("/first")
public class FirstController {

	private final static Logger logger = LoggerFactory.getLogger(FirstController.class);
	
	@Autowired
	UserEntranceService userEntranceService;

	@RequestMapping(value = "/testRegisterStep1", method = RequestMethod.POST)
	public Map<String, Object> testRegisterStep1() {
		Integer uid = userEntranceService.registerNormalUserStep1("15920594796", null).getId();
		return new HashMap<>();
	}
	
}
