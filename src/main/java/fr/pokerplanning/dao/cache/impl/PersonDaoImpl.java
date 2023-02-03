package fr.pokerplanning.dao.cache.impl;

import fr.pokerplanning.dao.cache.PersonDao;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PersonDaoImpl implements PersonDao {
    @Cacheable(cacheNames = "personCacheManager", key = "#personId")
    @Override
    public String get(String personId) {
        return "";
    }

    @Override
    @CachePut(cacheNames = "personCacheManager", key = "#personId")
    public String put(String personId, String roomId) {
        return roomId;
    }

    @CacheEvict(cacheNames = "personCacheManager", key = "#personId")
    @Override
    public void delete(String personId) {
        // - delete person
    }
}
