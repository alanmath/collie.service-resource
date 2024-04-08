package insper.collie.service;

import insper.collie.squad.SquadAllInfo;

public class MicroserviceParser {
    
        public static MicroserviceModel to(MicroserviceIn in) {
            return MicroserviceModel.builder()
                .name(in.name())
                .linkRepositorio(in.linkRepositorio())
                .squadResponsavel(in.squadResponsavel())
                .build();

        }
    
        public static MicroserviceOut to(MicroserviceModel account) {
            return MicroserviceOut.builder()
                .id(account.id())
                .name(account.name())
                .linkRepositorio(account.linkRepositorio())
                .squadResponsavel(account.squadResponsavel())
                .build();
        }

        public static MicroserviceAll toAll(Microservice account, SquadAllInfo squad) {
            return MicroserviceAll.builder()
                .id(account.id())
                .name(account.name())
                .linkRepositorio(account.linkRepositorio())
                .squadResponsavel(account.squadResponsavel())
                .squadNome(squad.name())
                .build();
        }
}