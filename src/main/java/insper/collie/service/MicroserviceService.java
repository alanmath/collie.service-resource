package insper.collie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import insper.collie.service.exceptions.MicroserviceNotFoundException;
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
            Optional<MicroserviceModel> microservice = microserviceRepository.findById(id);
            if (microservice.isPresent()){
                return microservice.get().to();
            }
            throw new MicroserviceNotFoundException(id);
        }
    @Transactional
    public List<MicroserviceModel> listAllMicroservices() {
        return microserviceRepository.findAll();
    }

    @Transactional
    public Microservice updateMicroservice(String id, MicroserviceModel in) {
        MicroserviceModel m = microserviceRepository.findById(id).orElse(null);
        if (m == null) throw new MicroserviceNotFoundException(id);

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
        if (microserviceRepository.existsById(id)) microserviceRepository.deleteById(id);
        throw new MicroserviceNotFoundException(id);
    }
}