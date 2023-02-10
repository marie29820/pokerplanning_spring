package fr.pokerplanning.controller;

import fr.pokerplanning.controller.model.PlayerJson;
import fr.pokerplanning.dao.cache.dto.Step;
import fr.pokerplanning.service.RoomService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Validated
public class PokerController {

    private static final String REGEX_UUID = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
    private static final String REGEX_ROOMID = REGEX_UUID;
    private static final String REGEX_SESSIONID = REGEX_UUID;
    public static final String TOPIC_ROOM = "/topic/room/";
    private final RoomService roomService;
    private final SimpMessageSendingOperations messagingTemplate;


    @Autowired
    public PokerController(RoomService roomService, SimpMessageSendingOperations messagingTemplate) {
        this.roomService = roomService;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping(path = "/")
    public ResponseEntity<String> get() {
        return ResponseEntity.ok("hello, use /stomp to connect with sockjs");
    }

    @MessageMapping("/{roomId}/player")
    public void addPlayer(@Pattern(regexp = REGEX_SESSIONID) @Header("simpSessionId") String sessionId,
                          @Pattern(regexp = REGEX_ROOMID) @DestinationVariable String roomId,
                          PlayerJson player) {
        var room = roomService.addPlayer(sessionId, roomId, player.getName());
        messagingTemplate.convertAndSend(TOPIC_ROOM + roomId, room);
    }

    @MessageMapping("/{roomId}/play")
    public void playGame(@Pattern(regexp = REGEX_SESSIONID) @Header("simpSessionId") String sessionId,
                         @Pattern(regexp = REGEX_ROOMID) @DestinationVariable String roomId,
                         PlayerJson player) throws Exception {
        var room = roomService.play(roomId, sessionId, player.getCard());
        messagingTemplate.convertAndSend(TOPIC_ROOM + roomId, room);
    }

    @MessageMapping("/{roomId}/reveal")
    public void revealGame(@Pattern(regexp = REGEX_ROOMID) @DestinationVariable String roomId) {
        var room = roomService.revealRoom(roomId);
        messagingTemplate.convertAndSend(TOPIC_ROOM + roomId, room);
    }

    @MessageMapping("/{roomId}/reset")
    public void resetGame(@Pattern(regexp = REGEX_ROOMID) @DestinationVariable String roomId) {
        var room = roomService.resetRoom(roomId);
        messagingTemplate.convertAndSend(TOPIC_ROOM + roomId, room);
    }
}
