package org.superbiz.moviefun.moviesapi;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.util.List;


@Component
public class MoviesClient {

    private RestOperations restTemplate;
    private String moviesUrl;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public MoviesClient(String moviesUrl, RestOperations restTemplate) {
        this.restTemplate = restTemplate;
        this.moviesUrl = moviesUrl;
    }

    private String fullUrl(String path) {
        return moviesUrl + "/" + path;
    }

    public void addMovie(MovieInfo movie) {
        logger.info("Adding {}", movie);
        ResponseEntity<String> createResponse = restTemplate.postForEntity(fullUrl("/movies"), movie, String.class);
    }

    public void deleteMovieId(long id) {
        ResponseEntity<String> createResponse = restTemplate.exchange(fullUrl("/movies/" + id), HttpMethod.DELETE, null, String.class);
    }

    public int countAll() {
        Integer listResponse = restTemplate.getForObject(fullUrl("/count"), Integer.class);

        return listResponse;
    }

    public int count(String field, String searchTerm) {
        Integer createResponse =  restTemplate.getForObject(fullUrl("/count/?field={field}&searchTerm={searchTerm}"),
                Integer.class,
                field,
                searchTerm
        );

        return createResponse;
    }

    public List<MovieInfo> findAll(int firstResult, int maxResults) {
        ParameterizedTypeReference<List<MovieInfo>> typeReference = new ParameterizedTypeReference<List<MovieInfo>>(){};
        ResponseEntity<List<MovieInfo>> result = restTemplate.exchange(fullUrl("/movies?firstResult={1}&maxResults={2}"), HttpMethod.GET, null, typeReference, firstResult, maxResults);
        return result.getBody();
    }

    public List<MovieInfo> findRange(String field, String searchTerm, int firstResult, int maxResults) {
        ParameterizedTypeReference<List<MovieInfo>> typeReference = new ParameterizedTypeReference<List<MovieInfo>>(){};
        ResponseEntity<List<MovieInfo>> result = restTemplate.exchange(fullUrl("/movies?field={1}&searchTerm={2}&firstResult={3}&maxResults={4}"), HttpMethod.GET, null, typeReference, field, searchTerm, firstResult, maxResults);
        return result.getBody();

    }

    public List<MovieInfo> getMovies() {
        return findAll(0, Integer.MAX_VALUE);
    }

}
