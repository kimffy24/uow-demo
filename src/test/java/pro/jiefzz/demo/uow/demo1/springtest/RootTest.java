package pro.jiefzz.demo.uow.demo1.springtest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import pro.jiefzz.demo.uowdemo.App;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("dev")
@Configuration
@ContextConfiguration(classes = App.class)
@TestMethodOrder(MethodOrderer.MethodName.class)
public class RootTest {
	
}
