package security.securityscolarity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import security.securityscolarity.entity.University;
import security.securityscolarity.entity.UniversityAdmin;
import security.securityscolarity.service.IMPL.UniversityAdminService;
import security.securityscolarity.service.IMPL.UniversityService;

import java.util.List;

@Controller
@RequestMapping("/universityAdmins")
public class UniversityAdminController {

    @Autowired
    UniversityAdminService universityAdminService;
    @Autowired
    private UniversityService universityService;

    @GetMapping("/all")
    public String getUniversityAdmin(Model model) {
        model.addAttribute("allUniversityAdmin", universityAdminService.findAll());
        model.addAttribute("currentUrl", "universityAdmin_list");
        return "GlobalAdmin/universityAdmin/allUniversityAdmin";
    }

    @GetMapping("/detail")
    public String getUniversityAdmin(@RequestParam("universityAdminId") Long id, Model model) {
        UniversityAdmin universityAdmin = universityAdminService.findByUniversityAdminID(id);
        model.addAttribute("universityAdmin", universityAdmin);
        model.addAttribute("currentUrl", "universityAdmin_detail");
        return "GlobalAdmin/universityAdmin/detailUniversityAdmin";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("universityAdmin", new UniversityAdmin());
        model.addAttribute("action","Add");
        model.addAttribute("universities", universityService.getUniversityNotAssignedToAdminUniversity());
        model.addAttribute("currentUrl", "universityAdmin_add");
        return "GlobalAdmin/universityAdmin/formUniversityAdmin";
    }

    @PostMapping("/addUniversityAdmin")
    public String addUniversityAdmin(@ModelAttribute UniversityAdmin universityAdmin) {
        universityAdminService.addUniversityAdmin(universityAdmin);
        return "redirect:/universityAdmins";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("universityAdminId") Long id) {
        universityAdminService.deleteUniversityAdmin(id);
        return "redirect:/universityAdmins";
    }

    @GetMapping("/update")
    public String updateUniversityAdmin(@RequestParam("universityAdminId") Long id, Model model) {
        UniversityAdmin universityAdmin = universityAdminService.findByUniversityAdminID(id);
        List<University> universities = universityService.getUniversityNotAssignedToAdminUniversity();
        if (universityAdmin.getUniversity() != null) {
            universities.add(universityAdmin.getUniversity());
        }
        model.addAttribute("universityAdmin", universityAdmin);
        model.addAttribute("action","Update");
        model.addAttribute("universities", universities);
        model.addAttribute("currentUrl", "universityAdmin_update");
        return "GlobalAdmin/universityAdmin/formUniversityAdmin";
    }

    @PostMapping("/updateUniversityAdmin")
    public String updateUniversityAdmin(@ModelAttribute UniversityAdmin universityAdmin) {
        universityAdminService.updateUniversityAdmin(universityAdmin.getId(),universityAdmin);
        return "redirect:/universityAdmins";
    }
}