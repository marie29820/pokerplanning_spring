package fr.pokerplanning.dao.cache.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Player {

    private String id;
    private String name;
    private String card;
}
