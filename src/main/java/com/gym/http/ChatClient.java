package com.gym.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 9000);
        System.out.println("서버 연결됨!");

        // 수신 스레드
        new Thread(() -> {
            try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))){
                String msg;
                while((msg = in.readLine())!=null){
                    System.out.println(msg);
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }).start();

        // 송신 루프
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)){
            while (true){
                String input = scanner.nextLine();
                out.println(input);
            }
        }
    }
}
