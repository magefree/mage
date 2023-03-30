package mage.cards.r;

import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelicOfLegends extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("untapped legendary creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public RelicOfLegends(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Tap an untapped legendary creature you control: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new TapTargetCost(new TargetControlledPermanent(filter))));
    }

    private RelicOfLegends(final RelicOfLegends card) {
        super(card);
    }

    @Override
    public RelicOfLegends copy() {
        return new RelicOfLegends(this);
    }
}
