package fr.pokerplanning.controller.model;

import fr.pokerplanning.controller.PlayerAction;
import lombok.Data;

@Data
public class PlayerJson {
    private String id;
    private String name;
    private String card;
    private PlayerAction action;
}
