package com.github.dearrudam.springwithjnosqlmongodb.sample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/developers")
public class DeveloperController {

    private final Developers developers;

    public DeveloperController(Developers developers) {
        this.developers = developers;
    }

    public static record NewDeveloper(String name) {

        static Developer toEntity(NewDeveloper newDeveloper) {
            return Developer.of(newDeveloper.name());
        }
    }

    public static record CreatedDeveloper(String id, String name) {
        static CreatedDeveloper of(Developer developer) {
            return new CreatedDeveloper(developer.id(), developer.name());
        }
    }

    @PostMapping
    public CreatedDeveloper newDeveloper(
            @RequestBody NewDeveloper request) {
        var developer = NewDeveloper.toEntity(request);
        var persistedDeveloper = developers.save(developer);
        return CreatedDeveloper.of(persistedDeveloper);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreatedDeveloper> getDeveloperById(@PathVariable String id) {
        return developers.findById(id)
                .map(CreatedDeveloper::of)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CreatedDeveloper> update(
            @PathVariable String id,
            @RequestBody NewDeveloper request) {
        return developers.findById(id)
                .map(dev -> dev.updateName(request.name()))
                .map(developers::save)
                .map(CreatedDeveloper::of)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        developers.findById(id)
                .ifPresent(developers::delete);
    }

    @GetMapping
    public List<CreatedDeveloper> findAll() {
        return developers.findAll()
                .map(CreatedDeveloper::of)
                .toList();
    }


}
