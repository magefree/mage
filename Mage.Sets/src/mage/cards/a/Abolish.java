package mage.cards.a;

import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class Abolish extends CardImpl {

    private static final FilterCard filterCost = new FilterCard("Plains card");

    static {
        filterCost.add(SubType.PLAINS.getPredicate());
    }

    public Abolish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");

        // You may discard a Plains card rather than pay Abolish's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new DiscardTargetCost(new TargetCardInHand(filterCost))));

        // Destroy target artifact or enchantment.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
    }

    private Abolish(final Abolish card) {
        super(card);
    }

    @Override
    public Abolish copy() {
        return new Abolish(this);
    }
}