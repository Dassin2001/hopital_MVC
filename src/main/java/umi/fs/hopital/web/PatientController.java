package umi.fs.hopital.web;

import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PateintRepository pateintRepository;
    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue="0") int p,
                        @RequestParam(name="size",defaultValue="4") int s,
                        @RequestParam(name="keyword",defaultValue="") String kw) {

        Page<Patient> pagePatients = pateintRepository.findByNomContains(kw,PageRequest.of(p,s));
        //Page<Patient> pagePatients = pateintRepository.findAll(PageRequest.of(p,s));
        model.addAttribute("patientList",pagePatients.getContent());
        model.addAttribute("currentPages",p);
        model.addAttribute("keyword",kw);
        //Avoir le nombre total des pages
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        return "patients";
    }@GetMapping("/delete")
    public String delete(Long id,int page,String keyword) {
        pateintRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword ;
    }
    @GetMapping("/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }
    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute Patient patient) {
        pateintRepository.save(patient);
        return "redirect:/index";
    }
    @GetMapping("/editPatient")
    public String editPatient(@RequestParam Long id, Model model) {
        Patient patient = pateintRepository.findById(id).orElse(null);
        if (patient == null) return "redirect:/index";
        model.addAttribute("patient", patient);
        return "formPatient";
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat, true));
    }

}

