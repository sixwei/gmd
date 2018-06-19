package com.sd.gmd;

import com.sd.gmd.config.MyJdbcTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GmdApplication {

	public static void main(String[] args) {
		SpringApplication.run(GmdApplication.class, args);
	}


//	@Bean
//	public MyJdbcTemplate getJdbcTemplate(){
//		return new MyJdbcTemplate();
//	}
}
