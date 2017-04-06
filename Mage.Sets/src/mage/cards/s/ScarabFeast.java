package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author Stravant
 */
public class ScarabFeast extends CardImpl {
    public ScarabFeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");

        // Exile up to three target cards from a single graveyard.
        getSpellAbility().addEffect(new ExileTargetEffect());
        getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 3, new FilterCard("cards")));

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
