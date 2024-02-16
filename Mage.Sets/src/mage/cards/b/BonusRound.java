
package mage.cards.b;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class BonusRound extends CardImpl {

    public BonusRound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Until end of turn, whenever a player casts an instant or sorcery spell, that player copies it and may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BonusRoundDelayedTriggeredAbility()));
    }

    private BonusRound(final BonusRound card) {
        super(card);
    }

    @Override
    public BonusRound copy() {
        return new BonusRound(this);
    }
}

class BonusRoundDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public BonusRoundDelayedTriggeredAbility() {
        super(new CopyTargetSpellEffect(true, true), Duration.EndOfTurn, false);
    }

    private BonusRoundDelayedTriggeredAbility(final BonusRoundDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BonusRoundDelayedTriggeredAbility copy() {
        return new BonusRoundDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.isInstantOrSorcery(game)) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a player casts an instant or sorcery spell, "
                + "that player copies it and may choose new targets for the copy";
    }
}
