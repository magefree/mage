
package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.constants.AttachmentType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author Galatolol
 */
public class CantAttackUnlessPaysAttachedEffect extends PayCostToAttackBlockEffectImpl {

    public CantAttackUnlessPaysAttachedEffect(ManaCosts manaCosts, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK, manaCosts);
        staticText = attachmentType.verb()
                + " creature can't attack unless its controller pays "
                + (manaCosts == null ? "" : manaCosts.getText());
    }

    public CantAttackUnlessPaysAttachedEffect(CantAttackUnlessPaysAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
                return event.getSourceId().equals(enchantment.getAttachedTo());
            }
        }
        return false;
    }

    @Override
    public CantAttackUnlessPaysAttachedEffect copy() {
        return new CantAttackUnlessPaysAttachedEffect(this);
    }
}
