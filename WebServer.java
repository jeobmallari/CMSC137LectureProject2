import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

  protected void start() {
    ServerSocket s;
    File f;
    System.out.println("Webserver starting up on port 80");
    System.out.println("(press ctrl-c to exit)");
    try{
		s = new ServerSocket(80);
	}catch(Exception e){
		System.out.println("Error: " + e);
		return;
    }

    System.out.println("Waiting for connection");
    f = new File("WebServer.html");
    for (;;) {
		try {
			Socket remote = s.accept();
			System.out.println("Connection, sending data.");
			BufferedReader in = new BufferedReader(new InputStreamReader(remote.getInputStream()));
			PrintWriter out = new PrintWriter(remote.getOutputStream());
			PrintWriter toFile = new PrintWriter(new BufferedWriter(new FileWriter(f)));
			String[] htmlReq = new String[64];

			String str = ".";
			int a = 0;
			while (!str.equals("")){
			str = in.readLine();
			htmlReq[a] = str+"</br>";
			a++;
        }
		out.println("HTTP/1.0 200 OK");
		out.println("Content-Type: text/html");
		out.println("Server: Bot");
		out.println("DirectoryIndex 8080 project.html");
		out.println("");
		out.println("<H1>CMSC 137 Lecture Project 2 (Web Server)</H1>\n");
		out.println("<p>HTML REQUEST: </br>");
		out.println("<table border=\"1\" style=\"width:100%\">");
/*
		toFile.println("HTTP/1.0 200 OK");
		toFile.println("Content-Type: text/html");
		toFile.println("Server: Bot");
		toFile.println("DirectoryIndex 8080 project.html");
		toFile.println("");
		toFile.println("<H1>CMSC 137 Lecture Project 2 (Web Server)</H1>\n");
		toFile.println("<p>HTML REQUEST: </br>");
		toFile.println("<table border=\"1\" style=\"width:100%\">");
*/		
		for(int i=0;i<a-1;i++){
			String[] tokens = htmlReq[i].split(" ");
			int length = tokens.length;
			String rest = "";
			out.println("<tr>");
			out.println("<td>");
			out.println(tokens[0]);
			out.println("</td>");

			toFile.println("<tr>");
			toFile.println("<td>");
			toFile.println(tokens[0]);
			toFile.println("</td>");

        	
        	for(int b=1;b<length;b++){
        		rest += tokens[b] + " ";
        	}
        	
			out.println("<td>");
			out.println(rest);
			out.println("</td>");
			out.println("</tr>");

			toFile.println("<td>");
			toFile.println(rest);
			toFile.println("</td>");
			toFile.println("</tr>");
		}
		out.println("</table>");
		out.flush();

		toFile.println("</table>");
		toFile.flush();
			remote.close();
		}catch(Exception e) {
			System.out.println("Error: " + e);
		}
	}
}

	public static void main(String args[]) {
		WebServer ws = new WebServer();
		ws.start();
	}
}
