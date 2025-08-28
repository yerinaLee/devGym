package com.gym.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class MultiChatServer {

    // 연결된 클라이언트 목록
  /*  private static final List<PrintWriter> clientOutputs = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        int port = 9000;
        System.out.println("채팅 서버 시작! 포트 : " + port);

        try(ServerSocket serverSocket = new ServerSocket(port)){
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println("새 클라이언트 접속: " + socket);

                // 클라이언트 전용 스레드 생성
                new Thread(new ClientHandler(socket)).start();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }*/
    
    // 중복 방지를 위해 list -> set으로 자료구조 변경
    private static Set<ClientHandler> clients = Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("채팅 서버 시작! 포트 9000");

        while (true){
            Socket socket = serverSocket.accept();
            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    // 클라이언트 연결을 처리하는 클래스
    private static class ClientHandler implements Runnable{
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String nickname;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            try{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // 닉네임 입력 받기
                out.println("닉네임을 입력하세요: ");
                nickname = in.readLine();
                System.out.println(nickname + " 접속");

                broadcast("[알림] "+nickname+"님이 입장했습니다~~~소리질러~~~~", this);

                String msg;
                while ((msg = in.readLine()) != null){
                    System.out.println(nickname + ": " + msg);
                    broadcast("상대방(" + nickname + "): " + msg, this);
                    selfBroadcast("나: " + msg, this);
                }
            } catch (IOException e){
                System.out.println("클라이언트 연결 종료: " + socket);
            } finally {
                try{
                    clients.remove(this);
                    if (socket != null) socket.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
                broadcast("[알림] " + nickname + "님이 퇴장했습니다. 쏘쌛....", this);
            }
        }

        // 모든 클라이언트에게 메시지 전송
        private void broadcast(String msg, ClientHandler sender){
            synchronized (clients){
                for (ClientHandler client : clients){
                    if (client != sender){ // 메세지 전송한 본인 제외
                        client.out.println(msg);
                    }
                }
            }
        }
        
        // 작성자 자신에게 메시지 전송
        private  void selfBroadcast(String msg, ClientHandler sender){
            sender.out.println(msg);
        }
    }
}