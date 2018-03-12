package com.warumono.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.warumono.auth.entities.OAuthClientDetails;

public interface OAuth2CleintDetailsRepository extends JpaRepository<OAuthClientDetails, Long>
{

}
