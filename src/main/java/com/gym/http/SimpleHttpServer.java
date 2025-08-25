package com.gym.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
    public static void main(String[] args) throws IOException{
        ServerSocket serverSocket = new ServerSocket(8080); // 서버 포트 열기 - 포트 바인드
        System.out.println("서버 start! http://localhost:8080");

        while (true){
            Socket socket = serverSocket.accept(); // blocking call. 클라이언트 요청 수락
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String requestLine = in.readLine(); // 첫 줄만 읽어옴
            System.out.println("Request : " + requestLine);

            // simple HTTP 응답
            String body = "<h1>It's me, HTTP!</h1>";
            out.write("HTTP/1.1 200 OK\r\n");
            out.write("content-Type: text/html\r\n");
            out.write("Content-Length: " + body.getBytes().length + "\r\n");
            out.write("\r\n");
            out.write(body);

            out.flush();
            socket.close();
        }
    }
}
