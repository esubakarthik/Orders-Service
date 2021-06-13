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

import static org.junit.jupiter.api.Assertions.*;

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

	// Step 2 test:
	@Test
	void WithofferAppleeven() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Apple=6");
		assertEquals("1.80",df2.format(getordercost(response)));
	}
	@Test
	void WithofferAppleodd() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Apple=7");
		assertEquals("2.40",df2.format(getordercost(response)));
	}
	@Test
	void WithofferAppleSingle() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Apple=1");
		assertEquals("0.60",df2.format(getordercost(response)));
	}
	void WithofferAppleDouble() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Apple=2");
		assertEquals("0.60",df2.format(getordercost(response)));
	}



	@Test
	void WithofferOrangesx3() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Orange=9");
		assertEquals("1.50",df2.format(getordercost(response)));
	}
	@Test
	void WithofferOrangesx3plus1() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Orange=10");
		assertEquals("1.75",df2.format(getordercost(response)));
	}
	@Test
	void WithofferOrangesx3minus1() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Orange=8");
		assertEquals("1.50",df2.format(getordercost(response)));
	}
	@Test
	void WithofferOrangesx3Single() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Orange=1");
		assertEquals("0.25",df2.format(getordercost(response)));
	}
	@Test
	void WithofferOrangesdouble() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Orange=2");
		assertEquals("0.50",df2.format(getordercost(response)));
	}
	@Test
	void WithofferOrangestriple() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Orange=3");
		assertEquals("0.50",df2.format(getordercost(response)));
	}
// Task 3 checks
	@Test
	void checkgetallorder() throws IOException {
		String response = load("http://localhost:8080/getallorders");
		assertNotEquals("[]",response);
	}
	@Test
	void checkorderbyid() throws IOException {
		String response = load("http://localhost:8080/neworderwithoffer?Orange=23"); //to add atleast one record
		 response = load("http://localhost:8080/getbyorderid?id=1");
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		JsonParser parser = factory.createParser(response);
		JsonNode actualObj = mapper.readTree(parser);
		 int orderno = actualObj.get("order_Number").asInt();
		assertEquals(1,orderno);
	}
	@Test
	void checkorderbyincorrectid() throws IOException {
		String response = load("http://localhost:8080/getbyorderid?id=100000000");
		assertEquals("No order exists with that order id",response);
	}







	@Test
	void contextLoads() {
	}

}
