package gift.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gift.Model.Link;
import gift.Model.MessageDTO;
import gift.Model.OrderRequestDTO;
import gift.Model.OrderResponseDTO;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class OrderService {

    private final RestClient client = RestClient.builder().build();

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

    public ResponseEntity<String> sendMessage(String message, String token){
        var url = "https://kapi.kakao.com/v2/api/talk/memo/default/send";

        Link link = new Link();
        link.setWebUrl("http://localhost:8080/api/wishlist");

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setObject_type("text");
        messageDTO.setText(message);
        messageDTO.setLink(link);

        ObjectMapper objectMapper = new ObjectMapper();
        // json으로 변경
        String messageJSON;
        try {
            messageJSON = objectMapper.writeValueAsString(messageDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("json 변환 실패");
        }
        // body에 "template_object" : json 값 으로 값 보내기
        var body = new LinkedMultiValueMap<String, String>();
        body.add("template_object", messageJSON);

        var response = client.post()
            .uri(URI.create(url))
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .header("Authorization", "Bearer "+token)
            .body(body)
            .retrieve()
            .toEntity(String.class);
        return response;
    }
}
