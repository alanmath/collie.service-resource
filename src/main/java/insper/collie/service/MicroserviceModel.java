package insper.collie.service;

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
public class MicroserviceModel {

    @Id
    @Column(name = "id_microservice")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "link_repositorio")
    private String linkRepositorio;

    @Column(name = "squad_responsavel")
    private String squadResponsavel;

    public MicroserviceModel(Microservice o) {
        this.id = o.id();
        this.name = o.name();
        this.squadResponsavel = o.squadResponsavel();
    }

    public Microservice to() {
        return Microservice.builder()
            .id(id)
            .name(name)
            .squadResponsavel(squadResponsavel)
            .build();
    }

}