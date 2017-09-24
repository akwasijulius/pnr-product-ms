package com.jogsoft.apps.pnr.product;

import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


@SpringBootApplication
public class Application {
	
    public static void main( String[] args ){
    	SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public ModelMapper modelMapper(){
    	ModelMapper mapper = new ModelMapper();
    	return mapper;
    }
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {         
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
    }
     
    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }   
}
