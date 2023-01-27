package fr.pokerplanning.dao.cache.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Room {
    private String id;
    private Step step;
    private List<Player> players = new ArrayList<>();
}
