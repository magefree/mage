
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

/**
 *
 * @author Plopman
 */
public final class AncestralVision extends CardImpl {

    public AncestralVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"");

        this.color.setBlue(true);
        
        // Suspend 4-{U}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{U}"), this));
        // Target player draws three cards.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3));
    }

    private AncestralVision(final AncestralVision card) {
        super(card);
    }

    @Override
    public AncestralVision copy() {
        return new AncestralVision(this);
    }
}
