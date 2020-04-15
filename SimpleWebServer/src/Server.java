// A Java program for a Server 
import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class Server {
	private Socket socket = null;
	private ServerSocket server = null;
	private Scanner scan = null;
	private PrintWriter print = null;

	public Server(int port) {
		// waits for a connection
		try {
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			socket = server.accept();
			System.out.println("Client accepted");
			System.out.println("------------------------------------------");

			// takes input from the client socket

			scan = new Scanner(socket.getInputStream());
			print = new PrintWriter(socket.getOutputStream(), true);

			String line = "";

			try {
				int sb = 0, sa = 0;
				while (true) {
					sb = line.length();
					line = line + scan.nextLine();
					sa = line.length();
					if (sa == sb)
						break;
					line = line + "\n";
				}
				// to check if the request doesn't start with GET
				// line=line.substring(1);
				System.out.println(line);
				if (line.startsWith("GET")) {
					print.println("HTTP/1.1 200 OK");
					print.println("Connection: close");
					print.println("Date: " + new Date());
					print.println("Content-Type: text/html");
					print.println();
					print.println(
							"<html><head><title>Simple Webserver</title></head><body><table border='1'><thead><td>Name</td><td>ID</td></thead><tr><td><b>Layth Abufarhah</b></td><td><b>1162636</b></td></tr></table><p>Welcome to <span style='color:green'>Computer Networks<span></p><p>IP Address: "
									+ socket.getInetAddress() + "</p><p>Port Number: " + socket.getPort()
									+ "</p></body></html>");
				} else {
					print.println("HTTP/1.1 405 Method Not Allowed");
					print.println("Cache-Control: no-cache");
					print.println("Pragma: no-cache");
					print.println("Date: " + new Date());
					print.println("Content-Type: application/json; charset=utf-8");
					print.println();
					print.println("{" + "\"Message\"" + ":" + "\"The requested resource does not support http method.\""
							+ "}");
				}
			} catch (Exception i) {
				System.out.println(i);
			}
			System.out.println("Closing connection");

			// close connection
			socket.close();
			scan.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public static void main(String args[]) {
		Server server = new Server(1900);
	}
}