package com.codegym.controller;

import com.codegym.model.Producer;
import com.codegym.model.Smartphone;
import com.codegym.repository.ProducerRepository;
import com.codegym.service.SmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/smartphones")
public class SmartphoneController {

    @Autowired
    private SmartphoneService smartphoneService;

    @Autowired
    private ProducerRepository producerRepository;


    @ModelAttribute("producers")
    public Iterable<Producer> producers(){
        return producerRepository.findAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET )
    public ModelAndView createSmartphonePage() {
        ModelAndView mav = new ModelAndView("phone/new-phone");
        mav.addObject("sPhone", new Smartphone());
        return mav;
    }

//    @RequestMapping(value = "/create", method = RequestMethod.POST,
//            produces = MediaType.APPLICATION_JSON_VALUE,
//            consumes = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping("/create")
    public ResponseEntity<Smartphone> createSmartphone(@RequestBody Smartphone smartphone) {
        smartphone.setProducer(producerRepository.findOne(smartphone.getProducer().getId()));
        smartphoneService.save(smartphone);
//        return new ResponseEntity<>(smartphone,HttpStatus.OK);
        return ResponseEntity.ok(smartphone);
    }

    @GetMapping("")
    public ModelAndView allPhonesPage() {
        ModelAndView modelAndView = new ModelAndView("phone/all-phones");
        modelAndView.addObject("allphones", smartphoneService.findAll());
        return modelAndView;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Smartphone> deleteSmartphone(@PathVariable Long id){
        Smartphone smartphone = smartphoneService.findById(id);
        smartphoneService.remove(id);
        return ResponseEntity.ok(smartphone);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editSmartphonePage(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("phone/edit-phone");
        Smartphone smartphone = smartphoneService.findById(id);
        mav.addObject("sPhone", smartphone);
        return mav;
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Smartphone> editSmartphone(@PathVariable long id, @RequestBody Smartphone smartphone) {
        smartphone.setId(id);
        return ResponseEntity.ok(smartphoneService.save(smartphone));
    }
}
