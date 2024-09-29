
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */

public class CantActivateAbilitiesAttachedEffect extends ContinuousRuleModifyingEffectImpl {

    public CantActivateAbilitiesAttachedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.UnboostCreature);
        staticText = "Enchanted creature's activated abilities can't be activated";
    }

    protected CantActivateAbilitiesAttachedEffect(final CantActivateAbilitiesAttachedEffect effect) {
        super(effect);
    }

    @Override
    public CantActivateAbilitiesAttachedEffect copy() {
        return new CantActivateAbilitiesAttachedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (event.getSourceId().equals(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }
}
