package com.gym.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8008);
        System.out.println("서버 시작! http://localhost:8008");

        while (true){
            Socket socket = serverSocket.accept();
            handleClient(socket);
        }
    }

    private static void handleClient(Socket socket) throws IOException {
        // try-with-resources. try 코드 블록 종료 시 close() 자동호출
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))){

            // client 요청 읽기
            String requestLine = in.readLine();
            if(requestLine == null || requestLine.isEmpty()) return;
            System.out.println("Request : " + requestLine);

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];
            System.out.println("method : " + method);
            System.out.println("path : " + path);

            // 헤더 읽기
            Map<String, String> headers = new HashMap<>();
            String line;
            while(!(line = in.readLine()).isEmpty()){
                String[] headerParts = line.split(": ", 2);
                if(headerParts.length == 2){
                    headers.put(headerParts[0], headerParts[1]);
                }
            }

            // POST 요청의 경우 -> Body 읽기
            String body = "";
            if("POST".equalsIgnoreCase(method)){
                int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
                char[] bodyChars = new char[contentLength];
                in.read(bodyChars);
                body = new String(bodyChars);
                System.out.println("Body : " + body);
            }

            // 라우팅 처리
            String responseBody;
            if ("GET".equalsIgnoreCase(method) && path.startsWith("/hello")){
                responseBody = "<h1>Hello GET!</h1><p>Path: " + path + "</p>";
            } else if ("POST".equalsIgnoreCase(method) && path.equals("/submit")){
                responseBody = "<h1>POST Received!</h1><p>Data: " + body + "</p>";
            } else {
                responseBody = "<h1>404 Not Found</h1>";
            }

            // 응답 전송
            writeResponse(out, responseBody);
        } finally {
            socket.close();
        }
    }


    private static void writeResponse(BufferedWriter out, String body) throws IOException {
        out.write("HTTP/1.1 200 OK\r\n");
        out.write("Content=Type: text/html; charset=UTF-8\r\n");
        out.write("Content-Length: " + body.getBytes(StandardCharsets.UTF_8).length + "\r\n");
        out.write("\r\n");
        out.write(body);
        out.flush();
    }
}
