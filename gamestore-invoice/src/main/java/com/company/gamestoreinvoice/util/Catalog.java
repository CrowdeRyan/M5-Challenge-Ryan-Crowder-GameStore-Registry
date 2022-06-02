package com.company.gamestoreinvoice.util;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name="gamestore-invoice")
public interface Catalog {

    @RequestMapping(value="/game/{id}", method = RequestMethod.GET)
    public  Optional<Game> getGameById(@RequestParam long id);

    @RequestMapping(value="/console/{id}", method=RequestMethod.GET)
    public Optional<Console> getConsoleById(@RequestParam long id);

    @RequestMapping(value="/tshirt/{id}", method=RequestMethod.GET)
    public Optional<Tshirt> getTshirtById(@RequestParam long id);
}
