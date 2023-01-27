package fr.pokerplanning.service;

import com.google.gson.Gson;
import fr.pokerplanning.dao.cache.CacheDao;
import fr.pokerplanning.dao.cache.dto.Player;
import fr.pokerplanning.dao.cache.dto.Room;
import fr.pokerplanning.dao.cache.dto.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private CacheDao cacheDao;

    private Gson gson;

    @Autowired
    public RoomService(CacheDao cacheDao, Gson gson) {
        this.cacheDao = cacheDao;
        this.gson = gson;
    }

    public Room getRoom(String roomId) {
        return cacheDao.get(new Room()
                .setId(roomId)
        );
    }

    public Room resetRoom(String roomId) {
        var room = getRoom(roomId)
                .setStep(Step.HIDDEN);
        room.getPlayers().forEach(
                player -> player.setCard(null)
        );
        return room;
    }

    public Room createRoom() {
        return cacheDao.get(new Room()
                .setId(RandomStringUtils.randomAlphabetic(30))
                .setStep(Step.HIDDEN)
        );
    }

    public Room addPlayer(String roomId, String playerId, String playerName) {
        var room = getRoom(roomId);
        room.getPlayers().add(new Player()
                .setName(playerName)
                .setId(playerId)
        );
        return room;
    }

    public Room deletePlayer(String roomId, String playerId) {
        var room = getRoom(roomId);
        return room.setPlayers(room.getPlayers().stream().filter(player ->
                !player.getId().equals(playerId)
        ).collect(Collectors.toCollection(ArrayList::new)));
    }

    public Room play(String roomId, String playerId, String cardValue) {
        var room = getRoom(roomId)
                .setStep(Step.HIDDEN);

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
        var clone = gson.fromJson(gson.toJson(room), Room.class);
        // - mask card
        return clone.setPlayers(
                clone.getPlayers()
                        .stream()
                        .map(
                                player ->
                                        StringUtils.isNotEmpty(player.getCard()) ? player.setCard("*") : player)
                        .toList());
    }
}
