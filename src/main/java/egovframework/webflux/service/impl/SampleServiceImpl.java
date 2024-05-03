package egovframework.webflux.service.impl;

import egovframework.webflux.entity.Sample;
import egovframework.webflux.repository.IdsRepository;
import egovframework.webflux.repository.SampleRepository;
import egovframework.webflux.service.SampleService;
import egovframework.webflux.service.SampleVO;
import egovframework.webflux.util.EgovAppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.ptl.reactive.annotation.EgovService;
import org.egovframe.rte.ptl.reactive.exception.EgovAbstractService;
import org.egovframe.rte.ptl.reactive.exception.EgovErrorCode;
import org.egovframe.rte.ptl.reactive.exception.EgovException;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@EgovService
@Slf4j
@RequiredArgsConstructor
public class SampleServiceImpl extends EgovAbstractService implements SampleService {

    private final IdsRepository idsRepository;

    private final SampleRepository sampleRepository;

    private int sequence = 1;

    public Flux<SampleVO> list() {
        return this.sampleRepository.selectAllSample().map(EgovAppUtils::sampleEntityToVo);
    }

    public Flux<SampleVO> search(SampleVO sampleVO) {
        if ("0".equals(sampleVO.getSearchCondition())) {
            return this.sampleRepository.selectSearchSampleSampleId(sampleVO.getSearchKeyword()).map(EgovAppUtils::sampleEntityToVo);
        } else if ("1".equals(sampleVO.getSearchCondition())) {
            return this.sampleRepository.selectSearchSampleName(sampleVO.getSearchKeyword()).map(EgovAppUtils::sampleEntityToVo);
        } else {
            return this.list();
        }
    }

    public Mono<SampleVO> detail(int id) {
        return this.sampleRepository.selectOneSample(id).map(EgovAppUtils::sampleEntityToVo);
    }

    public Mono<SampleVO> add(SampleVO sampleVO) {
        Sample sample = EgovAppUtils.sampleVoToEntity(sampleVO);
        try {
            sequence = this.idsRepository.selectOneIds("sample")
                    .flatMap(result -> {
                        result.setSeq(ObjectUtils.isEmpty(result.getSeq()) ? 1 : result.getSeq()+1);
                        return this.idsRepository.updateIds(result);
                    }).toFuture().get().getSeq();
        } catch (InterruptedException e) {
            return Mono.error(new EgovException(EgovErrorCode.INTERNAL_SERVER_ERROR, "SampleService InterruptedException Occurred"));
        } catch (ExecutionException e) {
            return Mono.error(new EgovException(EgovErrorCode.INTERNAL_SERVER_ERROR, "SampleService ExecutionException Occurred"));
        }
        sample.setSampleId("SAMPLE-".concat(String.format("%05d", sequence)));
        return this.sampleRepository.insertSample(sample).map(EgovAppUtils::sampleEntityToVo);
    }

    public Mono<SampleVO> update(SampleVO sampleVO) {
        Sample sample = EgovAppUtils.sampleVoToEntity(sampleVO);
        return this.sampleRepository.selectOneSample(sample.getId())
                .flatMap(result -> {
                    result.setName(sample.getName());
                    result.setDescription(sample.getDescription());
                    result.setUseYn(sample.getUseYn());
                    result.setRegUser(sample.getRegUser());
                    return this.sampleRepository.updateSample(result);
                })
                .map(EgovAppUtils::sampleEntityToVo);
    }

    public Mono<SampleVO> delete(int id) {
        return this.sampleRepository.selectOneSample(id)
                .flatMap(this.sampleRepository::deleteSample)
                .map(EgovAppUtils::sampleEntityToVo);
    }

}
