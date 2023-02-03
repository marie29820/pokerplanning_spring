package fr.pokerplanning.config.socket;

import fr.pokerplanning.controller.PokerController;
import fr.pokerplanning.dao.cache.PersonDao;
import fr.pokerplanning.dao.cache.RoomDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class WebSocketEventListener {
    private final RoomDao roomDao;
    private final PersonDao personDao;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public WebSocketEventListener(RoomDao roomDao, PersonDao personDao, SimpMessageSendingOperations messagingTemplate) {
        this.roomDao = roomDao;
        this.personDao = personDao;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        // - recuperer la room associée au user
        var roomId = personDao.get(event.getSessionId());
        // - supprimer l'association personne/room
        personDao.delete(event.getSessionId());
        var room = roomDao.get(roomId);
        // - Maj de la room
        roomDao.put(room.setPlayers(room.getPlayers().stream().filter(player ->
                !player.getId().equals(event.getSessionId())
        ).collect(Collectors.toCollection(ArrayList::new))));
        messagingTemplate.convertAndSend(PokerController.TOPIC_ROOM + roomId, room);
    }

}
