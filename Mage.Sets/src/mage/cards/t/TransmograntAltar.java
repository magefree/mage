package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AshnodZombieToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TransmograntAltar extends CardImpl {

    public TransmograntAltar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {B}, {T}, Sacrifice a creature: Add {C}{C}{C}.
        Ability ability = new SimpleManaAbility(
                Zone.BATTLEFIELD, Mana.ColorlessMana(3), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);

        // {2}, {T}, Sacrifice a creature: Create a 3/3 colorless Zombie artifact creature token. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                new CreateTokenEffect(new AshnodZombieToken()), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        this.addAbility(ability);
    }

    private TransmograntAltar(final TransmograntAltar card) {
        super(card);
    }

    @Override
    public TransmograntAltar copy() {
        return new TransmograntAltar(this);
    }
}
