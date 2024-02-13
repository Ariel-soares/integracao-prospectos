package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

import entities.Prospecto;
import entities.Servico;

public class App {

	public static void main(String[] args)
			throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {

		Gson gson = new Gson();
		String path = "C:\\temp\\Book1.csv";

		List<Prospecto> list = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			while (line != null) {
				String[] campos = line.split(",");
				list.add(new Prospecto(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5], campos[6], campos[7], campos[8], new Servico(campos[9], campos[10])));
				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("O caminho especificado apresenta problemas");
		}

		List<String> prospectos = new ArrayList<>();

		for (Prospecto p : list) {
			prospectos.add(gson.toJson(p));
		}

		for(String s : prospectos) {
			System.out.println(s);
		}
		for (String s : prospectos) {
			System.out.println(s);

			HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(s))
					.uri(URI.create("https://api.demo.hubsoft.com.br/api/v1/integracao/prospecto"))
					.header("Content-Type", "application/json")
					.header("Authorization",
							"Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjE1YjBhYjQzNWQ5NjJmMDg4YTc1YjYzMzZlYjc1NjdjYWU5N2Y0MWQyOTIzZDliMzllZDcwNTdmNjkxMGJiYzY3NDQzOWQzYzNmNWI1YjZiIn0.eyJhdWQiOiIxMTg2IiwianRpIjoiMTViMGFiNDM1ZDk2MmYwODhhNzViNjMzNmViNzU2N2NhZTk3ZjQxZDI5MjNkOWIzOWVkNzA1N2Y2OTEwYmJjNjc0NDM5ZDNjM2Y1YjViNmIiLCJpYXQiOjE3MDc1MDMzNDUsIm5iZiI6MTcwNzUwMzM0NSwiZXhwIjoxNzEwMDk1MzQ0LCJzdWIiOiI2NjU0Iiwic2NvcGVzIjpbXX0.T6l4kTYTYpSXO2unSrtBDKKxyrotT-lWve2lhoob0zIluLsPdTYNAqN6Cy0L4h5oB6bl1_4-GtBJn9YssxXAfM-o0IZwZMq1jHB3cl0xF4bukL8cURcPEt0JM3noIQ3aK_XIYPFxCAYdDNTGnx7W6YBLTimcn5vPHQ8YRCbBWJn6SQfGarHqcXP1mkoaSfhVD3yAhFHnJRBTUX-IG23profNPUQlhzd3Bbku8Qpm5T8zUNfIQdMHgP6xf9vYpIL1GR8m21yeZKt9y_JOmnx0wjyd5qfXgl53-qqraQqOjMiaWT7aJOJxbLXeh-Cg1CRhHZQ8d6zl8QpmM_liUfBt04aNqGjYHCRxoHgm1Lld3XPXL67jex8vNw2er-vP10A8EqZwFPt8yumvs4sx0pgAFdWAbFvuusTwk4MAJQRS2IR3HlKSwuZ8WtPUIlT2QwrxNHQlBdWPssYX_8z19YRpNWRsICIQQWkdLfB4RKOzs4iUEma_ay_yT96oo4X6Y12ViZq6XWg-kTEjQoul4gqUbjJAgGGLm4lj5MZUpZ6rhQkGpzkOVMn5EwIb5eT9X7glrzmBYKWO7lW3pHkCeUw-9QSs1S5mo2LBo7o1aSRh48hPJEmNsGJVqcmrB5mc9RMY2rg8-CulVb9SLKhd68ARiDAJEVEAYUKFE-8C7pZ7Glo")
					.build();

			HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3))
					.followRedirects(Redirect.NORMAL).build();

			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

			System.out.println(response.body());
			System.out.println(response.statusCode());
			System.out.println(response.headers());
			System.out.println(response.version());

		}

		/*
		 * HttpRequest request = HttpRequest.newBuilder()
		 * .POST(BodyPublishers.ofString("{\r\n" + "   \"cep\":\"40276140\",\r\n" +
		 * "   \"servico\":{\r\n" + "      \"id_servico\":1,\r\n" +
		 * "      \"valor\":100\r\n" + "   },\r\n" +
		 * "   \"cpf_cnpj\":\"01425836925\",\r\n" +
		 * "   \"telefone\":\"7597896541\",\r\n" +
		 * "   \"nome_razaosocial\":\"Teste Ariel 4\",\r\n" +
		 * "   \"tipo_pessoa\":\"pf\",\r\n" + "   \"bairro\":\"Centro\",\r\n" +
		 * "   \"endereco\":\"Praça Getúlio Vargas\",\r\n" + "   \"numero\":77\r\n" +
		 * "}"))
		 * .uri(URI.create("https://api.demo.hubsoft.com.br/api/v1/integracao/prospecto"
		 * )) .header("Content-Type", "application/json") .header("Authorization",
		 * "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjE1YjBhYjQzNWQ5NjJmMDg4YTc1YjYzMzZlYjc1NjdjYWU5N2Y0MWQyOTIzZDliMzllZDcwNTdmNjkxMGJiYzY3NDQzOWQzYzNmNWI1YjZiIn0.eyJhdWQiOiIxMTg2IiwianRpIjoiMTViMGFiNDM1ZDk2MmYwODhhNzViNjMzNmViNzU2N2NhZTk3ZjQxZDI5MjNkOWIzOWVkNzA1N2Y2OTEwYmJjNjc0NDM5ZDNjM2Y1YjViNmIiLCJpYXQiOjE3MDc1MDMzNDUsIm5iZiI6MTcwNzUwMzM0NSwiZXhwIjoxNzEwMDk1MzQ0LCJzdWIiOiI2NjU0Iiwic2NvcGVzIjpbXX0.T6l4kTYTYpSXO2unSrtBDKKxyrotT-lWve2lhoob0zIluLsPdTYNAqN6Cy0L4h5oB6bl1_4-GtBJn9YssxXAfM-o0IZwZMq1jHB3cl0xF4bukL8cURcPEt0JM3noIQ3aK_XIYPFxCAYdDNTGnx7W6YBLTimcn5vPHQ8YRCbBWJn6SQfGarHqcXP1mkoaSfhVD3yAhFHnJRBTUX-IG23profNPUQlhzd3Bbku8Qpm5T8zUNfIQdMHgP6xf9vYpIL1GR8m21yeZKt9y_JOmnx0wjyd5qfXgl53-qqraQqOjMiaWT7aJOJxbLXeh-Cg1CRhHZQ8d6zl8QpmM_liUfBt04aNqGjYHCRxoHgm1Lld3XPXL67jex8vNw2er-vP10A8EqZwFPt8yumvs4sx0pgAFdWAbFvuusTwk4MAJQRS2IR3HlKSwuZ8WtPUIlT2QwrxNHQlBdWPssYX_8z19YRpNWRsICIQQWkdLfB4RKOzs4iUEma_ay_yT96oo4X6Y12ViZq6XWg-kTEjQoul4gqUbjJAgGGLm4lj5MZUpZ6rhQkGpzkOVMn5EwIb5eT9X7glrzmBYKWO7lW3pHkCeUw-9QSs1S5mo2LBo7o1aSRh48hPJEmNsGJVqcmrB5mc9RMY2rg8-CulVb9SLKhd68ARiDAJEVEAYUKFE-8C7pZ7Glo"
		 * ) .build();
		 * 
		 * HttpClient httpClient =
		 * HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3))
		 * .followRedirects(Redirect.NORMAL).build();
		 * 
		 * HttpResponse<String> response = httpClient.send(request,
		 * BodyHandlers.ofString());
		 * 
		 * System.out.println(response.body());
		 * System.out.println(response.statusCode());
		 * System.out.println(response.headers());
		 * System.out.println(response.version());
		 */}
}