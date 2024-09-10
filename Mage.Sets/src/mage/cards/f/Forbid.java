
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author dustinconrad
 */
public final class Forbid extends CardImpl {

    public Forbid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}{U}");


        // Buyback-Discard two cards.
        this.addAbility(new BuybackAbility(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS))));
        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Forbid(final Forbid card) {
        super(card);
    }

    @Override
    public Forbid copy() {
        return new Forbid(this);
    }
}
