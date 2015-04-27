package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;

public interface DynamicCost {
	
	Cost getCost(Ability ability, Game game);
	
	String getText(Ability ability, Game game);

}
