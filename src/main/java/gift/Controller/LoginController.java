package gift.Controller;

import gift.Service.LoginService;
import gift.Service.MemberAccessTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;
    private final MemberAccessTokenProvider memberAccessTokenProvider;

    public LoginController(LoginService loginService,MemberAccessTokenProvider memberAccessTokenProvider){
        this.loginService = loginService;
        this.memberAccessTokenProvider = memberAccessTokenProvider;
    }

    // 인가 코드 받아와 토큰 추출
    @GetMapping("")
    public ResponseEntity<String> getLogin(@RequestParam(value = "code") String code){
        var response = loginService.makeResponse(code);
        String accessToken = loginService.abstractToken(response);
        String id = loginService.getId(accessToken);
        loginService.getMemberOrSignup(id);
        String jwtToken = memberAccessTokenProvider.createJwt(id+"@kakao.com");
        return ResponseEntity.ok().body(jwtToken);
    }
}
