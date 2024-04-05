package insper.collie.service;

public class MicroserviceParser {
    
        public static MicroserviceModel to(MicroserviceIn in) {
            return MicroserviceModel.builder()
                .name(in.name())
                .squadResponsavel(in.squadResponsavel())
                .build();

        }
    
        public static MicroserviceOut to(MicroserviceModel account) {
            return MicroserviceOut.builder()
                .id(account.id())
                .name(account.name())
                .squadResponsavel(account.squadResponsavel())
                .build();
        }
}