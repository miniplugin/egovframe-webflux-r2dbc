package egovframework.webflux.config;

import egovframework.webflux.entity.Ids;
import egovframework.webflux.entity.Sample;
import egovframework.webflux.repository.IdsRepository;
import egovframework.webflux.repository.SampleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class EgovCommandLineRunner implements CommandLineRunner {

    private final IdsRepository idsRepository;
    private final SampleRepository sampleRepository;

    public EgovCommandLineRunner(IdsRepository idsRepository, SampleRepository sampleRepository) {
        this.idsRepository = idsRepository;
        this.sampleRepository = sampleRepository;
    }

    @Override
    public void run(final String... args) {
        Flux<Ids> idsData = Flux.just(
                new Ids(null, "sample",8)
        );
        idsRepository.deleteAllIds().thenMany(idsData.flatMap(idsRepository::insertIds).log()).subscribe();

        Flux<Sample> sampleData = Flux.just(
                new Sample(null, "SAMPLE-00001", "Runtime Environment", "Foundation Layer", "Y", "eGov"),
                new Sample(null, "SAMPLE-00002", "Runtime Environment", "Persistence Layer", "Y", "eGov"),
                new Sample(null, "SAMPLE-00003", "Runtime Environment", "Presentation Layer", "Y", "eGov"),
                new Sample(null, "SAMPLE-00004", "Runtime Environment", "Business Layer", "Y", "eGov"),
                new Sample(null, "SAMPLE-00005", "Runtime Environment", "Batch Layer", "Y", "eGov"),
                new Sample(null, "SAMPLE-00006", "Runtime Environment", "Integration Layer", "Y", "eGov"),
                new Sample(null, "SAMPLE-00007", "Development Environment", "Implementation Tool", "Y", "eGov"),
                new Sample(null, "SAMPLE-00008", "Development Environment", "Test Tool", "Y", "eGov")
        );
        sampleRepository.deleteAllSample().thenMany(sampleData.flatMap(sampleRepository::insertSample).log()).subscribe();
    }

}
