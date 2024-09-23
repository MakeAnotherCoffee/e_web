package com.mwas.securityjwt106.Clients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Web_Users,Integer> {
    Optional<Web_Users> findByUsername(String username);
}
