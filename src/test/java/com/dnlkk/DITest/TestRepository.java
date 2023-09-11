package com.dnlkk.DITest;

import java.util.List;

import com.dnlkk.dependency_injector.annotations.components.Repository;
import com.dnlkk.repository.DnlkkRepository;

import lombok.Data;

@Repository
public interface TestRepository extends DnlkkRepository<Integer, User>{
    List<User> findByNameAndSurnameOrId(String name, String surname, Integer id);
    List<User> findByName(String name);
}