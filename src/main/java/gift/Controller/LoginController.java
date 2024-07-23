package gift.Controller;

import gift.Model.KakaoProperties;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class LoginController {

    @Autowired
    private KakaoProperties kakaoProperties;

    private final RestClient client = RestClient.builder().build();

    // 인가 코드 받아오기
    @GetMapping("/")
    public ResponseEntity<String> getLogin(@RequestParam(value = "code") String code){

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
        return response;
    }
}
