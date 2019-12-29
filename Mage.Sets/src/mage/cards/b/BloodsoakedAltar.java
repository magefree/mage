package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.DemonToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodsoakedAltar extends CardImpl {

    public BloodsoakedAltar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{B}{B}");

        // {T}, Pay 2 life, Discard a card, Sacrifice a creature: Create a 5/5 black Demon creature token with flying. Activate this ability only any time you could play a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new DemonToken()), new TapSourceCost()
        );
        ability.addCost(new PayLifeCost(2));
        ability.addCost(new DiscardCardCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(
                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        )));
        this.addAbility(ability);
    }

    private BloodsoakedAltar(final BloodsoakedAltar card) {
        super(card);
    }

    @Override
    public BloodsoakedAltar copy() {
        return new BloodsoakedAltar(this);
    }
}
