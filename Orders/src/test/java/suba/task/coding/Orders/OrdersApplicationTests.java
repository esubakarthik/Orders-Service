package suba.task.coding.Orders;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class OrdersApplicationTests {

	 DecimalFormat df2 = new DecimalFormat("0.00");

	String load(String Uri){
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(Uri))
				.build();

		try {
			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	Double getordercost(String response) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		JsonParser parser = factory.createParser(response);
		JsonNode actualObj = mapper.readTree(parser);
		return actualObj.get("total_cost").asDouble();
	}

	// Task 1 checks
	@Test
	void AppleOranges() throws IOException {
		String response = load("http://localhost:8080/neworder?Orange=67&Apple=23");
		assertEquals("30.55",df2.format(getordercost(response)));
	}

	@Test
	void Appleonly() throws IOException {
		String response = load("http://localhost:8080/neworder?Apple=23");
		assertEquals("13.80",df2.format(getordercost(response)));
	}
	@Test
	void Orangesonly() throws IOException {
		String response = load("http://localhost:8080/neworder?Orange=67");
		assertEquals("16.75",df2.format(getordercost(response)));
	}

	@Test
	void Invaildnumbers() throws IOException {
		String response = load("http://localhost:8080/neworder?Orange=-7");
		assertEquals("Invaild input",response);
	}


	@Test
	void ZeroOrder() throws IOException {
		String response = load("http://localhost:8080/neworder?Orange=0&Apple=0");
		assertEquals("NO item in the order",response);
	}

	@Test
	void contextLoads() {
	}

}
