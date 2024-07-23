package gift.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.Model.KakaoProperties;
import java.net.URI;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class LoginService {
    @Autowired
    private KakaoProperties kakaoProperties;

    private final RestClient client = RestClient.builder().build();

    public ResponseEntity<String> makeResponse(String code){

        var url = "https://kauth.kakao.com/oauth/token";
        var body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id",kakaoProperties.getClientId());
        body.add("redirect_url",kakaoProperties.getRedirectUrl());
        body.add("code",code);

        var response = client.post()
            .uri(URI.create(url))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body) // request
            .retrieve()
            .toEntity(String.class);
        if (response.getStatusCode() != HttpStatus.OK){
            throw new IllegalArgumentException("에러");
        }
        return response;
    }

    public String abstractToken(ResponseEntity<String> response){
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseBody;
        try {// body를 map으로 변환 후 토큰 추출
            responseBody = objectMapper.readValue(response.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }
        return (String)responseBody.get("access_token"); // Object로 받았으므로 String으로 캐스팅
    }

    public String getEmailMadeById(String token){
        var url = "https://kapi.kakao.com/v2/user/me";

        var response = client.post()
            .uri(URI.create(url))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
            .retrieve()
            .toEntity(String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> responseBody;
        try {// body를 map으로 변환 후 토큰 추출
            responseBody = objectMapper.readValue(response.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e);
        }

        return responseBody.get("id") + "@kakao.com";
    }

}
