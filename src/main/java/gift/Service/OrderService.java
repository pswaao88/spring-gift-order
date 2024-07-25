package gift.Service;

import gift.Model.OrderRequestDTO;
import gift.Model.OrderResponseDTO;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    public OrderResponseDTO makeResponse(OrderRequestDTO orderRequestDTO, Long productId){
        //현재 날짜 가져오기
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        // respone 생성
        OrderResponseDTO response = new OrderResponseDTO();
        response.setId(productId);
        response.setOptionId(orderRequestDTO.getOptionId());
        response.setQuantity(orderRequestDTO.getQuantity());
        response.setOrderDateTime(formatter.format(date));
        response.setMessage(orderRequestDTO.getMessage());
        return response;
    }
}
