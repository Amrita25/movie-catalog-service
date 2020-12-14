package io.springboot.moviecatalogservice.services;

import io.springboot.moviecatalogservice.models.Rating;
import io.springboot.moviecatalogservice.models.UserRating;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingInfoService {
	
	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod="getFallbackUserRating")
	public UserRating getUserRating(String userID) {
		UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/"+userID, UserRating.class);
		return ratings;
	}
	
	public UserRating getFallbackUserRating(String userID){
		UserRating rating = new UserRating();
		rating.setUserId(userID);
		rating.setUserRatings(Arrays.asList(new Rating("0",0)));
		return rating;
	}

}
