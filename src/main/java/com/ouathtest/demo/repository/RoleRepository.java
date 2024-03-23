package com.ouathtest.demo.repository;

import com.ouathtest.demo.domain.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long>{
    Optional<RoleEntity> findByName(String name);

    List<RoleEntity> findAllByNameIn(List<String> name);

}
