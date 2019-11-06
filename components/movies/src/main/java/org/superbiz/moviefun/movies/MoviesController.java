package org.superbiz.moviefun.movies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MoviesController {


    private MoviesRepository moviesRepository;

    public MoviesController(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }



    @PostMapping("/movies")
    public void addMovie(@RequestBody Movie movie) {
        moviesRepository.addMovie(movie);
       }

    @DeleteMapping("/movies/{id}")
    public void deleteMovieId(@PathVariable long id) {
        moviesRepository.deleteMovieId(id);
     }

    @GetMapping("/count")
    public int countAll() {
        return moviesRepository.countAll();
    }

    @GetMapping("/count?field={field}&searchTerm={searchTerm}")
    public int count(@PathVariable(name = "field") String field, @PathVariable(name = "searchTerm") String searchTerm) {

        return moviesRepository.count(field, searchTerm);
    }

   @GetMapping("/movies")
    public List<Movie> findAll(@RequestParam(name = "firstResult", required = false) int firstResult,
                               @RequestParam(name = "maxResults", required = false) int maxResults,
                               @RequestParam(name = "field", required = false) String field,
                               @RequestParam(name = "searchTerm", required = false) String searchTerm) {
        if (field != null) {
            return moviesRepository.findRange(field, searchTerm, firstResult, maxResults);
        } else {
            return moviesRepository.findAll(firstResult, maxResults);
        }

   }

}
