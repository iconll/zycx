package com.zycx.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class})
@EnableCaching
public class ZycxSystemApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ZycxSystemApplication.class, args);
	}
	@Bean
	public MultipartConfigElement configElement(){
		String tmpUrl = "";
		String osName = System.getProperty("os.name");

		if(osName.contains("Windows")){
			tmpUrl = "C:\\tmp\\credit-rtm\\";
		}else if(osName.contains("Mac OS X")){
			tmpUrl = "/Users/jiong/Desktop";
		}else if(osName.contains("Linux")){
			tmpUrl = "/tmp/credit-rtm/";
		}
        File tmp = new File(tmpUrl);

		if(!tmp.exists()){
			tmp.mkdirs();
		}

		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation(tmpUrl);
		return factory.createMultipartConfig();
	}
}
