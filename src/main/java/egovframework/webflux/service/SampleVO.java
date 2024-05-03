package egovframework.webflux.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.egovframe.rte.ptl.reactive.validation.EgovNullCheck;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SampleVO extends SampleDefaultVO {

    private Integer id;
    private String sampleId;
    @EgovNullCheck(message="{confirm.required.name}")
    private String name;
    @EgovNullCheck(message="{confirm.required.description}")
    private String description;
    private String useYn;
    @EgovNullCheck(message="{confirm.required.user}")
    private String regUser;

}
