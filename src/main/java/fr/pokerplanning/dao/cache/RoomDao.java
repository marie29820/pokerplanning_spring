package fr.pokerplanning.dao.cache;

import fr.pokerplanning.dao.cache.dto.Room;

public interface RoomDao {
    Room get(String roomId);

    Room put(Room room);
}
