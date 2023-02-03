package fr.pokerplanning.config.cache;

import fr.pokerplanning.config.cache.copier.RoomCopier;
import fr.pokerplanning.config.cache.copier.StringCopier;
import fr.pokerplanning.dao.cache.dto.Room;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import java.time.Duration;

@Configuration
@EnableCaching
@Slf4j
public class EhcacheConfig {
    @Bean
    public CacheManager roomCacheManager() {
        var roomCacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, Room.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB)
                                .build())
                .withValueCopier(RoomCopier.class)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(2)))
                .build();

        var personCacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, String.class,
                        ResourcePoolsBuilder
                                .newResourcePoolsBuilder()
                                .offheap(10, MemoryUnit.MB)
                                .build())
                .withValueCopier(StringCopier.class)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofHours(2)))
                .build();

        var cachingProvider = Caching.getCachingProvider();
        var cacheManager = cachingProvider.getCacheManager();
        var configurationRoomCache = Eh107Configuration.fromEhcacheCacheConfiguration(roomCacheConfig);
        var configurationPersonCache = Eh107Configuration.fromEhcacheCacheConfiguration(personCacheConfig);

        cacheManager.createCache("roomCacheManager", configurationRoomCache);
        cacheManager.createCache("personCacheManager", configurationPersonCache);
        return cacheManager;
    }

}
