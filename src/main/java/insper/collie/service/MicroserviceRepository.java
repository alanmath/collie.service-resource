package insper.collie.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroserviceRepository extends JpaRepository<MicroserviceModel, Long> {

    //find by id
    MicroserviceModel findById(String id);

    List<MicroserviceModel> findBySquadResponsavel(String squadResponsavel);

    void deleteById(String id);
}