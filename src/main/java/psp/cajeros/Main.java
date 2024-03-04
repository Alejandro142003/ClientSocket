package psp.cajeros;

import java.io.*;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 12345);

            // Establecer flujos de entrada y salida
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());

            // Solicitar credenciales al usuario
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese su nombre de usuario:");
            String usuario = reader.readLine();
            System.out.println("Ingrese su contraseña:");
            String contraseña = reader.readLine();

            // Enviar credenciales al servidor (hasheadas)
            String hashUsuario = hash(usuario);
            String hashContraseña = hash(contraseña);
            outputStream.writeUTF(hashUsuario);
            outputStream.writeUTF(hashContraseña);

            // Recibir respuesta del servidor
            String respuesta = inputStream.readUTF();
            System.out.println(respuesta);

            if (respuesta.equals("Inicio de sesión exitoso")) {
                // Mostrar menú de opciones
                System.out.println("Menú:");
                System.out.println("1. Ver saldo");
                System.out.println("2. Sacar dinero");
                System.out.println("3. Ingresar dinero");
                System.out.println("4. Salir");
                System.out.println("Seleccione una opción:");

                // Leer opción seleccionada por el usuario
                int opcion = Integer.parseInt(reader.readLine());

                // Enviar opción al servidor
                outputStream.writeInt(opcion);

                // Recibir y mostrar resultados
                String resultado = inputStream.readUTF();
                System.out.println(resultado);
            }

            // Cerrar conexión
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para calcular el hash de una cadena (dummy implementation)
    private static String hash(String input) {
        // Esta es una implementación ficticia para simular hashing
        return "hash(" + input + ")";
    }
}