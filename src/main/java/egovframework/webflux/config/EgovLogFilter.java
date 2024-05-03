package egovframework.webflux.config;

import org.egovframe.rte.fdl.reactive.idgnr.EgovSequenceGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class EgovLogFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .contextWrite(context ->
                        context.put("egovTraceId", EgovSequenceGenerator.generateSequence("SHA-1")));
    }

}
