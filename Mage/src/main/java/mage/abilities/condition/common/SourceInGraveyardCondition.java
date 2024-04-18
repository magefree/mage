package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.game.Game;

/**
 * If {this} is in your graveyard
 *
 * @author Susucr
 */

public enum SourceInGraveyardCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD;
    }
}
