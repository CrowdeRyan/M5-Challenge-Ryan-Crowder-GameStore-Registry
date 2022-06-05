package com.company.gamestoreinvoice.util;


import com.company.gamestoreinvoice.viewModel.ConsoleViewModel;
import com.company.gamestoreinvoice.viewModel.GameViewModel;
import com.company.gamestoreinvoice.viewModel.TshirtViewModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name="gamestore-invoice")
public interface Catalog {


    @GetMapping("/console/{id}")
    public ConsoleViewModel getConsole(@PathVariable("id") long consoleId);

    @GetMapping("/game/{id}")
    public GameViewModel getGame(@PathVariable("id") long gameId);

    @GetMapping("/tshirt/{id}")
    public TshirtViewModel getTShirt(@PathVariable("id") long tShirtId);
}
