package egovframework.webflux.router;

import egovframework.webflux.entity.Sample;
import egovframework.webflux.handler.SampleHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Component
public class SampleRouter {

    @RouterOperations({
            @RouterOperation(
                    path="/router/list",
                    method=RequestMethod.GET,
                    beanClass=SampleHandler.class,
                    beanMethod="list",
                    operation=@Operation(
                            operationId="list",
                            summary = "목록 조회",
                            description = "모든 목록을 조회합니다."
                    )
            ),
            @RouterOperation(
                    path="/router/{id}",
                    method=RequestMethod.GET,
                    beanClass=SampleHandler.class,
                    beanMethod="get",
                    operation=@Operation(
                            operationId="get",
                            parameters={@Parameter(in=ParameterIn.PATH, name="id")},
                            summary = "아이디별 조회",
                            description = "아이디별 목록을 조회합니다."
                    )
            ),
            @RouterOperation(
                    path="/router/add",
                    method=RequestMethod.POST,
                    beanClass=SampleHandler.class,
                    beanMethod="add",
                    operation=@Operation(
                            operationId="add",
                            requestBody=@RequestBody(content=@Content(schema=@Schema(implementation=Sample.class))),
                            summary = "데이터 생성",
                            description = "데이터를 생성합니다."
                    )
            ),
            @RouterOperation(
                    path="/router/{id}",
                    method=RequestMethod.PUT,
                    beanClass=SampleHandler.class,
                    beanMethod="update",
                    operation=@Operation(
                            operationId="update",
                            parameters={@Parameter(in=ParameterIn.PATH, name="id")},
                            requestBody=@RequestBody(content=@Content(schema=@Schema(implementation=Sample.class))),
                            summary = "데이터 수정",
                            description = "데이터를 수정합니다."
                    )
            ),
            @RouterOperation(
                    path="/router/{id}",
                    method=RequestMethod.DELETE,
                    beanClass=SampleHandler.class,
                    beanMethod="delete",
                    operation=@Operation(
                            operationId="delete",
                            parameters={@Parameter(in=ParameterIn.PATH, name="id")},
                            summary = "데이터 삭제",
                            description = "데이터를 삭제합니다."
                    )
            )
    })
    @Bean
    public RouterFunction<ServerResponse> routerFunction(SampleHandler sampleHandler) {
        return RouterFunctions.route()
                .GET("/router/list", sampleHandler::list)
                .GET("/router/{id}", sampleHandler::get)
                .POST("/router/add", sampleHandler::add)
                .PUT("/router/{id}", sampleHandler::update)
                .DELETE("/router/{id}", sampleHandler::delete)
                .build();
    }

}
