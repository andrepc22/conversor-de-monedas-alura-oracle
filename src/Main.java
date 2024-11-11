import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private LinkedList<String> historial = new LinkedList<>();
    private static final int MAX_SIZE_HISTORIAL = 10;

    public double convertir(String from, String to) {
        Map<String, Double> listaConversion = consultarMoneda(from);
        if (listaConversion != null) {
            return listaConversion.get(to);
        }
        return 0;
    }

    public String setTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return dateTime.format(dateTimeFormatter);
    }

    public void convertirMoneda(int opcion, double valor) {
        double tipoCambio;
        double valorConvertido;
        ArrayList<String> divisas = new ArrayList<>();
        switch (opcion) {
            case 1:
                divisas.add("USD");
                divisas.add("ARS");
                break;
            case 2:
                divisas.add("ARS");
                divisas.add("USD");
                break;
            case 3:
                divisas.add("USD");
                divisas.add("BRL");
                break;
            case 4:
                divisas.add("BRL");
                divisas.add("USD");
                break;
            case 5:
                divisas.add("USD");
                divisas.add("COP");
                break;
            case 6:
                divisas.add("COP");
                divisas.add("USD");
                break;
            default:
                divisas.add(null);
                System.out.println("Ingresa un valor válido\n");
        }
        if (divisas.getFirst() != null) {
            tipoCambio = convertir(divisas.getFirst(), divisas.getLast());
            valorConvertido = valor * tipoCambio;
            String fecha = setTime();
            String msg = "El valor " + valor + " " + divisas.getFirst() +
                    " corresponde al valor final de =>> " + valorConvertido + " " + divisas.getLast() + " <" + fecha + ">";
            System.out.println(msg + "\n");
            agregarConversion(msg);
        }
    }

    public Map<String, Double> consultarMoneda(String input) {
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

    public void mostrarHistorial() {
        System.out.println("----------------------------------------------------");
        System.out.println("Historial de las últimas " + historial.size() + " conversiones: \n");
        for (String conversion : historial) {
            System.out.println(conversion);
        }
        System.out.println("----------------------------------------------------\n");
    }

    public void agregarConversion(String conversion) {
        if (historial.size() == MAX_SIZE_HISTORIAL) {
            historial.removeFirst();
        }
        historial.addLast(conversion);
    }

    public static void main(String[] args) {

        Main converter = new Main();
        Scanner listener = new Scanner(System.in);

        try {
            int opcionMenu;
            boolean loop = true;
            while (loop) {
                System.out.println("""
                ****************************************************
                Sea bienvenido/a al Conversor de Moneda :D
                
                1) Dólar =>> Peso argentino
                2) Peso argentino =>> Dólar
                3) Dólar =>> Real brasileño
                4) Real brasileño =>> Dólar
                5) Dólar =>> Peso colombiano
                6) Peso colombiano =>> Dólar
                7) Mostrar historial (Últimas 10 conversiones)
                8) Salir
                
                Elija una opción válida:
                ****************************************************
                """);

                opcionMenu = listener.nextInt();

                if (opcionMenu > 8 || opcionMenu == 0) {
                    System.out.println("Ingresa un valor válido\n");
                } else if (opcionMenu == 7) {
                    converter.mostrarHistorial();
                } else if (opcionMenu == 8) {
                    System.out.println("FIN");
                    loop = false;
                } else {
                    System.out.println("Ingrese el valor que deseas convertir:\n");
                    double valor = listener.nextDouble();
                    converter.convertirMoneda(opcionMenu, valor);
                }
            }
        } catch (Exception e) {
            System.out.println("Ingresa un valor válido\n");
            System.out.println(e.getMessage());
        }
    }
}
