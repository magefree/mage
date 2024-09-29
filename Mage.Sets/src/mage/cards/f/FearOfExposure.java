package mage.cards.f;

import mage.MageInt;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearOfExposure extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped creatures and/or lands you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public FearOfExposure(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, tap two untapped creatures and/or lands you control.
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledPermanent(2, filter)));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private FearOfExposure(final FearOfExposure card) {
        super(card);
    }

    @Override
    public FearOfExposure copy() {
        return new FearOfExposure(this);
    }
}
