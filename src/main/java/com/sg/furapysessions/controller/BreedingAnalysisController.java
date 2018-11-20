/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.furapysessions.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Ryan
 */
@Controller
public class BreedingAnalysisController {

    @RequestMapping(value = "/displayDogBreed", method = RequestMethod.GET)
    public String displayDogBreed() {
        return "breedingAnalysis";
    }

    @RequestMapping(value = "/analyzeName", method = RequestMethod.GET)
    public String analyzePetName(HttpServletRequest request, Model model) {
        
        int randomNum;
        int breedsLeft = 4;
        int percentLeft = 100;
        List<String> results = new ArrayList<>();
        String[] dogArray = {"Boxer", "Shiba Inu", "Border Collie", "Pembroke Welsh Corgi", "Siberian Husky"};
        
        Random rng = new Random();

        for (int i = 0; i < dogArray.length; i++) {
            if(breedsLeft != 0){
                randomNum = rng.nextInt(percentLeft - breedsLeft) + 1;
                percentLeft = percentLeft - randomNum;
                breedsLeft--;
                results.add(randomNum + "% " + dogArray[i]);
            } else{
                results.add(percentLeft + "% " + dogArray[i]);
            }
            
        }
        
        model.addAttribute("results", results);

        return "breedingAnalysis";         
    }

    @RequestMapping(value = "/resetForm", method = RequestMethod.GET)
    public String resetResults() {

        return "redirect:breedingAnalysis";
    }
}