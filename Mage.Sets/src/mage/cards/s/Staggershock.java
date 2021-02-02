
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class Staggershock extends CardImpl {

    public Staggershock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.addAbility(new ReboundAbility());
    }

    private Staggershock(final Staggershock card) {
        super(card);
    }

    @Override
    public Staggershock copy() {
        return new Staggershock(this);
    }
}
