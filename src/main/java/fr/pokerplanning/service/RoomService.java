package fr.pokerplanning.service;

import fr.pokerplanning.dao.cache.PersonDao;
import fr.pokerplanning.dao.cache.RoomDao;
import fr.pokerplanning.dao.cache.dto.Player;
import fr.pokerplanning.dao.cache.dto.Room;
import fr.pokerplanning.dao.cache.dto.Step;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomDao roomDao;

    private  final PersonDao personDao;


    @Autowired
    public RoomService(RoomDao roomDao, PersonDao personDao) {
        this.roomDao = roomDao;
        this.personDao = personDao;
    }

    public Room getRoom(String roomId) {
        return roomDao.get(roomId);
    }

    public Room resetRoom(String roomId) {
        var room = roomDao.get(roomId).setStep(Step.HIDDEN);
        room.getPlayers().forEach(player -> player.setCard(null));
        return roomDao.put(room);
    }

    public Room addPlayer(String sessionId, String roomId, String playerName) {
        var room = roomDao.get(roomId);
        room.getPlayers().add(new Player()
                .setName(playerName)
                .setId(sessionId)

        );
        personDao.put(sessionId, room.getId());
        return roomDao.put(room);
    }

    public Room deletePlayer(String sessionId, String roomId) {
        var room = roomDao.get(roomId);
        personDao.delete(sessionId);
        return roomDao.put(room.setPlayers(room.getPlayers().stream().filter(player ->
                !player.getId().equals(sessionId)
        ).collect(Collectors.toCollection(ArrayList::new))));
    }

    public Room play(String roomId, String playerId, String cardValue) {
        var room = roomDao.get(roomId);

        room.getPlayers().stream().filter(
                        player ->
                                player.getId().equals(playerId))
                .findFirst()
                .ifPresentOrElse(
                        player ->
                                player.setCard(cardValue),
                        () -> {
                            throw new RuntimeException("No player found");
                        }
                );

        var updatedRoom = roomDao.put(room);
        // - mask players cards
        return updatedRoom.setPlayers(
                updatedRoom.getPlayers()
                        .stream()
                        .map(
                                player ->
                                        StringUtils.isNotEmpty(player.getCard()) ? player.setCard("*") : player)
                        .toList());
    }
}
