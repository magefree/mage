package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author Stravant
 */
public final class ScarabFeast extends CardImpl {
    public ScarabFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Exile up to three target cards from a single graveyard.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 3, StaticFilters.FILTER_CARD_CARDS));

        // Cycling {B}
        addAbility(new CyclingAbility(new ManaCostsImpl<>("{B}")));
    }

    private ScarabFeast(final ScarabFeast card) {
        super(card);
    }

    @Override
    public ScarabFeast copy() {
        return new ScarabFeast(this);
    }
}
