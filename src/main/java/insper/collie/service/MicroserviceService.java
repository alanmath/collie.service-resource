package insper.collie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import insper.collie.service.exceptions.MicroserviceNotFoundException;
import insper.collie.squad.SquadAllInfo;
import insper.collie.squad.SquadController;
import insper.collie.squad.SquadInfo;
import jakarta.transaction.Transactional;

import insper.collie.squad.exceptions.RequestErrorException;
import insper.collie.squad.exceptions.SquadNotFoundException;


@Service
public class MicroserviceService {

    @Autowired
    private MicroserviceRepository microserviceRepository;


    @Autowired
    private SquadController squadController;
    
    @Transactional
    public MicroserviceModel createMicroservice(MicroserviceModel microservice) {

        ResponseEntity<Boolean> responseSquad = squadController.isSquad(microservice.squadResponsavel());

        // verifica se o squad de fato existe
        if (responseSquad != null){
            if (!responseSquad.getBody()){
                throw new SquadNotFoundException(microservice.squadResponsavel());
            }
        }else throw new RequestErrorException("Squad");
        

        return microserviceRepository.save(microservice);
    }
    @Transactional
    public MicroserviceAll getMicroservice(String id) {
            Optional<MicroserviceModel> m = microserviceRepository.findById(id);
            if (!m.isPresent()){
                throw new MicroserviceNotFoundException(id);
            }
            
            Microservice microservice = m.get().to();
            ResponseEntity<SquadAllInfo> response = squadController.getSquad(microservice.squadResponsavel());
            if (response == null) throw new SquadNotFoundException(microservice.squadResponsavel());
    
            SquadAllInfo squad = response.getBody();

            return  MicroserviceParser.toAll(microservice, squad);

        }
    @Transactional
    public List<MicroserviceModel> listAllMicroservices() {
        return microserviceRepository.findAll();
    }

    @Transactional
    public MicroserviceModel updateMicroservice(String id, MicroserviceModel in) {
        MicroserviceModel m = microserviceRepository.findById(id).orElse(null);
        if (m == null) throw new MicroserviceNotFoundException(id);

        MicroserviceModel microservice = m;

        if (in.name() != null)  microservice.name(in.name());

        if (in.linkRepositorio() != null)  microservice.linkRepositorio(in.linkRepositorio());


        if(in.squadResponsavel() != null){
            ResponseEntity<Boolean> responseSquad = squadController.isSquad(in.squadResponsavel());
            if (responseSquad != null){
                if (!responseSquad.getBody()){
                    throw new SquadNotFoundException(in.squadResponsavel());
                }
            }else throw new RequestErrorException("Squad");
            microservice.squadResponsavel(in.squadResponsavel());
        }

        return microserviceRepository.save(microservice);
    }

    @Transactional
    public void deleteMicroservice(String id) {
        if (microserviceRepository.existsById(id)) microserviceRepository.deleteById(id);
        throw new MicroserviceNotFoundException(id);
    }
}