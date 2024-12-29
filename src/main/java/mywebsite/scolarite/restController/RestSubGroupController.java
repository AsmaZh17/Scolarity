package mywebsite.scolarite.restController;

import mywebsite.scolarite.entity.SubGroup;
import mywebsite.scolarite.service.IMPL.SubGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
    @RequestMapping("/api/subgroups")
public class RestSubGroupController {

    @Autowired
    SubGroupService subGroupService;

    @GetMapping
    public List<SubGroup> getAllSubGroups() {
        return subGroupService.findAll();
    }

    @GetMapping("/{subGroupId}")
    public SubGroup getSubGroupById(@PathVariable("subGroupId") Long id) {
        return subGroupService.findBySubGroupID(id);
    }

    @PostMapping
    public SubGroup addSubGroup(@RequestBody SubGroup subGroup) {
        return subGroupService.addSubGroup(subGroup);
    }

    @DeleteMapping("/{subGroupId}")
    public String deleteSubGroup(@PathVariable("subGroupId") Long id) {
        subGroupService.deleteSubGroup(id);
        return "SubGroup with ID " + id + " deleted successfully";
    }

    @PutMapping("/{subGroupId}")
    public SubGroup updateSubGroup(@PathVariable("subGroupId") Long id, @RequestBody SubGroup subGroup) {
        return subGroupService.updateSubGroup(id, subGroup);
    }
}
