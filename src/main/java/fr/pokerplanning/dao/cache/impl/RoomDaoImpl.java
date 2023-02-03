package fr.pokerplanning.dao.cache.impl;

import fr.pokerplanning.dao.cache.RoomDao;
import fr.pokerplanning.dao.cache.dto.Room;
import fr.pokerplanning.dao.cache.dto.Step;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RoomDaoImpl implements RoomDao {

    @Cacheable(cacheNames = "roomCacheManager", key = "#roomId")
    @Override
    public Room get(String roomId) {
        return new Room().setId(roomId).setStep(Step.HIDDEN);
    }

    @CachePut(cacheNames = "roomCacheManager", key = "#room.id")
    @Override
    public Room put(Room room) {
        return room;
    }

}
