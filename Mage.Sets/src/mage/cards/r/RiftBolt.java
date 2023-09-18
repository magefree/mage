
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class RiftBolt extends CardImpl {

    public RiftBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");


        // Rift Bolt deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Suspend 1-{R}
        this.addAbility(new SuspendAbility(1, new ManaCostsImpl<>("{R}"), this));


    }

    private RiftBolt(final RiftBolt card) {
        super(card);
    }

    @Override
    public RiftBolt copy() {
        return new RiftBolt(this);
    }
}
