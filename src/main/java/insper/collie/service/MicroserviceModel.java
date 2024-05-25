package insper.collie.service;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "microservices")
@EqualsAndHashCode(of = "id")
@Builder @Getter @Setter @Accessors(chain = true, fluent = true)
@NoArgsConstructor @AllArgsConstructor
public class MicroserviceModel implements Serializable{

    @Id
    @Column(name = "id_microservice")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "link_repository")
    private String linkRepository;

    @Column(name = "squad_id")
    private String squad_id;

    public MicroserviceModel(Microservice o) {
        this.id = o.id();
        this.name = o.name();
        this.linkRepository = o.linkRepository();
        this.squad_id = o.squad_id();
    }

    public Microservice to() {
        return Microservice.builder()
            .id(id)
            .name(name)
            .linkRepository(linkRepository)
            .squad_id(squad_id)
            .build();
    }

}