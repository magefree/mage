
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.discard.DiscardHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public final class WheelAndDeal extends CardImpl {
    
    private static final FilterPlayer filter = new FilterPlayer("opponent");
    
    static {
        filter.add(TargetController.OPPONENT.getPlayerPredicate());
    }

    public WheelAndDeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Any number of target opponents each discards their hand and draws seven cards.
        Effect effect = new DiscardHandTargetEffect();
        effect.setText("Any number of target opponents each discards their hand");
        this.getSpellAbility().addTarget(new TargetPlayer(0, Integer.MAX_VALUE, false, filter));
        this.getSpellAbility().addEffect(effect);
        effect = new DrawCardTargetEffect(7);
        effect.setText("and draws seven cards");
        this.getSpellAbility().addEffect(effect);
        
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
