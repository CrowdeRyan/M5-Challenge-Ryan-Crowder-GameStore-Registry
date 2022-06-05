package com.company.gamestorecatalog.repository;


import com.company.gamestorecatalog.model.TShirt;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TShirtRepositoryTest {

    @Autowired
    TShirtRepository tShirtRepository;

    @Before
    public void setUp() throws Exception {
        tShirtRepository.deleteAll();
    }

    @Test
    public void shouldAddFindDeleteTShirt() {

        TShirt newTShirt = new TShirt();
        newTShirt.setQuantity(1);
        newTShirt.setPrice( new BigDecimal("10.05"));
        newTShirt.setDescription("Everybody Knows Your Name");
        newTShirt.setColor("SkyBlue");
        newTShirt.setSize("M");

        newTShirt = tShirtRepository.save(newTShirt);
        TShirt savedTShirt = tShirtRepository.save(newTShirt);

        assertEquals(newTShirt, savedTShirt);

        newTShirt.setQuantity(5);
        newTShirt.setDescription("Aint nobody got time for that!");

        tShirtRepository.save(newTShirt);
        savedTShirt = tShirtRepository.save(newTShirt);

        assertEquals(newTShirt, savedTShirt);

        tShirtRepository.deleteById(newTShirt.getId());
        Optional<TShirt> foundTShirt = tShirtRepository.findById(newTShirt.getId());

        assertFalse(foundTShirt.isPresent());
    }

    @Test
    public void shouldFindAllTShirt(){
        TShirt newTShirt1 = new TShirt();
        newTShirt1.setQuantity(1);
        newTShirt1.setPrice( new BigDecimal("10.05"));
        newTShirt1.setDescription("Everybody Knows Your Name");
        newTShirt1.setColor("SkyBlue");
        newTShirt1.setSize("M");

        TShirt newTShirt2 = new TShirt();
        newTShirt2.setQuantity(11);
        newTShirt2.setPrice( new BigDecimal("15.00"));
        newTShirt2.setDescription("I am not always right...");
        newTShirt2.setColor("Pink");
        newTShirt2.setSize("S");

        newTShirt1 = tShirtRepository.save(newTShirt1);
        newTShirt2 = tShirtRepository.save(newTShirt2);
        List<TShirt> allTShirt = new ArrayList();
        allTShirt.add(newTShirt1);
        allTShirt.add(newTShirt2);

        List<TShirt> foundAllTShirt = tShirtRepository.findAll();

        assertEquals(allTShirt.size(),foundAllTShirt.size());
    }

    @Test
    public void shouldFindTShirtByColor(){
        TShirt newTShirt1 = new TShirt();
        newTShirt1.setQuantity(1);
        newTShirt1.setPrice( new BigDecimal("10.05"));
        newTShirt1.setDescription("Everybody Knows Your Name");
        newTShirt1.setColor("SkyBlue");
        newTShirt1.setSize("M");

        TShirt newTShirt2 = new TShirt();
        newTShirt2.setQuantity(11);
        newTShirt2.setPrice( new BigDecimal("15.00"));
        newTShirt2.setDescription("I am not always right...");
        newTShirt2.setColor("Pink");
        newTShirt2.setSize("S");

        TShirt newTShirt3 = new TShirt();
        newTShirt3.setQuantity(9);
        newTShirt3.setPrice( new BigDecimal("19.00"));
        newTShirt3.setDescription("Trust me I am a Pro...crastinator");
        newTShirt3.setColor("Pink");
        newTShirt3.setSize("L");

        newTShirt1 = tShirtRepository.save(newTShirt1);
        newTShirt2 = tShirtRepository.save(newTShirt2);
        newTShirt3 = tShirtRepository.save(newTShirt3);

        List<TShirt> foundNoTShirt = tShirtRepository.findAllByColor("InvalidValue");

        List<TShirt> foundOneTShirt = tShirtRepository.findAllByColor("SkyBlue");

        List<TShirt> foundTwoTShirt = tShirtRepository.findAllByColor("Pink");

        assertEquals(foundNoTShirt.size(),0);
        assertEquals(foundOneTShirt.size(),1);
        assertEquals(foundTwoTShirt.size(),2);

    }
    @Test
    public void shouldFindTShirtBySize(){
        TShirt newTShirt1 = new TShirt();
        newTShirt1.setQuantity(1);
        newTShirt1.setPrice( new BigDecimal("10.05"));
        newTShirt1.setDescription("Everybody Knows Your Name");
        newTShirt1.setColor("SkyBlue");
        newTShirt1.setSize("L");

        TShirt newTShirt2 = new TShirt();
        newTShirt2.setQuantity(11);
        newTShirt2.setPrice( new BigDecimal("15.00"));
        newTShirt2.setDescription("I am not always right...");
        newTShirt2.setColor("Pink");
        newTShirt2.setSize("S");

        TShirt newTShirt3 = new TShirt();
        newTShirt3.setQuantity(9);
        newTShirt3.setPrice( new BigDecimal("19.00"));
        newTShirt3.setDescription("Trust me I am a Pro...crastinator");
        newTShirt3.setColor("Pink");
        newTShirt3.setSize("L");

        newTShirt1 = tShirtRepository.save(newTShirt1);
        newTShirt2 = tShirtRepository.save(newTShirt2);
        newTShirt3 = tShirtRepository.save(newTShirt3);

        List<TShirt> foundNoTShirt = tShirtRepository.findAllBySize("InvalidValue");

        List<TShirt> foundOneTShirt = tShirtRepository.findAllBySize("S");

        List<TShirt> foundTwoTShirt = tShirtRepository.findAllBySize("L");

        assertEquals(foundNoTShirt.size(),0);
        assertEquals(foundOneTShirt.size(),1);
        assertEquals(foundTwoTShirt.size(),2);
    }
}