package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.delayed.PactDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.GiantToken;

/**
 *
 * @author Plopman
 */
public final class PactOfTheTitan extends CardImpl {

    public PactOfTheTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{0}");

        this.color.setRed(true);

        // Create a 4/4 red Giant creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GiantToken()));

        // At the beginning of your next upkeep, pay {4}{R}. If you don't, you lose the game.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PactDelayedTriggeredAbility(new ManaCostsImpl<>("{4}{R}"))));
    }

    private PactOfTheTitan(final PactOfTheTitan card) {
        super(card);
    }

    @Override
    public PactOfTheTitan copy() {
        return new PactOfTheTitan(this);
    }
}
