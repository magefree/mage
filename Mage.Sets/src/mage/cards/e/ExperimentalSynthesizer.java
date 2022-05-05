package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SamuraiToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExperimentalSynthesizer extends CardImpl {

    public ExperimentalSynthesizer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        // When Experimental Synthesizer enters or leaves the battlefield, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(
                new ExileTopXMayPlayUntilEndOfTurnEffect(1), false
        ));

        // {2}{R}, Sacrifice Experimental Synthesizer: Create a 2/2 white Samurai creature token with vigilance. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new SamuraiToken()), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ExperimentalSynthesizer(final ExperimentalSynthesizer card) {
        super(card);
    }

    @Override
    public ExperimentalSynthesizer copy() {
        return new ExperimentalSynthesizer(this);
    }
}
