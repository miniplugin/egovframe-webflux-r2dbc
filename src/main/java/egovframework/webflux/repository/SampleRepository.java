package egovframework.webflux.repository;

import egovframework.webflux.entity.Sample;
import io.r2dbc.spi.ConnectionFactory;
import org.egovframe.rte.psl.reactive.r2dbc.repository.EgovR2dbcRepository;
import org.egovframe.rte.ptl.reactive.annotation.EgovRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.r2dbc.query.Criteria.where;

@EgovRepository
public class SampleRepository extends EgovR2dbcRepository<Sample> {

    private final Sort sort = Sort.by("sampleId").descending();

    public SampleRepository(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    public Flux<Sample> selectAllSample() {
        return selectAllData(Query.empty().sort(this.sort), Sample.class);
    }

    public Flux<Sample> selectSearchSampleSampleId(String sampleId) {
        return selectAllData(Query.query(where("sampleId").like("%" + sampleId + "%")).sort(this.sort), Sample.class);
    }

    public Flux<Sample> selectSearchSampleName(String name) {
        return selectAllData(Query.query(where("name").like("%" + name + "%")).sort(this.sort), Sample.class);
    }

    public Mono<Sample> selectOneSample(int id) {
        return selectOneData(Query.query(where("id").is(id)), Sample.class);
    }

    public Mono<Long> countSample() {
        return countData(Query.empty(), Sample.class);
    }

    public Mono<Sample> insertSample(Sample sample) {
        return insertData(sample);
    }

    public Mono<Sample> updateSample(Sample sample) {
        return updateData(sample);
    }

    public Mono<Sample> deleteSample(Sample sample) {
        return deleteData(sample);
    }

    public Flux<Sample> deleteAllSample() {
        return this.selectAllSample().flatMap(this::deleteSample);
    }

}
