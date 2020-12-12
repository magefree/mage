
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
 * @author LevelX2
 */
public class CantAttackBlockUnlessPaysAttachedEffect extends PayCostToAttackBlockEffectImpl {

    public CantAttackBlockUnlessPaysAttachedEffect(ManaCosts manaCosts, AttachmentType attachmentType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK_AND_BLOCK, manaCosts);
        staticText = attachmentType.verb()
                + " creature can't attack or block unless its controller pays "
                + (manaCosts == null ? "" : manaCosts.getText());
    }

    public CantAttackBlockUnlessPaysAttachedEffect(CantAttackBlockUnlessPaysAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
                return event.getSourceId().equals(enchantment.getAttachedTo());
            }
            if (event.getType() == GameEvent.EventType.DECLARE_BLOCKER) {
                return event.getSourceId().equals(enchantment.getAttachedTo());
            }
        }
        return false;
    }

    @Override
    public CantAttackBlockUnlessPaysAttachedEffect copy() {
        return new CantAttackBlockUnlessPaysAttachedEffect(this);
    }
}
