package com.ilnur.WeatherBot.Repository;

import com.ilnur.WeatherBot.Entities.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Component
public interface UserCityRepository extends CrudRepository<City, Long> {
}
