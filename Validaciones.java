import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validaciones {
    public int ValidarEntero(Scanner sc, int min, int max) {
    int numero = min - 1;
    while (numero < min || numero > max) {
        try {
            numero = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            numero = min - 1;
        }
        if (numero < min || numero > max) {
            System.out.println("por favor Ingrese un número entre " + min + " y " + max);
        }
    }
    return numero;
}

public String ValidarTexto(Scanner sc, int limite) {
        String texto = sc.nextLine().trim();
        while (texto.isEmpty() || texto.length() > limite) {
            System.out.println("por favor Ingrese un texto de máximo " + limite + " caracteres");
            texto = sc.nextLine().trim();
        }
        return texto;
    }

public String ValidarAlfanumerico(Scanner sc, int limite) {
    String texto = sc.nextLine().trim();
    while (texto.isEmpty() || texto.length() > limite || !texto.matches("[a-zA-Z0-9]+")) {
        System.out.println("por favor ingrese solo letras y números (sin espacios), máximo " + limite + " caracteres");
        texto = sc.nextLine().trim();
    }
    return texto;
}

public String ValidarSoloNumeros(Scanner sc, int limite) {
    String texto = sc.nextLine().trim();
    while (texto.isEmpty() || texto.length() > limite || !texto.matches("\\d+")) {
        System.out.println("por favor Ingrese solo números, máximo " + limite + " dígitos");
        texto = sc.nextLine().trim();
    }
    return texto;
}

public String ValidarFecha(Scanner sc) {
    String texto = sc.nextLine().trim();
    boolean valida = false;
    while (!valida) {
        try {
            LocalDate.parse(texto);
            valida = true;
        } catch (DateTimeParseException e) {
            System.out.println("por favor Ingrese una fecha válida (yyyy-MM-dd)");
            texto = sc.nextLine().trim();
        }
    }
    return texto;
}
}