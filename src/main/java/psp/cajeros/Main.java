package psp.cajeros;

import psp.cajeros.Model.Credenciales;

import java.io.*;
import java.net.Socket;

import static java.util.Objects.hash;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 12345);

            // Establecer flujos de entrada y salida
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

            // Solicitar credenciales al usuario
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Ingrese su nombre de usuario:");
            String usuario = reader.readLine();
            System.out.println("Ingrese su contraseña:");
            String contraseña = reader.readLine();

            // Hash de la contraseña
            String hashContraseña = String.valueOf(hash(contraseña));

            // Crear objeto de credenciales
            Credenciales credenciales = new Credenciales(usuario, hashContraseña);

            // Enviar credenciales al servidor
            outputStream.writeObject(credenciales);

            // Recibir respuesta del servidor
            String respuesta = inputStream.readUTF();
            System.out.println(respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}