package mage.cards.g;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TimingRule;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GalvanicIteration extends CardImpl {

    public GalvanicIteration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // When you cast your next instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new GalvanicIterationAbility()));

        // Flashback {1}{U}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{U}{R}")));
    }

    private GalvanicIteration(final GalvanicIteration card) {
        super(card);
    }

    @Override
    public GalvanicIteration copy() {
        return new GalvanicIteration(this);
    }
}

class GalvanicIterationAbility extends DelayedTriggeredAbility {

    GalvanicIterationAbility() {
        super(new CopyTargetSpellEffect(true), Duration.EndOfTurn);
    }

    private GalvanicIterationAbility(final GalvanicIterationAbility ability) {
        super(ability);
    }

    @Override
    public GalvanicIterationAbility copy() {
        return new GalvanicIterationAbility(this);
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
