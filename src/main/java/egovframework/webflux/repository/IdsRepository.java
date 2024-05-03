package egovframework.webflux.repository;

import egovframework.webflux.entity.Ids;
import io.r2dbc.spi.ConnectionFactory;
import org.egovframe.rte.psl.reactive.r2dbc.repository.EgovR2dbcRepository;
import org.egovframe.rte.ptl.reactive.annotation.EgovRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.relational.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.r2dbc.query.Criteria.where;

@EgovRepository
public class IdsRepository extends EgovR2dbcRepository<Ids> {

    public IdsRepository(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        super(connectionFactory);
    }

    public Flux<Ids> selectAllIds() {
        return selectAllData(Query.empty(), Ids.class);
    }

    public Mono<Ids> selectOneIds(String tableName) {
        return selectOneData(Query.query(where("tableName").is(tableName)), Ids.class);
    }

    public Mono<Long> countIds() {
        return countData(Query.empty(), Ids.class);
    }

    public Mono<Ids> insertIds(Ids ids) {
        return insertData(ids);
    }

    public Mono<Ids> updateIds(Ids ids) {
        return updateData(ids);
    }

    public Mono<Ids> deleteIds(Ids ids) {
        return deleteData(ids);
    }

    public Flux<Ids> deleteAllIds() {
        return this.selectAllIds().flatMap(this::deleteIds);
    }

}
