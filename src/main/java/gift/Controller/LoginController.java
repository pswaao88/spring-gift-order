package gift.Controller;

import gift.Service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    // 인가 코드 받아와 토큰 추출
    @GetMapping("")
    public ResponseEntity<String> getLogin(@RequestParam(value = "code") String code){
        var reponse = loginService.makeResponse(code);
        String accessToken = loginService.abstractToken(reponse);
        return ResponseEntity.ok().body(accessToken);
    }
}
