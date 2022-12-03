package com.exemple.proactor.client;

import java.net.*;
import java.io.*;

public class Client {
    // ? Semplice client Socket per testare il server
    public static void main(String args[]) throws Exception {
        Socket s = new Socket("localhost", 4333);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine();

        out.println(str);
        String str2 = in.readLine();
        System.out.println("Server says: " + str2);

        in.close();
        out.close();
        s.close();
    }
}
