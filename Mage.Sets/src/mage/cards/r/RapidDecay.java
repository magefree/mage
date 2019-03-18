
package mage.cards.r;

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
 *
 * @author fireshoes
 */
public final class RapidDecay extends CardImpl {

    public RapidDecay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Exile up to three target cards from a single graveyard.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 3, new FilterCard("cards from a single graveyard")));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    public RapidDecay(final RapidDecay card) {
        super(card);
    }

    @Override
    public RapidDecay copy() {
        return new RapidDecay(this);
    }
}
