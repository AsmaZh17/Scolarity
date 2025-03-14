package security.securityscolarity.restController;

import security.securityscolarity.entity.Building;
import security.securityscolarity.service.IMPL.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/buildings")
public class RestBuildingController {

    @Autowired
    BuildingService buildingService;

    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingService.findAll();
    }

    @GetMapping("/university/{universityId}")
    public List<Building> getBuildingsByUniversity(@PathVariable("universityId") Long id) {
        return buildingService.findByUniversityId(id);
    }

    @GetMapping("/{buildingId}")
    public Building getBuildingById(@PathVariable("buildingId") Long id) {
        return buildingService.findByBuildingID(id);
    }

    @PostMapping
    public Building addBuilding(@RequestBody Building building) {
        return buildingService.addBuilding(building);
    }

    @PostMapping("/{universityId}")
    public Building addBuildingByUniversity(@PathVariable("universityId") Long id, @RequestBody Building building) {
        return buildingService.addBuildingByUniversity(building,id);
    }

    @DeleteMapping("/{buildingId}")
    public String deleteBuilding(@PathVariable("buildingId") Long id) {
        buildingService.deleteBuilding(id);
        return "Building with ID " + id + " deleted successfully";
    }

    @PutMapping("/{buildingId}")
    public Building updateBuilding(@PathVariable("buildingId") Long id, @RequestBody Building building) {
        return buildingService.updateBuilding(id, building);
    }
}