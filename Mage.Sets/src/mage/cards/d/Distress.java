
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

/**
 * @author nantuko
 */
public final class Distress extends CardImpl {

    public Distress(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");


        // Target player reveals their hand. You choose a nonland card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(StaticFilters.FILTER_CARD_NON_LAND, TargetController.ANY));
    }

    private Distress(final Distress card) {
        super(card);
    }

    @Override
    public Distress copy() {
        return new Distress(this);
    }
}
