package com.company.gamestorecatalog.repository;


import com.company.gamestorecatalog.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll();
    }

    @Test
    public void shouldAddFindDeleteGame() {

        Game newGame = new Game();
        newGame.setQuantity(1);
        newGame.setPrice( new BigDecimal("10.05"));
        newGame.setDescription("Fun Game for all");
        newGame.setEsrbRating("R18");
        newGame.setStudio("AE");
        newGame.setTitle("SimCopter");

        newGame = gameRepository.save(newGame);
        Optional<Game> foundCon = gameRepository.findById(newGame.getId());

        assertTrue(foundCon.isPresent());

        newGame.setQuantity(5);
        newGame.setDescription("Suspense in the air");

        gameRepository.save(newGame);
        foundCon = gameRepository.findById(newGame.getId());

        assertTrue(foundCon.isPresent());

        gameRepository.deleteById(newGame.getId());
        foundCon = gameRepository.findById(newGame.getId());

        assertFalse(foundCon.isPresent());
    }

    @Test
    public void shouldFindAllGame(){
        Game newGame1 = new Game();
        newGame1.setQuantity(1);
        newGame1.setPrice( new BigDecimal("10.05"));
        newGame1.setDescription("Fun Game for all");
        newGame1.setEsrbRating("R18");
        newGame1.setStudio("AE");
        newGame1.setTitle("SimCopter");

        Game newGame2 = new Game();
        newGame2.setQuantity(1);
        newGame2.setPrice( new BigDecimal("20.05"));
        newGame2.setDescription("Strategy 4 Everyone");
        newGame2.setEsrbRating("E");
        newGame2.setStudio("YoYo");
        newGame2.setTitle("FortLine");

        newGame1 = gameRepository.save(newGame1);
        newGame2 = gameRepository.save(newGame2);
        List<Game> allGame = new ArrayList();
        allGame.add(newGame1);
        allGame.add(newGame2);

        List<Game> foundAllGame = gameRepository.findAll();

        assertEquals(allGame.size(),foundAllGame.size());
    }

    @Test
    public void shouldFindGameByEsrb(){
        Game newGame1 = new Game();
        newGame1.setQuantity(1);
        newGame1.setPrice( new BigDecimal("10.05"));
        newGame1.setDescription("Fun Game for all");
        newGame1.setEsrbRating("E");
        newGame1.setStudio("AE");
        newGame1.setTitle("SimCopter");

        Game newGame2 = new Game();
        newGame2.setQuantity(7);
        newGame2.setPrice( new BigDecimal("20.05"));
        newGame2.setDescription("Strategy 4 Everyone");
        newGame2.setEsrbRating("E");
        newGame2.setStudio("YoYo");
        newGame2.setTitle("FortLine");

        Game newGame3 = new Game();
        newGame3.setQuantity(3);
        newGame3.setPrice( new BigDecimal("19.05"));
        newGame3.setDescription("NeverLand");
        newGame3.setEsrbRating("PG");
        newGame3.setStudio("GameMaker");
        newGame3.setTitle("Hook");
        newGame1 = gameRepository.save(newGame1);
        newGame2 = gameRepository.save(newGame2);
        newGame3 = gameRepository.save(newGame3);

        List<Game> foundNoGame = gameRepository.findAllByEsrbRating("InvalidValue");

        List<Game> foundOneGame = gameRepository.findAllByEsrbRating("PG");

        List<Game> foundTwoGame = gameRepository.findAllByEsrbRating("E");

        assertEquals(foundNoGame.size(),0);
        assertEquals(foundOneGame.size(),1);
        assertEquals(foundTwoGame.size(),2);
    }

    @Test
    public void shouldFindGameByStudio(){
        Game newGame1 = new Game();
        newGame1.setQuantity(1);
        newGame1.setPrice( new BigDecimal("10.05"));
        newGame1.setDescription("Fun Game for all");
        newGame1.setEsrbRating("E");
        newGame1.setStudio("AE");
        newGame1.setTitle("SimCopter");

        Game newGame2 = new Game();
        newGame2.setQuantity(7);
        newGame2.setPrice( new BigDecimal("20.05"));
        newGame2.setDescription("Strategy 4 Everyone");
        newGame2.setEsrbRating("E");
        newGame2.setStudio("YoYo");
        newGame2.setTitle("FortLine");

        Game newGame3 = new Game();
        newGame3.setQuantity(3);
        newGame3.setPrice( new BigDecimal("19.05"));
        newGame3.setDescription("NeverLand");
        newGame3.setEsrbRating("PG");
        newGame3.setStudio("AE");
        newGame3.setTitle("Hook");

        newGame1 = gameRepository.save(newGame1);
        newGame2 = gameRepository.save(newGame2);
        newGame3 = gameRepository.save(newGame3);

        List<Game> foundNoGame = gameRepository.findAllByStudio("InvalidValue");

        List<Game> foundOneGame = gameRepository.findAllByStudio("YoYo");

        List<Game> foundTwoGame = gameRepository.findAllByStudio("AE");

        assertEquals(foundNoGame.size(),0);
        assertEquals(foundOneGame.size(),1);
        assertEquals(foundTwoGame.size(),2);
    }

    @Test
    public void shouldFindGameByTitle(){
        Game newGame1 = new Game();
        newGame1.setQuantity(1);
        newGame1.setPrice( new BigDecimal("10.05"));
        newGame1.setDescription("Fun Game for all");
        newGame1.setEsrbRating("E");
        newGame1.setStudio("AE");
        newGame1.setTitle("SimCopter");

        Game newGame2 = new Game();
        newGame2.setQuantity(7);
        newGame2.setPrice( new BigDecimal("20.05"));
        newGame2.setDescription("Strategy 4 Everyone");
        newGame2.setEsrbRating("E");
        newGame2.setStudio("YoYo");
        newGame2.setTitle("FortLine");

        Game newGame3 = new Game();
        newGame3.setQuantity(3);
        newGame3.setPrice( new BigDecimal("19.05"));
        newGame3.setDescription("NeverLand");
        newGame3.setEsrbRating("PG");
        newGame3.setStudio("AE");
        newGame3.setTitle("Hook");

        newGame1 = gameRepository.save(newGame1);
        newGame2 = gameRepository.save(newGame2);
        newGame3 = gameRepository.save(newGame3);

        List<Game> foundNoGame = gameRepository.findAllByTitle("InvalidValue");

        List<Game> foundOneGame = gameRepository.findAllByTitle("Hook");

        assertEquals(foundNoGame.size(),0);
        assertEquals(foundOneGame.size(),1);
    }
}