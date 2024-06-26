package webtechBackend.webtechprojekt.web;

import webtechBackend.webtechprojekt.models.Trainingsplan;
import webtechBackend.webtechprojekt.service.TrainingsplanService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@AllArgsConstructor
@RequestMapping(path = "/trainingsplaene")
public class TrainingsplanController {

    private final TrainingsplanService trainingsplanService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Trainingsplan>> getTrainingsplaene() {
        return ResponseEntity.ok(trainingsplanService.getTrainingsplaene());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Trainingsplan> getTrainingsplan(@PathVariable("id") Integer id) {
        final Optional<Trainingsplan> found = trainingsplanService.getTrainingsplan(id);
        return found.isPresent()
                ? ResponseEntity.ok(found.get())
                : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trainingsplan> addTrainingsplan(@Valid @RequestBody Trainingsplan body){
        final Trainingsplan t = new Trainingsplan(body.getId(), body.getName(), body.getZeit(), body.getDauer(), body.getIntensivitaet());
        final Trainingsplan createdTrainingsplan = trainingsplanService.addTrainingsplan(t);
        return new ResponseEntity<>(createdTrainingsplan, HttpStatus.CREATED);

    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trainingsplan> updateTrainingsplan(@PathVariable("id") Integer id, @RequestBody Trainingsplan body) {
        body.setId(id);
        final Trainingsplan updatedTrainingsplan = trainingsplanService.editTrainingsplan(body);
        if (updatedTrainingsplan == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(updatedTrainingsplan);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTrainingsplan(@PathVariable("id") Integer id) {
        return trainingsplanService.removeTrainingsplan(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

