package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;

import java.util.stream.Stream;

/**
 * @author Susucr
 */
public class IsDealtCombatDamageSourceTriggeredAbility extends DealtDamageToSourceTriggeredAbility {

    public IsDealtCombatDamageSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public IsDealtCombatDamageSourceTriggeredAbility(Effect effect, boolean optional) {
        super(effect, optional);
        setTriggerPhrase("Whenever {this} is dealt combat damage, ");
    }

    protected IsDealtCombatDamageSourceTriggeredAbility(final IsDealtCombatDamageSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IsDealtCombatDamageSourceTriggeredAbility copy() {
        return new IsDealtCombatDamageSourceTriggeredAbility(this);
    }

    @Override
    public Stream<DamagedPermanentEvent> filterBatchEvent(GameEvent event, Game game) {
        return super.filterBatchEvent(event, game)
                .filter(e -> e.isCombatDamage());
    }
}
