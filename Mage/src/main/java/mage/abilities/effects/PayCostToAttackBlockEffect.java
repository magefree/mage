
package mage.abilities.effects;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCosts;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public interface PayCostToAttackBlockEffect extends ReplacementEffect {

    ManaCosts getManaCostToPay(GameEvent event, Ability source, Game game);

    Cost getOtherCostToPay(GameEvent event, Ability source, Game game);

    boolean isCostless(GameEvent event, Ability source, Game game);
}
