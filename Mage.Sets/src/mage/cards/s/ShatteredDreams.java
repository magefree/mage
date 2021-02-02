
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public final class ShatteredDreams extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card from it");
    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }
    
    public ShatteredDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}");


        // Target opponent reveals their hand. You choose an artifact card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
    }

    private ShatteredDreams(final ShatteredDreams card) {
        super(card);
    }

    @Override
    public ShatteredDreams copy() {
        return new ShatteredDreams(this);
    }
}
