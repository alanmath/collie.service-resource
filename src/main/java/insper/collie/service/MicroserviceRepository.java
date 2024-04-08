package insper.collie.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroserviceRepository extends JpaRepository<MicroserviceModel, String> {



    List<MicroserviceModel> findBySquadResponsavel(String squadResponsavel);

    void deleteById(String id);
}