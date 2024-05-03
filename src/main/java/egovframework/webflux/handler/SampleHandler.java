package egovframework.webflux.handler;

import egovframework.webflux.repository.SampleRepository;
import egovframework.webflux.service.SampleService;
import egovframework.webflux.service.SampleVO;
import egovframework.webflux.util.EgovAppUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.ptl.reactive.annotation.EgovController;
import org.egovframe.rte.ptl.reactive.exception.EgovErrorCode;
import org.egovframe.rte.ptl.reactive.exception.EgovException;
import org.egovframe.rte.ptl.reactive.validation.EgovValidation;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@EgovController
@Slf4j
@RequiredArgsConstructor
@Tag(name="R2DBC(H2) 연동 API", description = "R2DBC(H2) 연동 API 입니다.")
public class SampleHandler {

    private final SampleService sampleService;

    private final SampleRepository sampleRepository;

    private final EgovValidation validator;

    private final Mono<ServerResponse> response404 = Mono.error(new EgovException(EgovErrorCode.NOT_FOUND, "데이터 없음"));

    public Mono<ServerResponse> list(ServerRequest request) {
        Flux<SampleVO> sampleVO = this.sampleRepository.selectAllSample().map(EgovAppUtils::sampleEntityToVo);
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(sampleVO, SampleVO.class);
    }

    public Mono<ServerResponse> get(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return this.sampleService.detail(id)
                .flatMap(sampleVO -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(sampleVO)))
                .switchIfEmpty(this.response404);
    }

    public Mono<ServerResponse> add(ServerRequest request) {
        return request.bodyToMono(SampleVO.class)
                .doOnNext(this.validator::validate)
                .flatMap(this.sampleService::add)
                .flatMap(result -> ServerResponse.ok().build())
                .switchIfEmpty(this.response404);
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        Mono<SampleVO> sampleVOMono = request.bodyToMono(SampleVO.class);
        return sampleVOMono.doOnNext(sampleVO -> sampleVO.setId(id))
                .doOnNext(validator::validate)
                .flatMap(this.sampleService::update)
                .flatMap(result -> ServerResponse.ok().build())
                .switchIfEmpty(this.response404);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return this.sampleService.delete(id)
                .flatMap(result -> ServerResponse.ok().build());
    }

}
