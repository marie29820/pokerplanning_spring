package fr.pokerplanning.config.cache.copier;

import com.google.gson.Gson;
import fr.pokerplanning.dao.cache.dto.Room;
import org.ehcache.spi.copy.Copier;
import org.springframework.stereotype.Component;

@Component
public class RoomCopier implements Copier<Room> {

    private final Gson gson;

    public RoomCopier(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Room copyForRead(Room obj) {
        return gson.fromJson(gson.toJson(obj), Room.class);
    }

    @Override
    public Room copyForWrite(Room obj) {
        return gson.fromJson(gson.toJson(obj), Room.class);
    }
}
