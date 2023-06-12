import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClubGestion {
    private static final String FILENAME = "club.dat";
    private Map<String, Socio> socios;

    public ClubGestion() {
        socios = cargarDatos();
    }

    public void ejecutar() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("----- Menú -----");
            System.out.println("1. Alta socio");
            System.out.println("2. Baja socio");
            System.out.println("3. Modificación socio");
            System.out.println("4. Listar socios por apodo");
            System.out.println("5. Listar socios por antigüedad");
            System.out.println("6. Listar socios con alta anterior a un año determinado");
            System.out.println("0. Salir");
            System.out.print("Ingrese una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    altaSocio();
                    break;
                case 2:
                    bajaSocio();
                    break;
                case 3:
                    modificarSocio();
                    break;
                case 4:
                    listarSociosPorApodo();
                    break;
                case 5:
                    listarSociosPorAntiguedad();
                    break;
                case 6:
                    listarSociosPorAntiguedadDeterminada();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (opcion != 0);

        guardarDatos();
    }

    private void altaSocio() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el apodo del socio: ");
        String apodo = scanner.nextLine();
        System.out.print("Ingrese el nombre del socio: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la fecha de ingreso (formato: dd/MM/yyyy): ");
        String fechaIngresoStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date fechaIngreso = dateFormat.parse(fechaIngresoStr);
            Socio socio = new Socio(nombre, fechaIngreso);
            socios.put(apodo, socio);
            System.out.println("Socio agregado correctamente.");
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha de ingreso.");
        }
    }

    private void bajaSocio() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el apodo del socio a dar de baja: ");
        String apodo = scanner.nextLine();

        if (socios.containsKey(apodo)) {
            socios.remove(apodo);
            System.out.println("Socio dado de baja correctamente.");
        } else {
            System.out.println("No se encontró ningún socio con ese apodo.");
        }
    }

    private void modificarSocio() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el apodo del socio a modificar: ");
        String apodo = scanner.nextLine();

        if (socios.containsKey(apodo)) {
            Socio socio = socios.get(apodo);
            System.out.print("Ingrese el nuevo nombre del socio: ");
            String nuevoNombre = scanner.nextLine();
            socio.setNombre(nuevoNombre);
            System.out.println("Socio modificado correctamente.");
        } else {
            System.out.println("No se encontró ningún socio con ese apodo.");
        }
    }

    private void listarSociosPorApodo() {
        System.out.println("----- Listado de Socios por Apodo -----");
        for (String apodo : socios.keySet()) {
            Socio socio = socios.get(apodo);
            System.out.println("Apodo: " + apodo + ", Nombre: " + socio.getNombre());
        }
        System.out.println("----------------------------------------");
    }

    private void listarSociosPorAntiguedad() {
        List<Socio> sociosOrdenados = new ArrayList<>(socios.values());
        sociosOrdenados.sort(Comparator.comparing(Socio::getFechaIngreso));

        System.out.println("----- Listado de Socios por Antigüedad -----");
        for (Socio socio : sociosOrdenados) {
            System.out.println("Nombre: " + socio.getNombre() + ", Fecha de ingreso: " + socio.getFechaIngreso());
        }
        System.out.println("---------------------------------------------");
    }

    private void listarSociosPorAntiguedadDeterminada() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el año determinado: ");
        int anioDeterminado = scanner.nextInt();

        System.out.println("----- Listado de Socios con Alta anterior a " + anioDeterminado + " -----");
        for (Socio socio : socios.values()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(socio.getFechaIngreso());
            int anioIngreso = calendar.get(Calendar.YEAR);

            if (anioIngreso < anioDeterminado) {
                System.out.println("Nombre: " + socio.getNombre() + ", Fecha de ingreso: " + socio.getFechaIngreso());
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    private Map<String, Socio> cargarDatos() {
        Map<String, Socio> datos = new HashMap<>();

        try (ObjectInputStream archivo = new ObjectInputStream(new FileInputStream(FILENAME))) {
            datos = (Map<String, Socio>) archivo.readObject();
            System.out.println("Datos cargados correctamente.");
        } catch (FileNotFoundException e) {
            System.out.println("No se encontró el archivo. Se creará uno nuevo al guardar los datos.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar los datos del archivo.");
        }

        return datos;
    }

    private void guardarDatos() {
        try (ObjectOutputStream archivo = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            archivo.writeObject(socios);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos en el archivo.");
        }
    }

    public static void main(String[] args) {
        ClubGestion club = new ClubGestion();
        club.ejecutar();
    }
}