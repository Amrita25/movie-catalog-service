package io.springboot.moviecatalogservice.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import io.springboot.moviecatalogservice.models.CatalogItem;
import io.springboot.moviecatalogservice.models.Movie;
import io.springboot.moviecatalogservice.models.Rating;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class MovieInfoService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackCatalogItem") // when the circuit breaks, getFallbackCatalog method needs to be called
	public CatalogItem getCatalogItem(Rating rating) {
		Movie movie=restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
		//put them all together
		return new CatalogItem(movie.getMovieName(),"Movie Desc",rating.getRating());
	}
	public CatalogItem getFallbackCatalogItem(Rating rating){
		return new CatalogItem("Movie Name not Found !!", "", 0);
	}

}
