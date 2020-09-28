package com.codegym.service;

import com.codegym.model.Smartphone;

public interface SmartphoneService {
    Iterable<Smartphone> findAll();
    Smartphone findById(Long id);
    Smartphone save(Smartphone phone);
    void remove(Long id);
}