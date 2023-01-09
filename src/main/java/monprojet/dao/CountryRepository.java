package monprojet.dao;

import java.util.List;

import monprojet.dto.PopulationParPays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import monprojet.entity.City;
import monprojet.entity.Country;

// This will be AUTO IMPLEMENTED by Spring 

public interface CountryRepository extends JpaRepository<Country, Integer> {
    @Query(value = "SELECT SUM(city.population)"
            +" FROM city"
            +" WHERE city.country_id = :iddupays", nativeQuery = true)
    public Integer populationPourChaquePays(int iddupays);


    @Query(value = "SELECT country.name AS name , SUM(city.population) as population"
            +" FROM city INNER JOIN country ON city.country_id = country.id"
            +" GROUP BY country.name;"
            ,nativeQuery = true )
    public List<PopulationParPays> populationParPays();
}
