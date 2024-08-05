package mage.abilities.condition.common;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.Optional;

/**
 * @author TheElk801
 */
public enum CastFromGraveyardSourceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source)
                .map(Ability::getSourceId)
                .map(game::getSpell)
                .map(Spell::getFromZone)
                .map(Zone.GRAVEYARD::match)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "this spell was cast from a graveyard";
    }
}
