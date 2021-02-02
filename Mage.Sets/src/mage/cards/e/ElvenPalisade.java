
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author TheElk801
 */
public final class ElvenPalisade extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Forest");

    static {
        filter.add(SubType.FOREST.getPredicate());
    }

    public ElvenPalisade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // Sacrifice a Forest: Target attacking creature gets -3/-0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(-3, 0, Duration.EndOfTurn), new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private ElvenPalisade(final ElvenPalisade card) {
        super(card);
    }

    @Override
    public ElvenPalisade copy() {
        return new ElvenPalisade(this);
    }
}
