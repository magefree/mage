

package mage.cards.s;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Backfir3
 */
public final class StrokeOfGenius extends CardImpl {

    public StrokeOfGenius (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{2}{U}");


        // Target player draws X cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private StrokeOfGenius(final StrokeOfGenius card) {
        super(card);
    }

    @Override
    public StrokeOfGenius copy() {
        return new StrokeOfGenius(this);
    }

}