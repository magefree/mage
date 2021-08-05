package mage.cards.t;

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

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeachByExample extends CardImpl {

    public TeachByExample(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U/R}{U/R}");

        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(
                new CreateDelayedTriggeredAbilityEffect(new TeachByExampleAbility())
                        .setText("When you cast your next instant or sorcery spell this turn, "
                                + "copy that spell. You may choose new targets for the copy")
        );
    }

    private TeachByExample(final TeachByExample card) {
        super(card);
    }

    @Override
    public TeachByExample copy() {
        return new TeachByExample(this);
    }
}

class TeachByExampleAbility extends DelayedTriggeredAbility {

    TeachByExampleAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
    }

    private TeachByExampleAbility(final TeachByExampleAbility ability) {
        super(ability);
    }

    @Override
    public TeachByExampleAbility copy() {
        return new TeachByExampleAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
        return true;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn, "
                + "copy that spell. You may choose new targets for the copy.";
    }
}
