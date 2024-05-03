package egovframework.webflux.controller;

import egovframework.webflux.service.SampleService;
import egovframework.webflux.service.SampleVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.egovframe.rte.ptl.reactive.annotation.EgovController;
import org.egovframe.rte.ptl.reactive.exception.EgovErrorCode;
import org.egovframe.rte.ptl.reactive.exception.EgovException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@EgovController
@Slf4j
@RequiredArgsConstructor
public class SampleController {

    private final SampleService service;

    @GetMapping({"/","/sample/list"})
    public String list(@ModelAttribute SampleVO sampleVO, Model model) {
        // 데이터 스트림과 출력 버퍼에 대한 역할을 동시에 수행
        IReactiveDataDriverContextVariable contextVariable =
                new ReactiveDataDriverContextVariable(this.service.list().log(), 1);
        model.addAttribute("resultList", contextVariable);
        return "egovSampleList";
    }

    @PostMapping("/sample/search")
    public String search(@ModelAttribute SampleVO sampleVO, Model model) {
        IReactiveDataDriverContextVariable contextVariable =
                new ReactiveDataDriverContextVariable(this.service.search(sampleVO), 1);
        model.addAttribute("resultList", contextVariable);
        return "egovSampleList";
    }

    @GetMapping("/sample/{id}")
    public String detail(@PathVariable int id, Model model) {
        Mono<SampleVO> detail = this.service.detail(id);
        model.addAttribute("sampleVO", detail);
        return "egovSampleRegister";
    }

    @GetMapping("/sample/add")
    public String form(@ModelAttribute SampleVO sampleVO) {
        return "egovSampleRegister";
    }

    @PostMapping("/sample/add")
    public String add(@ModelAttribute @Valid SampleVO sampleVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "egovSampleRegister";
        }
        this.service.add(sampleVO).subscribe();
        return "redirect:/sample/list";
    }

    @PostMapping("/sample/update")
    public String update(@ModelAttribute @Valid SampleVO sampleVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "egovSampleRegister";
        }
        this.service.update(sampleVO).subscribe();
        return "redirect:/sample/list";
    }

    @DeleteMapping("/sample/{id}")
    public String delete(@PathVariable int id) {
        this.service.delete(id).subscribe();
        return "redirect:/sample/list";
    }

    @GetMapping("/sample/error")
    @ResponseBody
    public Mono<String> error() {
        return Mono.error(new EgovException(EgovErrorCode.INVALID_INPUT_VALUE, "입력 데이터 오류"));
    }

    @GetMapping("/sample/log")
    @ResponseBody
    public void log() {
        Flux.just(1,2,3,4,5).log().subscribe(value -> log.info("##### test log >>> {}", value));
    }

}
