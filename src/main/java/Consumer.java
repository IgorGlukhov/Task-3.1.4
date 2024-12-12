import model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

public class Consumer {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "http://94.198.50.185:7081/api/users";

        HttpHeaders headers = new HttpHeaders();

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        List<String> test = Objects.requireNonNull(response.getHeaders().get("Set-Cookie"));

        String cookie = String.join(";", test);

        headers.set("Cookie", cookie);

        User user = new User(3L, "James", "Brown", (byte) 14);

        HttpEntity<User> request = new HttpEntity<>(user, headers);

        String req1 = restTemplate.postForObject(url, request, String.class);

        user.setName("Thomas");

        user.setLastName("Shelby");

        ResponseEntity<String> responseEntityPut = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

        ResponseEntity<String> responseEntityDelete = restTemplate.exchange(url+"/"+3L, HttpMethod.DELETE, request, String.class);

        String code = req1 + responseEntityPut.getBody() + responseEntityDelete.getBody();

        System.out.println(code);

    }
}
