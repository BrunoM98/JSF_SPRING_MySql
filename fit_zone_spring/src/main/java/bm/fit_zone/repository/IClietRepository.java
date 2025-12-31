package bm.fit_zone.repository;

import bm.fit_zone.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClietRepository extends JpaRepository<Client, Integer> {

}
