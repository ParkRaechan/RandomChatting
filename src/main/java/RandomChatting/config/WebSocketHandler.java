package RandomChatting.config;

import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @Autowired  : 5계층 [ @Service @Controller  @Repository @Entity 등 자동 클래스 스캔 ]
@Component // 5계층 외 클래스에서 @Autowired 사용시 해당 클래스 스캔 위한 @Component  주입
public class WebSocketHandler extends TextWebSocketHandler {
                                                                // 문자 웹소켓 핸들러 클래스   // 웹소켓의 이벤트에 따른 메소드 실행
    // 1.접속된 세션들을 저장하는 리스트 선언
    private Map<WebSocketSession,String> list = new HashMap<>();
    @Override // 1. 웹소켓과 연결 되었을때 메소드 재정의
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 경로에서 아이디 추출
        String path = session.getUri().getPath();
        String mid = path.substring( path.lastIndexOf("/") +1 );
        // 세션과 아이디 같이 저장
        list.put( session , mid);

        // 2. 웹소켓과 접속시 리스트에 접속된 세션 담기
//        list.add( session );
//        System.out.println(session);
    }
    @Override // 2. 웹소켓과 연결이 종료 되었을때 메소드 재정의
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 3. 웹소켓과 접속이 종료되었을때 종료된 세션 지우기
        list.remove( session );
    }
    @Override // 3. 메시지를 받을때 메소드 재정의
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject object = new JSONObject( message.getPayload() ); // Payload() : 메시지내용
        for( WebSocketSession socketSession : list.keySet()  ){    // 모든 키값 호출
            if( list.get( socketSession).equals( object.get("to")  ) ){
                socketSession.sendMessage( message );
            }
//            if( list.get( socketSession).equals( object.get("to")  ) ){
//                socketSession.sendMessage( message );
//            }
        }


        // 4. JS에서 send 메소드가 들어왔을때 현재 접속된 모든 세션에게 전달
//        for( WebSocketSession socketobject : list ){ // 모든 접속된 세션 반복문
//            socketobject.sendMessage(message); // 해당 세션에게 메시지 전달
//        }
    }
}
