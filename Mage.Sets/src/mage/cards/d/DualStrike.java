package mage.cards.d;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DualStrike extends CardImpl {

    public DualStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // When you cast your next instant or sorcery spell with converted mana cost 4 or less this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new DualStrikeAbility()));

        // Foretell {R}
        this.addAbility(new ForetellAbility(this, "{R}"));
    }

    private DualStrike(final DualStrike card) {
        super(card);
    }

    @Override
    public DualStrike copy() {
        return new DualStrike(this);
    }
}

class DualStrikeAbility extends DelayedTriggeredAbility {

    DualStrikeAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
    }

    private DualStrikeAbility(final DualStrikeAbility ability) {
        super(ability);
    }

    @Override
    public DualStrikeAbility copy() {
        return new DualStrikeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!this.isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null
                || !spell.isInstantOrSorcery(game)
                || spell.getManaValue() > 4) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell " +
                "with mana value 4 or less this turn, " +
                "copy that spell. You may choose new targets for the copy.";
    }
}
