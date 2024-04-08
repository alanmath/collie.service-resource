package insper.collie.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MicroserviceService {

    @Autowired
    private MicroserviceRepository microserviceRepository;
    
    @Transactional
    public MicroserviceModel createMicroservice(MicroserviceModel microservice) {
        return microserviceRepository.save(microservice);
    }
    @Transactional
    public Microservice getMicroservice(String id) {
        return microserviceRepository.findById(id).map(MicroserviceModel::to).orElse(null);

    }
    @Transactional
    public List<MicroserviceModel> listAllMicroservices() {
        return microserviceRepository.findAll();
    }

    @Transactional
    public Microservice updateMicroservice(String id, MicroserviceModel in) {
        MicroserviceModel m = microserviceRepository.findById(id).orElse(null);
        if (m == null){
            return null;
        }

        MicroserviceModel microservice = m;

        if (in.name() != null) {
            microservice.name(in.name());
        }
        if(in.squadResponsavel() != null){
            microservice.squadResponsavel(in.squadResponsavel());
        }

        return microserviceRepository.save(microservice).to();
    }

    @Transactional
    public void deleteMicroservice(String id) {
        microserviceRepository.deleteById(id);
    }
}