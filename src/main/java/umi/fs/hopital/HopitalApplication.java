package umi.fs.hopital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import umi.fs.hopital.entities.Patient;
import umi.fs.hopital.repository.PatientRepository;
import umi.fs.hopital.repository.UserRepository;
import umi.fs.hopital.security.SecurityConfig;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {
    @Autowired
    private PatientRepository pateintRepository;
    private UserRepository user1;
    public static void main(String[] args) {
        SpringApplication.run(HopitalApplication.class, args);
    }
    //teste
    @Override
    public void run(String... args) throws Exception {
        pateintRepository.save(new Patient(null,"Imane",new Date(),false,314));
        pateintRepository.save(new Patient(null,"Hanane",new Date(),false,4321));
        pateintRepository.save(new Patient(null,"Mohamed",new Date(),true,211 ));


    }
    @Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager, PasswordEncoder passwordEncoder){
        return args -> {
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user11").password(passwordEncoder.encode("1234")).roles("USER").build()
            );
            jdbcUserDetailsManager.createUser(
                    User.withUsername("user22").password(passwordEncoder.encode("1234")).roles("USER").build()
            );
            jdbcUserDetailsManager.createUser(
                    User.withUsername("admin2").password(passwordEncoder.encode("1234")).roles("USER", "ADMIN").build()
            );
        };
    }



}
