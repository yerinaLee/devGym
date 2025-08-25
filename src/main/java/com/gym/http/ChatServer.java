package com.gym.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("채팅 서버 시작! 포트 : 9000");

        Socket client1 = serverSocket.accept(); // accept() 호출시 연결된 Socket 생성
        System.out.println("클라이언트1 연결됨");
        System.out.println(client1.getInetAddress());
        Socket client2 = serverSocket.accept();
        System.out.println("클라이언트2 연결됨");
        System.out.println(client1.getInetAddress());

        new Thread(() -> relayMessages(client1, client2)).start();
        new Thread(() -> relayMessages(client2, client1)).start();
    }

    private static void relayMessages(Socket from, Socket to){
        try(BufferedReader in = new BufferedReader(new InputStreamReader(from.getInputStream()));
            PrintWriter out = new PrintWriter(to.getOutputStream(), true)){
            String msg;
            while ((msg = in.readLine()) != null) {
                out.println(msg);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
