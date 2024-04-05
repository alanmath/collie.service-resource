package insper.collie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.qos.logback.classic.Logger;

@RestController
public class MicroserviceResource implements MicroserviceController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(MicroserviceResource.class);
    @Autowired
    private MicroserviceService microserviceService;

    @Override
    public ResponseEntity<MicroserviceOut> create(MicroserviceIn microserviceRegisterIn) {
        final MicroserviceModel microservice = microserviceService.createMicroservice(MicroserviceModel.builder()
            .name(microserviceRegisterIn.name())
            .squadResponsavel(microserviceRegisterIn.squadResponsavel())
            .build()
        );
        logger.info("Microservice created: {}", microservice);
        return ResponseEntity.ok(MicroserviceOut.builder()
            .id(microservice.id())
            .name(microservice.name())
            .squadResponsavel(microservice.squadResponsavel())
            .build()
        );
    }
    
    
    @Override
    public ResponseEntity<MicroserviceOut> readById(String id) {
        final MicroserviceModel microservice = microserviceService.getMicroservice(id);
        logger.info("Microservice found: {}", microservice);
        return ResponseEntity.ok(MicroserviceOut.builder()
            .id(microservice.id())
            .name(microservice.name())
            .squadResponsavel(microservice.squadResponsavel())
            .build()
        );
    }

    @Override
    public ResponseEntity<MicroserviceOut> update(String id, MicroserviceIn microserviceRegisterIn) {
        final MicroserviceModel microservice = microserviceService.updateMicroservice(id, MicroserviceModel.builder()
            .name(microserviceRegisterIn.name())
            .squadResponsavel(microserviceRegisterIn.squadResponsavel())
            .build()
        );
        logger.info("Microservice updated: {}", microservice);
        return ResponseEntity.ok(MicroserviceOut.builder()
            .id(microservice.id())
            .name(microservice.name())
            .squadResponsavel(microservice.squadResponsavel())
            .build()
        );
    }

    @Override
    public ResponseEntity<MicroserviceOut> delete(String id) {
        microserviceService.deleteMicroservice(id);
        logger.info("Microservice deleted: {}", id);
        // return the microservice that was deleted
        return ResponseEntity.ok(MicroserviceOut.builder()
            .id(id)
            .build()
        );
    }

    @Override
    public ResponseEntity<List<MicroserviceOut>> read() {
        final List<MicroserviceModel> microservices = microserviceService.listAllMicroservices();
        logger.info("Microservices found: {}", microservices);
        return ResponseEntity.ok(microservices.stream()
            .map(microservice -> MicroserviceOut.builder()
                .id(microservice.id())
                .name(microservice.name())
                .squadResponsavel(microservice.squadResponsavel())
                .build()
            )
            .collect(Collectors.toList())
        );
    }




    
}
