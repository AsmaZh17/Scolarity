package mywebsite.scolarite.controller;

import mywebsite.scolarite.entity.Day;
import mywebsite.scolarite.service.IMPL.ChronoDayService;
import mywebsite.scolarite.service.IMPL.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/day")
public class DayController {

    @Autowired
    DayService dayService;
    @Autowired
    private ChronoDayService chronoDayService;

    @GetMapping("/all")
    public String getDay(Model model) {
        List<Day> allDay = dayService.findAll();
        model.addAttribute("allDay", allDay);
        model.addAttribute("currentUrl", "day_list");
        return "day/allDay";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("day", new Day());
        model.addAttribute("action","Add");
        model.addAttribute("currentUrl", "day_add");
        return "day/formDay";
    }

    @PostMapping("/addDay")
    public String addDay(@ModelAttribute Day day, RedirectAttributes redirectAttributes) {
        dayService.addDay(day);
        redirectAttributes.addFlashAttribute("successMessage", "Day added");
        return "redirect:/day/all";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("dayId") Long id) {
        chronoDayService.deleteChronoDayByDayId(id);
        dayService.deleteDay(id);
        return "redirect:/day/all";
    }

    @GetMapping("/update")
    public String updateDay(@RequestParam("dayId") Long id, Model model) {
        Day day = dayService.findByDayID(id);
        model.addAttribute("day", day);
        model.addAttribute("action","Update");
        model.addAttribute("currentUrl", "day_update");
        return "day/formDay";
    }

    @PostMapping("/updateDay")
    public String updateDay(@ModelAttribute Day day) {
        dayService.updateDay(day.getDayId(),day);
        return "redirect:/day/all";
    }
}
