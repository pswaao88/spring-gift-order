package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.Model.KakaoProperties;
import java.net.URI;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@SpringBootTest
public class RestClientTest {
    @Autowired
    private KakaoProperties kakaoProperties;

    private final RestClient client = RestClient.builder().build();

    @Test
    void test1(){
        var url = "https://kauth.kakao.com/oauth/token";
        var body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id",kakaoProperties.getClientId());
        body.add("redirect_url",kakaoProperties.getRedirectUrl());
        body.add("code","9Is5pfoVl6Jb-BvI95BTIyskRIq73yGEynvl6ObqNhpqaxUFQmHinAAAAAQKKiWOAAABkNg9QiVV7imzm104lw"); // 변수로 뺴야됨
        var response = client.post()
            .uri(URI.create(url))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body) // request
            .retrieve()
            .toEntity(String.class); // response
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        System.out.println(response);

    }
    @Test
    void test2(){
        assertThat(kakaoProperties.getClientId()).isEqualTo("key");
        assertThat(kakaoProperties.getRedirectUrl()).isEqualTo("http://localhost:8080");
    }
}
