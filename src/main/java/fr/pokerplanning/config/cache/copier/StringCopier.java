package fr.pokerplanning.config.cache.copier;

import org.ehcache.spi.copy.Copier;

public class StringCopier  implements Copier<String> {
    @Override
    public String copyForRead(String obj) {
        return new String(obj);
    }

    @Override
    public String copyForWrite(String obj) {
        return new String(obj);
    }
}
