package com.springboot.bunch.repository;

import com.springboot.bunch.entity.Bunch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BunchRepository extends JpaRepository<Bunch, Long> {
}
