package insper.collie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.qos.logback.classic.Logger;
import insper.collie.squad.SquadAllInfo;
import insper.collie.squad.SquadController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;



@RestController
@Tag(name = "Microservice", description = "API de Microserviços")
public class MicroserviceResource implements MicroserviceController {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(MicroserviceResource.class);

    @Autowired
    private MicroserviceService microserviceService;

    @Autowired
    private SquadController squadController;

    @Override
    @Operation(summary = "Cria um novo Microserviço", description = "Cria um novo Microserviço e retorna o objeto criado com seu ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Microserviço criado com sucesso", content = @Content(schema = @Schema(implementation = MicroserviceOut.class))),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        })
    public ResponseEntity<MicroserviceOut> create(MicroserviceIn microserviceRegisterIn) {
        final MicroserviceModel microservice = microserviceService.createMicroservice(MicroserviceParser.to(microserviceRegisterIn));
        logger.info("Microservice created: {}", microservice);
        return ResponseEntity.ok(MicroserviceParser.to(microservice));
    }
    
    
    @Override
    @Operation(summary = "Lê um Microserviço pelo ID", description = "Obtém as informações de um Microserviço específico pelo seu ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Microserviço encontrado", content = @Content(schema = @Schema(implementation = MicroserviceOut.class))),
            @ApiResponse(responseCode = "404", description = "Microserviço não encontrado")
        })
    public ResponseEntity<MicroserviceAll> readById(String id) {
        MicroserviceAll microservice = microserviceService.getMicroservice(id);
        logger.info("Microservice found: {}", microservice);

        return ResponseEntity.ok(microservice);
    }

    @Override
    @Operation(summary = "Atualiza um Microserviço", description = "Atualiza as informações de um Microserviço específico.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Microserviço atualizado com sucesso", content = @Content(schema = @Schema(implementation = MicroserviceOut.class))),
            @ApiResponse(responseCode = "404", description = "Microserviço não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        })
    public ResponseEntity<MicroserviceOut> update(String id, MicroserviceIn microserviceRegisterIn) {
        MicroserviceModel microservice = microserviceService.updateMicroservice(id, MicroserviceParser.to(microserviceRegisterIn));

        logger.info("Microservice updated: {}", microservice);
        return ResponseEntity.ok(MicroserviceParser.to(microservice));
    }

    @Override
    @Operation(summary = "Deleta um Microserviço", description = "Deleta um Microserviço específico pelo seu ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Microserviço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Microserviço não encontrado")
        })
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
    @Operation(summary = "Lista todos os Microserviços", description = "Lista todas as instâncias de Microserviços.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de Microserviços encontrada", content = @Content(schema = @Schema(implementation = MicroserviceOut[].class))),
            @ApiResponse(responseCode = "404", description = "Nenhum Microserviço encontrado")
        })
    public ResponseEntity<List<MicroserviceOut>> read() {
        final List<MicroserviceModel> microservices = microserviceService.listAllMicroservices();
        logger.info("Microservices found: {}", microservices);
        return ResponseEntity.ok(microservices.stream()
            .map(microservice -> MicroserviceParser.to(microservice))
            .collect(Collectors.toList())
        );    
    }

    @Override
    @Operation(summary = "Trivy Report" , description = "Adiciona um relatório Trivy a um Microserviço específico.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Relatório Trivy adicionado com sucesso", content = @Content(schema = @Schema(implementation = MicroserviceOut.class))),
            @ApiResponse(responseCode = "404", description = "Microserviço não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
        })
    public void trivy(String id, TrivyReportIn trivyReportIn) {
        logger.info("passou aqui");
        microserviceService.trivyReport(id, (trivyReportIn));
        // logger.info("Trivy report added to microservice: {}", microservice);
        logger.info("id: {}, report", id);    
    
        }

    // get microservices by squad
    @Override
    @Operation(summary = "Lista todos os Microserviços de um Squad", description = "Lista todas as instâncias de Microserviços de um Squad específico.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de Microserviços encontrada", content = @Content(schema = @Schema(implementation = MicroserviceOut[].class))),
            @ApiResponse(responseCode = "404", description = "Nenhum Microserviço encontrado")
        })
    public ResponseEntity<List<MicroserviceAll>> readBySquad(String squadId) {
        final List<MicroserviceModel> microservices = microserviceService.listMicroservicesBySquad(squadId);
        
        SquadAllInfo squad = squadController.getSquad(squadId).getBody();
        logger.info("Microservices found: {}", microservices);
        return ResponseEntity.ok(microservices.stream()
            .map(microservice -> MicroserviceParser.toAll(microservice, squad))
            .collect(Collectors.toList())
        );
           
    }

    
}
