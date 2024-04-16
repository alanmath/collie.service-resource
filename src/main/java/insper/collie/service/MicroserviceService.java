package insper.collie.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.qos.logback.classic.Logger;
import insper.collie.service.exceptions.MicroserviceNotFoundException;
import insper.collie.squad.SquadAllInfo;
import insper.collie.squad.SquadController;
import insper.collie.squad.SquadInfo;
import jakarta.transaction.Transactional;

import insper.collie.squad.exceptions.RequestErrorException;
import insper.collie.squad.exceptions.SquadNotFoundException;


@Service
public class MicroserviceService {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(TrivyReportIn.class);


    @Autowired
    private MicroserviceRepository microserviceRepository;


    @Autowired
    private SquadController squadController;
    
    @Transactional
    public MicroserviceModel createMicroservice(MicroserviceModel microservice) {

        ResponseEntity<Boolean> responseSquad = squadController.isSquad(microservice.squad_id());

        // verifica se o squad de fato existe
        if (responseSquad != null){
            if (!responseSquad.getBody()){
                throw new SquadNotFoundException(microservice.squad_id());
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
            ResponseEntity<SquadAllInfo> response = squadController.getSquad(microservice.squad_id());
            if (response == null) throw new SquadNotFoundException(microservice.squad_id());
    
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

        if (in.linkRepository() != null)  microservice.linkRepository(in.linkRepository());


        if(in.squad_id() != null){
            ResponseEntity<Boolean> responseSquad = squadController.isSquad(in.squad_id());
            if (responseSquad != null){
                if (!responseSquad.getBody()){
                    throw new SquadNotFoundException(in.squad_id());
                }
            }else throw new RequestErrorException("Squad");
            microservice.squad_id(in.squad_id());
        }

        return microserviceRepository.save(microservice);
    }

    @Transactional
    public void deleteMicroservice(String id) {
        if (microserviceRepository.existsById(id)) microserviceRepository.deleteById(id);
        throw new MicroserviceNotFoundException(id);
    }

    public void trivyReport(String id, TrivyReportIn in){
        // log the data that is in the trivyReport
        System.out.println(in);

        logger.info("Trivy Report added to microservice: {}", id);

        logger.info("Passou aqui, in: {}", in);


    }
}