package insper.collie.service;

import insper.collie.squad.SquadAllInfo;

public class MicroserviceParser {
    
        public static MicroserviceModel to(MicroserviceIn in) {
            return MicroserviceModel.builder()
                .name(in.name())
                .linkRepository(in.linkRepository())
                .squad_id(in.squad_id())
                .build();

        }
    
        public static MicroserviceOut to(MicroserviceModel account) {
            return MicroserviceOut.builder()
                .id(account.id())
                .name(account.name())
                .linkRepository(account.linkRepository())
                .squad_id(account.squad_id())
                .build();
        }

        public static MicroserviceAll toAll(MicroserviceModel account, SquadAllInfo squad) {
            return MicroserviceAll.builder()
                .id(account.id())
                .name(account.name())
                .linkRepository(account.linkRepository())
                .squad_id(account.squad_id())
                .squadName(squad.name())
                .build();
        }
}