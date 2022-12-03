package com.exemple.reactor.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {

        Socket clientSocket = new Socket("localhost", 7070);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();

        out.println(str);
        String str2 = in.readLine();
        System.out.println("Server says: " + str2);

        in.close();
        out.close();
        clientSocket.close();
    }
}
