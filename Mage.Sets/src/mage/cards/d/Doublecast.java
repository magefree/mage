package mage.cards.d;

import java.util.UUID;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
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
public final class Doublecast extends CardImpl {

    public Doublecast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{R}");

        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(
                new CreateDelayedTriggeredAbilityEffect(new DoublecastAbility())
                        .setText("When you cast your next instant or sorcery spell this turn, "
                                + "copy that spell. You may choose new targets for the copy")
        );
    }

    public Doublecast(final Doublecast card) {
        super(card);
    }

    @Override
    public Doublecast copy() {
        return new Doublecast(this);
    }
}

class DoublecastAbility extends DelayedTriggeredAbility {

    public DoublecastAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
    }

    public DoublecastAbility(final DoublecastAbility ability) {
        super(ability);
    }

    @Override
    public DoublecastAbility copy() {
        return new DoublecastAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && (spell.isInstant() || spell.isSorcery())) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you cast your next instant or sorcery spell this turn, "
                + "copy that spell. You may choose new targets for the copy.";
    }
}
