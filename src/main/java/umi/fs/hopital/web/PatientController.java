package umi.fs.hopital.web;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.validation.Valid;
import umi.fs.hopital.entities.Patient;
import umi.fs.hopital.repository.PatientRepository;

@Controller
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue = "0") int p,
                        @RequestParam(name="size",defaultValue = "4") int s,
                        @RequestParam(name="keyword",defaultValue = "") String kw,
                        @RequestParam(name="successMessage", required = false) String successMessage) {
        Page<Patient> pagePatients = patientRepository.findByNomContains(kw,PageRequest.of(p,s));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", p);
        model.addAttribute("keyword", kw);
        if(successMessage != null) model.addAttribute("successMessage", successMessage);
        return "patients";
    }

    @GetMapping("/deletePatient")
    public String delete(Long id, String keyword, int page, RedirectAttributes redirectAttributes) {
        patientRepository.deleteById(id);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("keyword", keyword);
        redirectAttributes.addAttribute("successMessage", "Patient supprimé avec succès !");
        return "redirect:/index";
    }

    @GetMapping("/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("editMode", false);
        return "formPatient";
    }

    @PostMapping("/savePatient")
    public String savePatient(@Valid @ModelAttribute Patient patient,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              @RequestParam(defaultValue = "0")int page,
                              @RequestParam(defaultValue = "0")String keyword) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", false);
            return "formPatient";
        }
        patientRepository.save(patient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient ajouté avec succès !");
        return "redirect:/index?page="+page+"&keyword"+keyword;
    }

    @GetMapping("/editPatient")
    public String editPatient(@RequestParam Long id,
                              Model model,
                              RedirectAttributes redirectAttributes,
                              @RequestParam(defaultValue = "0")int page,
                              @RequestParam(defaultValue = "0")String keyword) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) return "redirect:/index";
        model.addAttribute("patient", patient);
        model.addAttribute("editMode", true);
        return "formPatient";
    }

    @PostMapping("/updatePatient")
    public String updatePatient(@Valid @ModelAttribute Patient patient, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", true);
            return "formPatient";
        }
        patientRepository.save(patient);
        redirectAttributes.addFlashAttribute("successMessage", "Patient modifié avec succès !");
        return "redirect:/index";
    }

    @GetMapping("/patientDetails")
    public String patientDetails(@RequestParam Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) return "redirect:/index";
        model.addAttribute("patient", patient);
        return "patientDetails";
    }
}