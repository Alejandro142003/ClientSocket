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
            Socket socket = new Socket("192.168.18.34", 12345);

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

            outputStream.writeInt(opcion = scanner.nextInt());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            switch (opcion) {
                case 1:
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
    private static void mostrarMenuOperario(ObjectOutputStream outputStream, ObjectInputStream inputStream) throws IOException {
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

            outputStream.writeInt(opcion = scanner.nextInt());

            switch (opcion) {
                case 1:
                    // Lógica para ingresar un nuevo usuario en el banco
                    System.out.println("Opción seleccionada: Ingresar un nuevo usuario en el banco");

                    // Solicitar al operario que ingrese los datos del nuevo usuario
                    System.out.println("Ingrese el nombre del nuevo usuario:");
                    String nuevoUsuarioNombre = scanner.nextLine();
                    System.out.println("Ingrese la contraseña del nuevo usuario:");
                    String nuevoUsuarioContraseña = scanner.nextLine();
                    System.out.println("Ingrese el rol del nuevo usuario:");
                    String nuevoUsuarioRol = scanner.nextLine();

                    // Crear un nuevo objeto de tipo User con los datos ingresados
                    User nuevoUsuario = new User();
                    nuevoUsuario.setName(nuevoUsuarioNombre);
                    nuevoUsuario.setPassword(nuevoUsuarioContraseña);
                    nuevoUsuario.setRole(nuevoUsuarioRol);

                    try {
                        // Enviar el nuevo usuario al servidor para ser ingresado en el banco
                        outputStream.writeInt(opcion);
                        outputStream.writeObject(nuevoUsuario);
                        outputStream.flush();

                        // Recibir la confirmación del servidor
                        String respuestaServidor = inputStream.readUTF();
                        System.out.println(respuestaServidor);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    // Lógica para crear una nueva cuenta bancaria
                    System.out.println("Opción seleccionada: Crear una nueva cuenta bancaria");

                    // Solicitar al operario que ingrese el número de cuenta
                    System.out.println("Ingrese el número de la nueva cuenta:");
                    int nuevoNumeroCuenta = scanner.nextInt();

                    // Solicitar al operario que ingrese el nombre del usuario para asociar la cuenta
                    System.out.println("Ingrese el nombre del usuario asociado a la cuenta:");
                    String nombreUsuario = scanner.nextLine();

                    // Solicitar al servidor que busque al usuario en la base de datos
                    try {
                        outputStream.writeUTF(nombreUsuario);
                        outputStream.flush();

                        // Recibir la respuesta del servidor
                        String respuestaServidor = inputStream.readUTF();

                        if (respuestaServidor.equals("Usuario encontrado")) {
                            // Si el usuario está en la base de datos, continuar con la creación de la cuenta

                            // Crear un nuevo objeto de tipo User con el nombre del usuario proporcionado
                            User usuarioAsociado = new User();
                            usuarioAsociado.setName(nombreUsuario);

                            // Crear un nuevo objeto de tipo Account con los datos ingresados
                            Account nuevaCuenta = new Account();
                            nuevaCuenta.setAcountNumber(nuevoNumeroCuenta);
                            nuevaCuenta.setBalance(0); // Establecer el saldo inicial en 0
                            nuevaCuenta.setUser(usuarioAsociado); // Establecer el usuario asociado a la cuenta

                            // Enviar la nueva cuenta al servidor para ser creada
                            outputStream.writeObject(nuevaCuenta);
                            outputStream.flush();

                            // Recibir la confirmación del servidor
                            String respuestaCreacionCuenta = inputStream.readUTF();
                            System.out.println(respuestaCreacionCuenta);
                        } else {
                            // Si el usuario no se encuentra en la base de datos, mostrar un mensaje y volver al menú
                            System.out.println("El usuario proporcionado no se encuentra en la base de datos.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;


                case 3:
                    // Lógica para ver los datos de una cuenta bancaria
                    System.out.println("Opción seleccionada: Ver los datos de una cuenta bancaria");

                    // Solicitar al operario que ingrese el número de cuenta
                    System.out.println("Ingrese el número de cuenta:");
                    int accountNumber = scanner.nextInt();

                    try {
                        // Enviar el número de cuenta al servidor
                        outputStream.writeInt(accountNumber);
                        outputStream.flush();

                        // Recibir la cuenta del servidor
                        Account cuenta = (Account) inputStream.readObject();

                        // Verificar si se encontró la cuenta
                        if (cuenta != null) {
                            // Mostrar los detalles de la cuenta
                            System.out.println("Datos de la cuenta:");
                            System.out.println("ID de cuenta: " + cuenta.getId());
                            System.out.println("Saldo: " + cuenta.getBalance());
                            // Puedes mostrar más detalles de la cuenta si lo deseas
                        } else {
                            System.out.println("La cuenta con el número proporcionado no fue encontrada.");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    // Lógica para ver los datos de un cliente
                    System.out.println("Opción seleccionada: Ver los datos de un cliente");

                    // Solicitar al operario que ingrese el nombre de usuario del cliente
                    System.out.println("Ingrese el nombre de usuario del cliente:");
                    String nombreCliente = scanner.nextLine();

                    try {
                        // Enviar el nombre de usuario al servidor
                        outputStream.writeUTF(nombreCliente);
                        outputStream.flush();

                        // Recibir los datos del cliente desde el servidor
                        User cliente = (User) inputStream.readObject();

                        // Verificar si se encontró el cliente
                        if (cliente != null) {
                            // Mostrar los detalles del cliente
                            System.out.println("Datos del cliente:");
                            System.out.println("Nombre de usuario: " + cliente.getName());
                            // Mostrar más detalles del cliente si es necesario
                        } else {
                            System.out.println("El cliente con el nombre de usuario proporcionado no fue encontrado.");
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    // Lógica para eliminar una cuenta bancaria
                    System.out.println("Opción seleccionada: Eliminar una cuenta bancaria");

                    // Solicitar al operario que ingrese el número de cuenta a eliminar
                    System.out.println("Ingrese el número de cuenta a eliminar:");
                    int accountNumberToDelete = scanner.nextInt();

                    try {
                        // Enviar el número de cuenta al servidor para eliminar la cuenta
                        outputStream.writeInt(accountNumberToDelete);
                        outputStream.flush();

                        // Recibir la confirmación del servidor
                        String respuestaServidor = inputStream.readUTF();
                        System.out.println(respuestaServidor);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case 6:
                    // Opción para salir
                    System.out.println("Cerrando sesión...");

                    // Limpiar el flujo de entrada
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    // Volver al inicio de sesión
                    System.out.println("Ingrese su nombre de usuario:");
                    String nombreUsuarioo = scanner.nextLine();
                    System.out.println("Ingrese su contraseña:");
                    String contraseña = scanner.nextLine();

                    // Hash de la contraseña
                    String hashContraseña = String.valueOf(hash(contraseña));

                    // Crear objeto de credenciales
                    Credentials credentials = new Credentials(nombreUsuarioo, hashContraseña);

                    // Enviar credenciales al servidor
                    try {
                        outputStream.writeObject(credentials);

                        // Recibir respuesta del servidor
                        String respuesta = inputStream.readUTF();
                        System.out.println(respuesta);

                        // Si la respuesta indica un inicio de sesión exitoso, mostrar el menú correspondiente
                        if (respuesta.equals("Inicio de sesión exitoso")) {
                            // Leer el rol del usuario del servidor
                            String rol = inputStream.readUTF();

                            // Obtener el usuario del servidor
                            //usuario = (User) inputStream.readObject();

                            // Mostrar el menú correspondiente según el rol
                            switch (rol) {
                                case "Cajero":
                                    //mostrarMenuCajero(outputStream, inputStream);
                                    break;
                                case "Operario":
                                    mostrarMenuOperario(outputStream, inputStream);
                                    break;
                                default:
                                    System.out.println("Error: Rol desconocido");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
