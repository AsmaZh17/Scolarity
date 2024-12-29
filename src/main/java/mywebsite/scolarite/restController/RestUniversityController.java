package mywebsite.scolarite.restController;

import mywebsite.scolarite.entity.University;
import mywebsite.scolarite.service.IMPL.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/universities")
public class RestUniversityController {

    @Autowired
    UniversityService universityService;

    @GetMapping
    public List<University> getAllUniversities() {
        return universityService.findAll();
    }

    @GetMapping("/{universityId}")
    public University getUniversityById(@PathVariable("universityId") Long id) {
        return universityService.findByUniversityID(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public University addUniversity(@RequestBody University university) {
        return universityService.addUniversity(university);
    }

    @DeleteMapping("/{universityId}")
    public String deleteUniversity(@PathVariable("universityId") Long id) {
        universityService.deleteUniversity(id);
        return "University with ID " + id + " deleted successfully";
    }

    @PutMapping("/{universityId}")
    public University updateUniversity(@PathVariable("universityId") Long id, @RequestBody University university) {
        return universityService.updateUniversity(id, university);
    }
}
