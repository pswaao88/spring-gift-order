package gift;

import static org.assertj.core.api.Assertions.assertThat;

import gift.Model.KakaoProperties;
import gift.Model.Member;
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

    @Test
    void abstractToken(){
        var response = loginService.makeResponse("인가코드");
        String accessToken = loginService.abstractToken(response);
        assertThat(accessToken).isNotNull(); // null이 아니면 추출 성공
    }

    @Test
    void getId(){
        var response = loginService.makeResponse("인가코드");
        String accessToken = loginService.abstractToken(response);
        String id = loginService.getId(accessToken);
        assertThat(id).isNotNull(); // null이 아니면 가져오기 성공
    }

    @Test
    void signupMember(){
        var response = loginService.makeResponse("인가코드");
        String accessToken = loginService.abstractToken(response);
        String id = loginService.getId(accessToken);
        Member actual = loginService.signupMember(id);

        assertThat(actual.getEmail()).isEqualTo(id+"@kakao.com");
        assertThat(actual.getPassword()).isEqualTo(id);
    }
}
