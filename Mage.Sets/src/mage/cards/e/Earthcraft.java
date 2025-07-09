package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Earthcraft extends CardImpl {

    private static final FilterPermanent filterLand = new FilterLandPermanent("basic land");

    static {
        filterLand.add(SuperType.BASIC.getPredicate());
    }

    public Earthcraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Tap an untapped creature you control: Untap target basic land.
        Ability ability = new SimpleActivatedAbility(
                new UntapTargetEffect(),
                new TapTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE))
        );
        ability.addTarget(new TargetPermanent(filterLand));
        this.addAbility(ability);
    }

    private Earthcraft(final Earthcraft card) {
        super(card);
    }

    @Override
    public Earthcraft copy() {
        return new Earthcraft(this);
    }
}
