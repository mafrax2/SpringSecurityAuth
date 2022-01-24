package com.openclassrooms.SpringSecurityAuth.repository;


import com.openclassrooms.SpringSecurityAuth.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    Optional<Account> findByEmail(String email);


}


