package io.springboot.moviecatalogservice.resources;

import io.springboot.moviecatalogservice.models.CatalogItem;
import io.springboot.moviecatalogservice.models.Movie;
import io.springboot.moviecatalogservice.models.Rating;




import io.springboot.moviecatalogservice.models.UserRating;

import java.util.*;
import java.util.stream.Collectors;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userID){
		//get all rated movie ids
		
		System.out.println("inside");		
		UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userID, UserRating.class);
		return ratings.getUserRatings().stream().map(rating->{
			//for each movieId call movie info service and get details
			Movie movie=restTemplate.getForObject("http://localhost:8082/movies/"+rating.getMovieId(), Movie.class);
			//put them all together
			return new CatalogItem(movie.getMovieName(),"Desc",rating.getRating());
		})
		.collect(Collectors.toList());
	}

}
