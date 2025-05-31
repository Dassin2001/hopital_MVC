package umi.fs.hopital;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import umi.fs.hopital.entities.Patient;
import umi.fs.hopital.repository.PateintRepository;

import java.util.Date;

@SpringBootApplication
public class HopitalApplication implements CommandLineRunner {
    @Autowired
    private PateintRepository pateintRepository;
    public static void main(String[] args) {
        SpringApplication.run(HopitalApplication.class, args);
    }
    //teste
    @Override
    public void run(String... args) throws Exception {
        pateintRepository.save(new Patient(null,"Imane",new Date(),false,34));
        pateintRepository.save(new Patient(null,"Hanane",new Date(),false,4321));
        pateintRepository.save(new Patient(null,"Mohamed",new Date(),true,21 ));

    }

}
