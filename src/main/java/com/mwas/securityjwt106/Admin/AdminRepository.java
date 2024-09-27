package com.mwas.securityjwt106.Admin;

import com.mwas.securityjwt106.Clients.Web_Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Allfiles,Integer> {

}
