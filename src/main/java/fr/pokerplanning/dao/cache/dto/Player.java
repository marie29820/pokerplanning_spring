package fr.pokerplanning.dao.cache.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Player implements Serializable {

    private String id;
    private String name;
    private String card;
}
