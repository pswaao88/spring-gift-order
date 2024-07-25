package gift.Controller;

import gift.Model.Member;
import gift.Model.Option;
import gift.Model.OrderRequestDTO;
import gift.Model.OrderResponseDTO;
import gift.Model.Product;

import gift.Service.LoginService;
import gift.Service.MemberService;
import gift.Service.OptionService;
import gift.Service.OrderService;
import gift.Service.ProductService;
import gift.Service.WishlistService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    private final OptionService optionService;
    private final MemberService memberService;
    private final LoginService loginService;
    private final WishlistService wishlistService;
    private final OrderService orderService;
    
    public OrderController(ProductService productService, OptionService optionService, MemberService memberService, LoginService loginService, WishlistService wishlistService, OrderService orderService){
        this.optionService = optionService;
        this.memberService = memberService;
        this.loginService = loginService;
        this.wishlistService = wishlistService;
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponseDTO> doOrder(@RequestHeader(value = "Authorization") String bearerToken, @RequestBody OrderRequestDTO orderRequestDTO) {
        // Bearer 제거해 토큰 얻음
        String token = bearerToken.substring(6).trim();
        // optionId를 통해 해당하는 option 가져오기
        Option option = optionService.getOption(orderRequestDTO.getOptionId());
        // option에 해당하는 product 가져오기
        Product orderProduct = option.getProduct();
        // 토큰으로 얻은 정보로 해당하는 member 가져오기
        Member member = memberService.getMemberByEmail(loginService.getId(token)+"@kakao.com");
        // wishlistId가 존재 한다면 wishlist 삭제
        Long wishlistId = wishlistService.getWishlistId(member.getEmail(),orderProduct.getId());
        if (wishlistId != null){
            wishlistService.deleteWishlist(member.getEmail(),orderProduct.getId(),wishlistId);
        }
        // 해당하는 quantity만큼 option에서 빼기
        int result = optionService.subtractQuantity(orderRequestDTO);

        // response 만들기
        OrderResponseDTO response = orderService.makeResponse(orderRequestDTO, orderProduct.getId());
        // 메세지 보내기
        orderService.sendMessage(orderRequestDTO.getMessage(), token);
        return ResponseEntity.status(201).body(response); // 201코드와 response 리턴
    }
}
