package com.codeup.springproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DiceGameController {

    @GetMapping("/roll-dice")
    public String diceGame() {
        return "/roll-dice";
    }

    @GetMapping("/roll-dice/{num}")
        public String diceGuess(@PathVariable int num, Model model) {
        model.addAttribute("num", num);
        int randomNum = (int)(Math.random() * ((6 - 1) + 1)) + 1;
        boolean isCorrect = randomNum == num;
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("randomNum", randomNum);
        return "/userGuess";
    }

}
