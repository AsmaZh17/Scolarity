package security.securityscolarity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import security.securityscolarity.entity.*;
import security.securityscolarity.repository.ChronoDayRepository;
import security.securityscolarity.repository.ChronoRepository;
import security.securityscolarity.repository.DayRepository;
import security.securityscolarity.service.IMPL.ChronoDayService;
import security.securityscolarity.service.IMPL.DayService;
import security.securityscolarity.service.IMPL.TeacherService;
import security.securityscolarity.service.IMPL.UserService;
import security.securityscolarity.service.ITeacherConstraintService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/teacherConstraints")
public class TeacherConstraintController {

    @Autowired
    private ITeacherConstraintService teacherConstraintService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private ChronoDayService chronoDayService;
    @Autowired
    private ChronoDayRepository chronoDayRepository;
    @Autowired
    private ChronoRepository chronoRepository;
    @Autowired
    private DayRepository dayRepository;

    @GetMapping("/all")
    public String getTeacherConstraints(Model model) {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserID(userDetail.getId());
        if (user instanceof UniversityAdmin universityAdmin) {
            model.addAttribute("teacherConstraints", teacherConstraintService.findTeacherConstraintByTeacherUniversity(universityAdmin.getUniversity()));
        }
        model.addAttribute("currentUrl", "teacherConstraint_list");
        return "UniversityAdmin/teacherConstraint/allTeacherConstraints";
    }

    @GetMapping("/detail")
    public String getTeacherConstraint(@RequestParam("teacherConstraintId") Long id, Model model) {
        TeacherConstraint teacherConstraint = teacherConstraintService.findByTeacherConstraintID(id);
        model.addAttribute("teacherConstraint", teacherConstraint);
        model.addAttribute("currentUrl", "teacherConstraint_detail");
        return "UniversityAdmin/teacherConstraint/detailTeacherConstraint";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserID(userDetail.getId());
        if (user instanceof UniversityAdmin universityAdmin) {
            model.addAttribute("teachers",teacherService.findTeacherByUniversity(universityAdmin.getUniversity()));
            model.addAttribute("chronoDays",chronoDayService.findByUniversity(universityAdmin.getUniversity()));
        }
        model.addAttribute("teacherConstraint", new TeacherConstraint());
        model.addAttribute("action", "Add");
        model.addAttribute("currentUrl", "teacherConstraint_add");
        return "UniversityAdmin/teacherConstraint/formTeacherConstraint";
    }

    @PostMapping("/addTeacherConstraint")
    public String addTeacherConstraint(@ModelAttribute TeacherConstraint teacherConstraint,
                                       @RequestParam("chronoDayIds") String[] chronoDayIds,
                                       RedirectAttributes redirectAttributes) {
        System.out.println(Arrays.toString(chronoDayIds));
        List<ChronoDay> unavailableDays = Arrays.stream(chronoDayIds)
                .map(chronoDayIdStr -> {
                    String[] parts = chronoDayIdStr.strip().split(",");
                    Long chronoId = Long.parseLong(parts[0].strip());
                    Long dayId = Long.parseLong(parts[1].strip());
                    return chronoDayRepository.findById(new ChronoDayId(
                                chronoRepository.findByChronoId(chronoId),
                                dayRepository.findByDayId(dayId))
                            ).orElseThrow(() -> new RuntimeException("ChronoDay not found"));
                })
                .collect(Collectors.toList());

        teacherConstraint.setUnavailableDays(unavailableDays);

        teacherConstraintService.addTeacherConstraint(teacherConstraint);
        redirectAttributes.addFlashAttribute("successMessage", "Teacher constraint added");
        return "redirect:/teacherConstraints";
    }

    @GetMapping("/delete")
    public String deleteTeacherConstraint(@RequestParam("teacherConstraintId") Long id, RedirectAttributes redirectAttributes) {
        teacherConstraintService.deleteTeacherConstraint(id);
        redirectAttributes.addFlashAttribute("successMessage", "Teacher constraint deleted");
        return "redirect:/teacherConstraints";
    }

    @GetMapping("/update")
    public String updateTeacherConstraint(@RequestParam("teacherConstraintId") Long id, Model model) {
        CustomUserDetail userDetail = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUserID(userDetail.getId());
        if (user instanceof UniversityAdmin universityAdmin) {
            model.addAttribute("teachers",teacherService.findTeacherByUniversity(universityAdmin.getUniversity()));
            model.addAttribute("chronoDays",chronoDayService.findByUniversity(universityAdmin.getUniversity()));
        }
        model.addAttribute("teacherConstraint", teacherConstraintService.findByTeacherConstraintID(id));
        model.addAttribute("action", "Update");
        model.addAttribute("currentUrl", "teacherConstraint_update");
        return "UniversityAdmin/teacherConstraint/formTeacherConstraint";
    }

    @PostMapping("/updateTeacherConstraint")
    public String updateTeacherConstraint(@ModelAttribute TeacherConstraint teacherConstraint, RedirectAttributes redirectAttributes) {
        teacherConstraintService.updateTeacherConstraint(teacherConstraint.getId(), teacherConstraint);
        redirectAttributes.addFlashAttribute("successMessage", "Teacher constraint updated");
        return "redirect:/teacherConstraints";
    }
}