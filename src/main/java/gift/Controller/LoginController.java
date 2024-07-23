package gift.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    // 인가 코드 받아오기
    @GetMapping("/{code}")
    public String getLogin(@PathVariable(value = "code") String authorizationCode){
        System.out.println(authorizationCode);
        return "";
    }
    // 인가 코드로 토큰 받아오기
    //=> 멤버로 변경?
}
