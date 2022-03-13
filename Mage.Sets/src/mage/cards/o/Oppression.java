
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author dustinconrad
 */
public final class Oppression extends CardImpl {

    public Oppression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");

        // Whenever a player casts a spell, that player discards a card.
        this.addAbility(new OppressionTriggeredAbility());
    }

    private Oppression(final Oppression card) {
        super(card);
    }

    @Override
    public Oppression copy() {
        return new Oppression(this);
    }
}

class OppressionTriggeredAbility extends SpellCastAllTriggeredAbility {

    public OppressionTriggeredAbility() {
        super(new DiscardTargetEffect(1), false);
    }

    public OppressionTriggeredAbility(OppressionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, getControllerId(), this, game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public OppressionTriggeredAbility copy() {
        return new OppressionTriggeredAbility(this);
    }
}
