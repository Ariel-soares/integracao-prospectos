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
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import com.google.gson.Gson;

import entities.Prospecto;
import entities.Servico;

public class App {

	public static void main(String[] args)
			throws IOException, InterruptedException, ExecutionException, ClassNotFoundException {

		Scanner sc = new Scanner(System.in);
		Gson gson = new Gson();
		
		List<Prospecto> list = new ArrayList<>();
		
		System.out.println("Insira o caminho do arquivo a ser lido: ");
		String path = sc.next();
		
		System.out.println("Insira o token de acesso: ");
		String token = sc.next();
		
		System.out.println("Insira o endereço do servidor: ");
		String endereco = sc.next();
		
		
//Leitura de arquivo de texto para colher prospectos
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();

			while (line != null) {
				String[] campos = line.split(",");
				list.add(new Prospecto(campos[0], campos[1], campos[2], campos[3], campos[4], campos[5], campos[6],
						campos[7], campos[8], new Servico(campos[9], campos[10])));
				line = br.readLine();
			}

		} catch (IOException e) {
			System.out.println("O caminho especificado apresenta problemas");
		}

		List<String> prospectos = new ArrayList<>();

		for (Prospecto p : list) {
			prospectos.add(gson.toJson(p));
		}

		for (String s : prospectos) {
			System.out.println(s);
		}
		
		System.out.println("__________________________________________________________________________________");
		for (String s : prospectos) {
			System.out.println(s);

			HttpRequest request = HttpRequest.newBuilder().POST(BodyPublishers.ofString(s))
					.uri(URI.create(endereco))
					.header("Content-Type", "application/json").header("Authorization", "Bearer " + token).build();

			HttpClient httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(3))
					.followRedirects(Redirect.NORMAL).build();

			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

			System.out.println(response.body());
			System.out.println(response.statusCode());
			System.out.println(response.headers());
			System.out.println(response.version());
			
		}
		sc.close();
	}
}
