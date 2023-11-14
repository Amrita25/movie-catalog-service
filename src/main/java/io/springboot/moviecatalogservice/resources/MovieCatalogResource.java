package io.springboot.moviecatalogservice.resources;

import io.springboot.moviecatalogservice.models.CatalogItem;
import io.springboot.moviecatalogservice.models.Movie;
import io.springboot.moviecatalogservice.models.Rating;




import io.springboot.moviecatalogservice.models.UserRating;
import io.springboot.moviecatalogservice.services.MovieInfoService;
import io.springboot.moviecatalogservice.services.UserRatingInfoService;

import java.util.*;
import java.util.stream.Collectors;












import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	MovieInfoService movieInfo;
	@Autowired
	UserRatingInfoService userRatingInfo;
	
	
	public void sayHello(){
		System.out.println("Hello World");
	}
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userID){
		//get all rated movie ids ......
		
		System.out.println("inside");		
		UserRating ratings = userRatingInfo.getUserRating(userID);
		return ratings.getUserRatings().stream().map(rating-> movieInfo.getCatalogItem(rating))
												.collect(Collectors.toList());
	}

}
