
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author stravant
 */
public final class SacredExcavation extends CardImpl {

    private static final FilterCard cardsWithCycling = new FilterCard("cards with cycling from your graveyard");

    static {
        cardsWithCycling.add(new AbilityPredicate(CyclingAbility.class));
    }

    public SacredExcavation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Return up to two target cards with cycling from your graveyard to your hand.
        getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 2, cardsWithCycling));
    }

    private SacredExcavation(final SacredExcavation card) {
        super(card);
    }

    @Override
    public SacredExcavation copy() {
        return new SacredExcavation(this);
    }
}
