package psp.sockets.Servidor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;

import lombok.Data;
import psp.sockets.Servidor.Model.*;

import static java.util.Objects.hash;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer conexión con el servidor
            Socket socket = new Socket("192.168.1.14", 12345);

            // Establecer flujos de entrada y salida
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Solicitar credenciales al usuario
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese su nombre de usuario:");
            String nombreUsuario = reader.readLine();
            System.out.println("Ingrese su contraseña:");
            String contraseña = reader.readLine();

            // Crear objeto de credenciales
            Credentials credentials = new Credentials(nombreUsuario, contraseña);

            // Enviar credenciales al servidor
            outputStream.writeObject(credentials);

            // Recibir respuesta del servidor
            Object respuesta = inputStream.readObject();
            System.out.println(respuesta);

            // Si la respuesta indica un inicio de sesión exitoso, mostrar el menú correspondiente
            if (respuesta.equals("Inicio de sesión exitoso")) {
                // Leer el rol del usuario del servidor
                Object rol = inputStream.readObject();

                System.out.println(rol);

                // Mostrar el menú correspondiente según el rol
                switch ((String) rol) {
                    case "cajero":
                        mostrarMenuCajero(outputStream, inputStream);
                        break;
                    case "operario":
                        mostrarMenuOperario(outputStream, inputStream);
                        break;
                    default:
                        System.out.println("Error: Rol desconocido");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Muestra el menú del cajero y maneja las operaciones asociadas.
     *
     * @param outputStream  El flujo de salida para enviar datos al servidor.
     * @param inputStream   El flujo de entrada para recibir datos del servidor.
     */
    private static void mostrarMenuCajero( ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Menú del Cajero:");
            System.out.println("1. Ver saldo de la cuenta del cliente");
            System.out.println("2. Sacar dinero de la cuenta");
            System.out.println("3. Ingresar dinero en la cuenta");
            System.out.println("4. Salir");
            System.out.println("Seleccione una opción:");

            opcion = scanner.nextInt();

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            switch (opcion) {
                case 1:

                    outputStream.writeObject(1);
                    // Lógica para ver saldo de la cuenta
                    System.out.println("Opción seleccionada: Ver saldo de la cuenta del cliente");


                    System.out.println("Ingrese el número de cuenta:");
                    int cuenta = Integer.parseInt(reader.readLine());

                    outputStream.writeObject(cuenta);

                    Object balance = inputStream.readObject();
                    System.out.println("El saldo de la cuenta es: " + balance);

                    break;

                case 2:
                    outputStream.writeObject(2);
                    // Lógica para sacar dinero de la cuenta
                    System.out.println("Opción seleccionada: Sacar dinero de la cuenta");

                    System.out.println("Ingrese el número de cuenta:");
                    int cuentaSacar = Integer.parseInt(reader.readLine());

                    outputStream.writeObject(cuentaSacar);

                    System.out.println("Ingrese la cantidad de dinero a retirar:");
                    int dineroRetirar = Integer.parseInt(reader.readLine());

                    outputStream.writeObject(dineroRetirar);

                    // Leer la respuesta del servidor
                    String respuestaServidor = (String) inputStream.readObject();
                    System.out.println(respuestaServidor);

                    break;

                case 3:
                    outputStream.writeObject(3);

                    // Lógica para ingresar dinero en la cuenta
                    System.out.println("Opción seleccionada: Ingresar dinero en la cuenta");

                    System.out.println("Ingrese el número de cuenta:");
                    int cuentaIngresar = Integer.parseInt(reader.readLine());

                    outputStream.writeObject(cuentaIngresar);

                    System.out.println("Ingrese la cantidad de dinero a ingresar:");
                    int dineroIngresar = Integer.parseInt(reader.readLine());

                    outputStream.writeObject(dineroIngresar);

                    // Leer la respuesta del servidor
                    String ServidorIngresar = (String) inputStream.readObject();
                    System.out.println(ServidorIngresar);

                    break;

                case 4:
                    // Opción para salir
                    System.out.println("Cerrando sesión...");
                    System.exit(0);

                    // Limpiar el flujo de entrada
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //AÑADIR LIMPIEZA DE FLUJO DE SALIDA
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 4);
    }

    /**
     * Muestra el menú del operario y maneja las operaciones asociadas.
     *
     * @param outputStream  El flujo de salida para enviar datos al servidor.
     * @param inputStream   El flujo de entrada para recibir datos del servidor.
     */
    private static void mostrarMenuOperario(ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("Menú del Operario del Banco:");
            System.out.println("1. Ingresar un nuevo usuario en el banco");
            System.out.println("2. Crear una nueva cuenta bancaria");
            System.out.println("3. Ver los datos de una cuenta bancaria");
            System.out.println("4. Ver los datos de un cliente");
            System.out.println("5. Eliminar una cuenta bancaria");
            System.out.println("6. Salir");
            System.out.println("Seleccione una opción:");

            //outputStream.writeInt(opcion = scanner.nextInt());
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    outputStream.writeObject(1);
                    // Lógica para ingresar un nuevo usuario en el banco
                    System.out.println("Opción seleccionada: Ingresar un nuevo usuario en el banco");

                    // Solicitar al operario que ingrese los datos del nuevo usuario
                    System.out.println("Ingrese el nombre del nuevo usuario:");
                    String nuevoUsuarioNombre = scanner.next(); // Lee solo una palabra
                    scanner.nextLine();
                    outputStream.writeObject(nuevoUsuarioNombre);

                    System.out.println("Ingrese la contraseña del nuevo usuario:");
                    String nuevoUsuarioContraseña = scanner.nextLine();
                    outputStream.writeObject(nuevoUsuarioContraseña);

                    System.out.println("Ingrese el rol del nuevo usuario:");
                    String nuevoUsuarioRol = scanner.nextLine();
                    outputStream.writeObject(nuevoUsuarioRol);

                    String UsuarioNuevo = (String) inputStream.readObject();
                    System.out.println(UsuarioNuevo);

                    break;

                case 2:
                    outputStream.writeObject(2);
                    // Lógica para crear una nueva cuenta bancaria
                    System.out.println("Opción seleccionada: Crear una nueva cuenta bancaria");

                    // Solicitar al operario que ingrese el número de cuenta
                    System.out.println("Ingrese el número de la nueva cuenta:");
                    int nuevoNumeroCuenta = scanner.nextInt();
                    outputStream.writeObject(nuevoNumeroCuenta);

                    // Solicitar al operario que el balance con el que se va a iniciar la cuenta
                    System.out.println("Ingrese el balance con el que se va a iniciar la cuenta:");
                    float balance = scanner.nextInt();
                    outputStream.writeObject(balance);

                    // Solicitar al operario que ingrese el nombre del usuario para asociar la cuenta
                    System.out.println("Ingrese el nombre del usuario asociado a la cuenta:");
                    String nombreUsuario = scanner.next();
                    scanner.nextLine();
                    outputStream.writeObject(nombreUsuario);

                    String CuentaNueva = (String) inputStream.readObject();
                    System.out.println(CuentaNueva);


                    break;


                case 3:
                    outputStream.writeObject(3);
                    // Lógica para ver los datos de una cuenta bancaria
                    System.out.println("Opción seleccionada: Ver los datos de una cuenta bancaria");

                    // Solicitar al operario que ingrese el número de cuenta
                    System.out.println("Ingrese el número de cuenta:");
                    int accountNumber = scanner.nextInt();
                    outputStream.writeObject(accountNumber);

                    System.out.println("Ingrese el usuario:");
                    String userAccount = scanner.next();
                    outputStream.writeObject(userAccount);

                    String datosCuenta = (String) inputStream.readObject();
                    System.out.println(datosCuenta);

                    break;

                case 4:
                    outputStream.writeObject(4);
                    // Lógica para ver los datos de un cliente
                    System.out.println("Opción seleccionada: Ver los datos de un cliente");

                    System.out.println("Ingrese el usuario:");
                    String userShow = scanner.next();
                    outputStream.writeObject(userShow);

                    String datosUsuario = (String) inputStream.readObject();
                    System.out.println(datosUsuario);


                    break;

                case 5:
                    outputStream.writeObject(5);
                    // Lógica para eliminar una cuenta bancaria
                    System.out.println("Opción seleccionada: Eliminar una cuenta bancaria");

                    // Solicitar al operario que ingrese el número de cuenta a eliminar
                    System.out.println("Ingrese el número de cuenta a eliminar:");
                    int accountNumberToDelete = scanner.nextInt();
                    outputStream.writeObject(accountNumberToDelete);

                    System.out.println("Ingrese el usuario:");
                    String userAccountDelete = scanner.next();
                    outputStream.writeObject(userAccountDelete);

                    String mensajeEliminar = (String) inputStream.readObject();
                    System.out.println(mensajeEliminar);

                    break;

                case 6:
                    outputStream.writeObject(6);
                    // Opción para salir
                    System.out.println("Cerrando sesión...");
                    System.exit(0);

                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }
        } while (opcion != 6);
    }

    /**
     * Permite al usuario seleccionar una cuenta de entre un conjunto de cuentas.
     *
     * @param cuentas El conjunto de cuentas disponibles para seleccionar.
     * @return La cuenta seleccionada por el usuario, o null si la opción no es válida.
     */
    private static Account seleccionarCuenta(Set<Account> cuentas) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Seleccione la cuenta en la que desea ingresar dinero:");
        int index = 1;
        for (Account cuenta : cuentas) {
            System.out.println(index + ". " + cuenta.getId()); // Aquí puedes mostrar otros detalles de la cuenta si lo deseas
            index++;
        }

        int opcion = scanner.nextInt();
        for (Account cuenta : cuentas) {
            if (opcion == index) {
                return cuenta;
            }
        }

        System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
        return null;
    }
}
