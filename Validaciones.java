import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validaciones {

    public int ValidarEntero(Scanner sc, int min, int max) {
        String texto = sc.nextLine().trim();
        int numero;
        try {
            numero = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            numero = min - 1;
        }
        if (numero < min || numero > max) {
            System.out.println("por favor Ingrese un número entre " + min + " y " + max);
            return ValidarEntero(sc, min, max);
        }
        return numero;
    }

    public float ValidarDecimalPositivo(Scanner sc) {
        String texto = sc.nextLine().trim();
        float numero;
        try {
            numero = Float.parseFloat(texto);
        } catch (NumberFormatException e) {
            numero = -1;
        }
        if (numero <= 0) {
            System.out.println("por favor Ingrese un número positivo mayor a 0");
            return ValidarDecimalPositivo(sc);
        }
        return numero;
    }

    public String ValidarTexto(Scanner sc, int limite) {
        String texto = sc.nextLine().trim();
        if (texto.isEmpty() || texto.length() > limite) {
            System.out.println("por favor Ingrese un texto de máximo " + limite + " caracteres");
            return ValidarTexto(sc, limite);
        }
        return texto;
    }

    public String ValidarSoloLetras(Scanner sc, int limite) {
        String texto = sc.nextLine().trim();
        if (texto.isEmpty() || texto.length() > limite || !texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            System.out.println("por favor ingrese solo letras (sin números ni caracteres especiales), máximo " + limite + " caracteres");
            return ValidarSoloLetras(sc, limite);
        }
        return texto;
    }

    public String ValidarAlfanumerico(Scanner sc, int limite) {
        String texto = sc.nextLine().trim();
        if (texto.isEmpty() || texto.length() > limite || !texto.matches("[a-zA-Z0-9]+")) {
            System.out.println("por favor ingrese solo letras y números (sin espacios), máximo " + limite + " caracteres");
            return ValidarAlfanumerico(sc, limite);
        }
        return texto;
    }

    public String ValidarSoloNumeros(Scanner sc, int limite) {
        String texto = sc.nextLine().trim();
        if (texto.isEmpty() || texto.length() > limite || !texto.matches("\\d+")) {
            System.out.println("por favor Ingrese solo números, máximo " + limite + " dígitos");
            return ValidarSoloNumeros(sc, limite);
        }
        return texto;
    }

    public String ValidarFecha(Scanner sc) {
        String texto = sc.nextLine().trim();
        try {
            LocalDate.parse(texto);
            return texto;
        } catch (DateTimeParseException e) {
            System.out.println("por favor Ingrese una fecha válida (yyyy-MM-dd)");
            return ValidarFecha(sc);
        }
    }
}
