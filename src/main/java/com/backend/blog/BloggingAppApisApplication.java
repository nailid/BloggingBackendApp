package com.backend.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.backend.blog.config.AppConstants;
import com.backend.blog.entities.Role;
import com.backend.blog.repositories.RoleRepository;

@SpringBootApplication
public class BloggingAppApisApplication {

	@Autowired
	private RoleRepository roleRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingAppApisApplication.class, args);
	}

	public void run(String... args) throws Exception {
		try {
			Role roleAdmin = new Role();
			roleAdmin.setId(AppConstants.ADMIN_USER);
			roleAdmin.setRole("ADMIN_USER");
			
			Role roleNormal = new Role();
			roleNormal.setId(AppConstants.NORMAL_USER);
			roleNormal.setRole("NORMAL_USER");
			
			List<Role> roles = List.of(roleAdmin, roleNormal);
			List<Role> result = this.roleRepository.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getRole());
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
