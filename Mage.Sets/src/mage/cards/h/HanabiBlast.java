

package mage.cards.h;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnToHandSpellEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class HanabiBlast extends CardImpl {

    public HanabiBlast (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");

        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(ReturnToHandSpellEffect.getInstance());
        this.getSpellAbility().addEffect(new DiscardControllerEffect(1, true).concatBy(", then"));
    }

    public HanabiBlast (final HanabiBlast card) {
        super(card);
    }

    @Override
    public HanabiBlast copy() {
        return new HanabiBlast(this);
    }

}
