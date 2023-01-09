package monprojet.dao;

import monprojet.dto.PopulationParPays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import monprojet.entity.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

@Log4j2 // Génère le 'logger' pour afficher les messages de trace
@DataJpaTest
public class CountryRepositoryTest {

    @Autowired
    private CountryRepository countryDAO;

    @Test
    void lesNomsDePaysSontTousDifferents() {
        log.info("On vérifie que les noms de pays sont tous différents ('unique') dans la table 'Country'");
        
        Country paysQuiExisteDeja = new Country("XX", "France");
        try {
            countryDAO.save(paysQuiExisteDeja); // On essaye d'enregistrer un pays dont le nom existe   

            fail("On doit avoir une violation de contrainte d'intégrité (unicité)");
        } catch (DataIntegrityViolationException e) {
            // Si on arrive ici c'est normal, l'exception attendue s'est produite
        }
    }

    @Test
    @Sql("test-data.sql") // On peut charger des donnnées spécifiques pour un test
    void onSaitCompterLesEnregistrements() {
        log.info("On compte les enregistrements de la table 'Country'");
        int combienDePaysDansLeJeuDeTest = 3 + 1; // 3 dans data.sql, 1 dans test-data.sql
        long nombre = countryDAO.count();
        assertEquals(combienDePaysDansLeJeuDeTest, nombre, "On doit trouver 4 pays" );
    }

    @Test
    @Sql("test-data.sql")
    void testPopulationDunPays(){
        log.info("On vérifie que la population d'un pays est bien la sommes de la population de ses villes");
        assertEquals(countryDAO.populationPourChaquePays(1),12,"on doit trouver 12 pour la France");
    }

    @Test
    @Sql("test-data.sql")
    void testPopulationParPays() {
        log.info("On verifie le nombre de ligne de la liste,il doit correspondra au nombre de nombre de pays");
        assertEquals(countryDAO.populationParPays().size(), 3, "le nombre de ligne est égal à celui du pays");
        log.info("On vérifie que la valeur de la population pour le premier pays (france) correspond bien");
        assertEquals(countryDAO.populationParPays().get(0).getPopulation(), 12,"Test du 1er pays de la liste : la France");
    }

}
