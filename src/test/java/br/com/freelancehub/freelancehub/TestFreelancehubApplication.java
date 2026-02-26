package br.com.freelancehub.freelancehub;

import org.springframework.boot.SpringApplication;

public class TestFreelancehubApplication {

	public static void main(String[] args) {
		SpringApplication.from(FreelancehubApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
