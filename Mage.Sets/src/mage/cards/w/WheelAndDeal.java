package mage.cards.w;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class WheelAndDeal extends CardImpl {

    public WheelAndDeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Any number of target opponents each discards their hand and draws seven cards.
        this.getSpellAbility().addTarget(new TargetOpponent(0, Integer.MAX_VALUE, false));
        this.getSpellAbility().addEffect(new DiscardHandTargetEffect().setText("Any number of target opponents each discard their hands"));
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(7).setText(", then draw seven cards"));
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private WheelAndDeal(final WheelAndDeal card) {
        super(card);
    }

    @Override
    public WheelAndDeal copy() {
        return new WheelAndDeal(this);
    }
}
