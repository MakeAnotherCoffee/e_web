package com.mwas.securityjwt106.Clients;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Web_Users,Integer> {
}
