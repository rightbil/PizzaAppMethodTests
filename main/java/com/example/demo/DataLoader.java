package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import sun.awt.geom.AreaOp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired

    XOrderRepository xOrderRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... strings) throws Exception {
        roleRepository.save(new Role("USER"));
        roleRepository.save(new Role("ADMIN"));
        Role adminRole = roleRepository.findByRole("ADMIN");
        Role userRole = roleRepository.findByRole("USER");

        User user = new User("jim@jim.com", "password", "Jim", "Jimmerson",
                true, "jim");
        user.setRoles(Arrays.asList(userRole));
        userRepository.save(user);

        User user2 = new User("admin@admin.com", "password", "Admin", "User",
                true, "admin");
        user2.setRoles(Arrays.asList(adminRole));
        userRepository.save(user2);


        User user3 = new User("test@test.com", "test", "Testy", "Tester", true, "test");
        user3.setRoles(Arrays.asList(userRole));
        userRepository.save(user3);

        /*Adding XOrder objects*/
        XOrder o11= new XOrder("Cheese, vegie,tommato,pepperoni,BBQ", user);
        XOrder o12= new XOrder("Cheese, vegie,tommato,pepperoni", user);

        XOrder o21= new XOrder("Cheese, vegie,tommato,", user2);
        XOrder o22= new XOrder("Cheese, vegie,tommato,pepperoni,BBQ", user2);


        XOrder o31= new XOrder("Cheese, vegie,tommato,", user3);
        XOrder o32= new XOrder("Cheese, vegie,pepperoni,BBQ", user3);
        XOrder o33= new XOrder("Cheese, tommato,pepperoni,BBQ", user3);


    }
}
