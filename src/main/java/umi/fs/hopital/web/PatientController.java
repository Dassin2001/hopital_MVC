package umi.fs.hopital.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import umi.fs.hopital.entities.Patient;
import umi.fs.hopital.repository.PateintRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import java.util.List;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PateintRepository pateintRepository;
    @GetMapping("/index")
    public String index(Model model) {
        Page<Patient> pagePatients = pateintRepository.findAll(PageRequest.of(0,4));
        model.addAttribute("patientList",pagePatients.getContent());
        return "patients";
    }
}
