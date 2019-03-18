
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LightningBolt extends CardImpl {

    public LightningBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
    }

    public LightningBolt(final LightningBolt card) {
        super(card);
    }

    @Override
    public LightningBolt copy() {
        return new LightningBolt(this);
    }

}
