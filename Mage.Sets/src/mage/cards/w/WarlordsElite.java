package mage.cards.w;

import mage.MageInt;
import mage.abilities.costs.common.TapTargetCost;
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
public final class WarlordsElite extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("untapped artifacts, creatures, and/or lands you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public WarlordsElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As an additional cost to cast this spell, tap two untapped artifacts, creatures, and/or lands you control.
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledPermanent(2, filter)));
    }

    private WarlordsElite(final WarlordsElite card) {
        super(card);
    }

    @Override
    public WarlordsElite copy() {
        return new WarlordsElite(this);
    }
}
