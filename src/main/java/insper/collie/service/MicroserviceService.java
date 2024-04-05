package insper.collie.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MicroserviceService {

    @Autowired
    private MicroserviceRepository microserviceRepository;

    public MicroserviceModel createMicroservice(MicroserviceModel microservice) {
        return microserviceRepository.save(microservice);
    }

    public MicroserviceModel getMicroservice(String id) {
        // convert string id to ID
        return microserviceRepository.findById(id);
    }

    public List<MicroserviceModel> listAllMicroservices() {
        return microserviceRepository.findAll();
    }


    public MicroserviceModel updateMicroservice(String id, MicroserviceModel microservice) {
        MicroserviceModel existingMicroservice = microserviceRepository.findById(id);
        existingMicroservice.name(microservice.name());
        existingMicroservice.squadResponsavel(microservice.squadResponsavel());
        return microserviceRepository.save(existingMicroservice);
    }


    public void deleteMicroservice(String id) {
        microserviceRepository.deleteById(id);
    }
}