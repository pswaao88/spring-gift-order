package gift.Controller;

import gift.Model.Member;
import gift.Model.Option;
import gift.Model.OrderRequestDTO;
import gift.Model.OrderResponseDTO;
import gift.Model.Product;
import gift.Model.Wishlist;
import gift.Service.LoginService;
import gift.Service.MemberService;
import gift.Service.OptionService;
import gift.Service.ProductService;
import gift.Service.WishlistService;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final ProductService productService;
    private final OptionService optionService;
    private final MemberService memberService;
    private final LoginService loginService;
    private final WishlistService wishlistService;

    public OrderController(ProductService productService, OptionService optionService, MemberService memberService, LoginService loginService, WishlistService wishlistService){
        this.productService = productService;
        this.optionService = optionService;
        this.memberService = memberService;
        this.loginService = loginService;
        this.wishlistService = wishlistService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponseDTO> doOrder(@RequestHeader(value = "Authorization") String bearerToken, @RequestBody OrderRequestDTO orderRequestDTO) {

        String token = bearerToken.substring(6).trim();
        Option option = optionService.getOption(orderRequestDTO.getOptionId());
        System.out.println("option 트랜잭션");
        Product orderProduct = option.getProduct();
        Member member = memberService.getMemberByEmail(loginService.getId(token)+"@kakao.com");
        System.out.println("member 트랜잭션");
        int result = optionService.subtractQuantity(orderRequestDTO);
        System.out.println("결과: " + result);
        System.out.println("option 빼기");
        Long wishlistId = wishlistService.getWishlistId(member.getEmail(),orderProduct.getId());
        System.out.println("wishlist 트랜잭션1");
        if (wishlistId != null){
            wishlistService.deleteWishlist(member.getEmail(),orderProduct.getId(),wishlistId);
            System.out.println("wishlist 트랜잭션2");
        }

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());

        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(orderProduct.getId());
        response.setOptionId(orderRequestDTO.getOptionId());
        response.setQuantity(orderRequestDTO.getQuantity());
        response.setOrderDateTime(formatter.format(date));
        response.setMessage(orderRequestDTO.getMessage());
        return ResponseEntity.status(201).body(response);
    }
}
