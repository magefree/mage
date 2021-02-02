
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class Ostracize extends CardImpl {

    public Ostracize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target opponent reveals their hand. You choose a creature card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(new FilterCreatureCard("a creature card")));
    }

    private Ostracize(final Ostracize card) {
        super(card);
    }

    @Override
    public Ostracize copy() {
        return new Ostracize(this);
    }
}
