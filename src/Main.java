import java.util.Map;
import java.util.Scanner;

public class Main {

    public static double convertir(String from, String to) {
        Map<String, Double> listaConversion = consultarMoneda(from);
        if (listaConversion != null) {
            return listaConversion.get(to);
        }
        return 0;
    }

    public static void convertirMoneda(int opcion, double valor) {
        double tipoCambio;
        double valorConvertido;
        switch (opcion) {
            case 1:
                tipoCambio = convertir("USD", "ARS");
                valorConvertido = valor * tipoCambio;
                System.out.println("El valor " + valor + " [USD] corresponde al valor final de =>> " + valorConvertido + " [ARS]\n");
                break;
            case 2:
                tipoCambio = convertir("ARS", "USD");
                valorConvertido = valor * tipoCambio;
                System.out.println("El valor " + valor + " [ARS] corresponde al valor final de =>> " + valorConvertido + " [USD]\n");
                break;
            case 3:
                tipoCambio = convertir("USD", "BRL");
                valorConvertido = valor * tipoCambio;
                System.out.println("El valor " + valor + " [USD] corresponde al valor final de =>> " + valorConvertido + " [BRL]\n");
                break;
            case 4:
                tipoCambio = convertir("BRL", "USD");
                valorConvertido = valor * tipoCambio;
                System.out.println("El valor " + valor + " [BRL] corresponde al valor final de =>> " + valorConvertido + " [USD]\n");
                break;
            case 5:
                tipoCambio = convertir("USD", "COP");
                valorConvertido = valor * tipoCambio;
                System.out.println("El valor " + valor + " [USD] corresponde al valor final de =>> " + valorConvertido + " [COP]\n");
                break;
            case 6:
                tipoCambio = convertir("COP", "USD");
                valorConvertido = valor * tipoCambio;
                System.out.println("El valor " + valor + " [COP] corresponde al valor final de =>> " + valorConvertido + " [USD]\n");
                break;
            default:
                System.out.println("Ingresa un valor válido\n");
        }
    }

    public static Map<String, Double> consultarMoneda(String input) {
        try {
            ConsultaApi consulta = new ConsultaApi();
            Moneda moneda = consulta.buscarMoneda(input);
            return moneda.conversion_rates();
            //GeneradorDeArchivo generador = new GeneradorDeArchivo();
            //generador.guardarJson(moneda);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("FIN");
        }
        return null;
    }

    public static void main(String[] args) {

        Scanner listener = new Scanner(System.in);
        try {
            int opcionMenu;
            do {
                System.out.println("""
                ****************************************************
                Sea bienvenido/a al Conversor de Moneda :D
                
                1) Dólar =>> Peso argentino
                2) Peso argentino =>> Dólar
                3) Dólar =>> Real brasileño
                4) Real brasileño =>> Dólar
                5) Dólar =>> Peso colombiano
                6) Peso colombiano =>> Dólar
                7) Salir
                
                Elija una opción válida:
                ****************************************************
                """);
                opcionMenu = listener.nextInt();
                if (opcionMenu > 7 || opcionMenu == 0) {
                    System.out.println("Ingresa un valor válido\n");
                } else if (opcionMenu == 7) {
                    System.out.println("FIN");
                    break;
                } else {
                    System.out.println("Ingrese el valor que deseas convertir:\n");
                    double valor = listener.nextDouble();
                    convertirMoneda(opcionMenu, valor);
                }
            } while (opcionMenu != 7);
        } catch (Exception e) {
            System.out.println("Ingresa un valor válido\n");
            System.out.println(e.getMessage());
        }
    }
}
