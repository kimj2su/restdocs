package com.example.restdocs.document;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadSubsectionExtractor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomResponseFieldsSnippet extends AbstractFieldsSnippet {


    //첫번째 인자 type
    //custom-response-fields.snippet 템플릿을 사용할 것이므로 “custom-response” 를 인자로 넘깁니다.
    //두번째 인자 subsectionExtractor
    //현재 컨트롤러는 요청에 대한 응답으로 ApiResponseDto 객체를 보냅니다. 앞서 코드에서 반환값으로 ApiResponseDto는 data필드를 가지고 있고 이 데이터 필드 안에 문서화하고자 하는 enum값을 담아서 보냈습니다.
    //sex값을 예로 들면, data.sex에 값이 들어있습니다. 따라서 beneathPath에는 data.sex, withSubsectionId에는 sex를 명시해주면 이에 따라 데이터를 추출합니다.
    //세번째 인자 attributes
    //속성값을 넣는 곳인데 이 부분은 아래서 볼 문서화 과정에서 보시는게 훨씬 이해하기 편할 것 같습니다.
    //네번째 인자 descriptors
    //요청에 대한 응답값을 파싱해서 enumDocs를 추출해내면 이 안에는 Map 형태로 enum값들이 들어가 있습니다.
    //이 값들을 문서화애 사용하기 위해 enumConvertFieldDescriptor 함수를 만들어 enum값들을 추출하여 FieldDescriptor로 만들어 인자로 넣어줍니다.
    public CustomResponseFieldsSnippet(String type, PayloadSubsectionExtractor<?> subsectionExtractor, List<FieldDescriptor> descriptors, Map<String, Object> attributes, boolean ignoreUndocumentedFields) {
        super(type, descriptors, attributes, ignoreUndocumentedFields, subsectionExtractor);
    }

    @Override
    protected MediaType getContentType(Operation operation) {
        return operation.getResponse().getHeaders().getContentType();
    }

    @Override
    protected byte[] getContent(Operation operation) throws IOException {
        return operation.getResponse().getContent();
    }
}