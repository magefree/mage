package mage.cards.i;

import mage.abilities.common.delayed.PactDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class InterventionPact extends CardImpl {

    public InterventionPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{0}");

        this.color.setWhite(true);

        // The next time a source of your choice would deal damage to you this turn, prevent that damage. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(
                new PreventNextDamageFromChosenSourceEffect(
                        Duration.EndOfTurn, true,
                        PreventNextDamageFromChosenSourceEffect.ON_PREVENT_YOU_GAIN_THAT_MUCH_LIFE
                )
        );
        // At the beginning of your next upkeep, pay {1}{W}{W}. If you don't, you lose the game.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PactDelayedTriggeredAbility(new ManaCostsImpl<>("{1}{W}{W}")), false));
    }

    private InterventionPact(final InterventionPact card) {
        super(card);
    }

    @Override
    public InterventionPact copy() {
        return new InterventionPact(this);
    }
}