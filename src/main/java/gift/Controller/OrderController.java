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
    private final OrderService orderService;
    
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponseDTO> doOrder(@RequestHeader(value = "Authorization") String jwtToken, @RequestBody OrderRequestDTO orderRequestDTO) {
        // 주문하기 후 response
        OrderResponseDTO response = orderService.doOrder(jwtToken, orderRequestDTO);
        // 메세지 보내기
        orderService.sendMessage(orderRequestDTO.getMessage(), jwtToken);
        return ResponseEntity.status(201).body(response); // 201코드와 response 리턴
    }
}
