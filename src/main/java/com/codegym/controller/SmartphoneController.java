package com.codegym.controller;

import com.codegym.model.Producer;
import com.codegym.model.Smartphone;
import com.codegym.repository.ProducerRepository;
import com.codegym.service.SmartphoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping(value = "/smartphones")
public class SmartphoneController {
    @Autowired
    private Environment environment;

    @Autowired
    private SmartphoneService smartphoneService;

    @Autowired
    ProducerRepository producerRepository;


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

    @PostMapping("/create")
    public ResponseEntity<Smartphone> createSmartphone(Smartphone smartphone) {
        //1 gan student nhung thuoc tinh cua studentForm

        System.out.println(smartphone);
        MultipartFile file = smartphone.getImage();
        String image = file.getOriginalFilename();
        String fileUpload = environment.getProperty("file_upload");
        try {
            FileCopyUtils.copy(image.getBytes(), new File(fileUpload + image));
        } catch (IOException e) {
            e.printStackTrace();
        }
        smartphoneService.save(smartphone);
        return ResponseEntity.ok().body(smartphone);
    }

    @GetMapping("")
    public ModelAndView allPhonesPage() {
        ModelAndView modelAndView = new ModelAndView("phone/all-phones");
        modelAndView.addObject("allphones", smartphoneService.findAll());
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSmartphone(@PathVariable Long id){
        smartphoneService.remove(id);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editSmartphonePage(@PathVariable long id) {
        ModelAndView mav = new ModelAndView("phone/edit-phone");
        Smartphone smartphone = smartphoneService.findById(id);
        mav.addObject("sPhone", smartphone);
        return mav;
    }

    @RequestMapping(value = "/edit/{id}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Smartphone editSmartphone(@PathVariable long id, @RequestBody Smartphone smartphone) {
        smartphone.setId(id);
        return smartphoneService.save(smartphone);
    }
}
