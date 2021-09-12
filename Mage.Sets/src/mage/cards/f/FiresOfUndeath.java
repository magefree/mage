
package mage.cards.f;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author North
 */
public final class FiresOfUndeath extends CardImpl {

    public FiresOfUndeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");


        // Fires of Undeath deals 2 damage to any target.
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        // Flashback {5}{B}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{B}")));
    }

    private FiresOfUndeath(final FiresOfUndeath card) {
        super(card);
    }

    @Override
    public FiresOfUndeath copy() {
        return new FiresOfUndeath(this);
    }
}
