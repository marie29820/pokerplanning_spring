package fr.pokerplanning.dao.cache;

public interface PersonDao {
    String get(String personId);

    String put(String personId, String roomId);

    void delete(String personId);
}
