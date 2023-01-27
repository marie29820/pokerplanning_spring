package fr.pokerplanning.dao.cache.impl;

import fr.pokerplanning.dao.cache.CacheDao;
import fr.pokerplanning.dao.cache.dto.Room;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class CacheDaoImpl implements CacheDao {

    @Cacheable(value="rooms", key="#room.id")
    @Override
    public Room get(Room room) {
        return room;
    }

}
