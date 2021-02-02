
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Inspiration extends CardImpl {

    public Inspiration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Target player draws two cards.
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private Inspiration(final Inspiration card) {
        super(card);
    }

    @Override
    public Inspiration copy() {
        return new Inspiration(this);
    }
}
