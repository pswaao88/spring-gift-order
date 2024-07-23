package gift;

import gift.Model.KakaoProperties;
import gift.Service.LoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

@SpringBootTest
public class LoginTest {

    @Autowired
    private KakaoProperties kakaoProperties;

    @Autowired
    private LoginService loginService;

    private final RestClient client = RestClient.builder().build();
    
}
