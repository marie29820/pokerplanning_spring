package fr.pokerplanning.dao.cache.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Room implements Serializable {
    private String id;
    private Step step;
    private List<Player> players = new ArrayList<>();
}
