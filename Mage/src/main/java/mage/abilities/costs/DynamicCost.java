package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

public interface DynamicCost extends Serializable {

    Cost getCost(Ability ability, Game game);

    String getText(Ability ability, Game game);

}
