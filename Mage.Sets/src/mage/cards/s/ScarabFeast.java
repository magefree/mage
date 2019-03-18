package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 * @author Stravant
 */
public final class ScarabFeast extends CardImpl {
    public ScarabFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Exile up to three target cards from a single graveyard.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 3, new FilterCard("cards from a single graveyard")));

        // Cycling {B}
        addAbility(new CyclingAbility(new ManaCostsImpl("{B}")));
    }

    public ScarabFeast(final ScarabFeast card) {
        super(card);
    }

    @Override
    public ScarabFeast copy() {
        return new ScarabFeast(this);
    }
}
