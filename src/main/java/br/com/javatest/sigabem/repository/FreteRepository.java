package br.com.javatest.sigabem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.javatest.sigabem.model.Frete;

@Repository
public interface FreteRepository extends JpaRepository<Frete, Long> {

}