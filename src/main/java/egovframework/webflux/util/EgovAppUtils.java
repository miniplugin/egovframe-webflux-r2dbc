package egovframework.webflux.util;

import egovframework.webflux.entity.Sample;
import egovframework.webflux.service.SampleVO;
import org.springframework.beans.BeanUtils;

public class EgovAppUtils {

    public static SampleVO sampleEntityToVo(Sample sample){
        SampleVO sampleVO = new SampleVO();
        BeanUtils.copyProperties(sample, sampleVO);
        return sampleVO;
    }

    public static Sample sampleVoToEntity(SampleVO sampleVO) {
        Sample sample = new Sample();
        BeanUtils.copyProperties(sampleVO, sample);
        return sample;
    }

}
