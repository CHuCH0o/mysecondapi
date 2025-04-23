package co.edu.umanizales.mysecondapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.Normalizer;

@SpringBootApplication
public class MysecondapiApplication {

	// Método principal que inicia la aplicación Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(MysecondapiApplication.class, args);
	}

	// Método para normalizar texto: elimina tildes y lo pasa a minúsculas
	// Se usa para comparar nombres sin importar acentos o mayúsculas
	public static String normalize(String input) {
		if (input == null) return null;
		String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
		return normalized.replaceAll("\\p{M}", "").toLowerCase();
	}
}
