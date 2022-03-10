
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class RetreatToKazandu extends CardImpl {

    public RetreatToKazandu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // <i>Landfall</i>-Whenever a land enters the battlefield under your control, choose one - Put a +1/+1 counter on target creature; or You gain 2 life.
        LandfallAbility ability = new LandfallAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetCreaturePermanent());
        Mode mode = new Mode(new GainLifeEffect(2));
        ability.addMode(mode);
        this.addAbility(ability);
    }

    private RetreatToKazandu(final RetreatToKazandu card) {
        super(card);
    }

    @Override
    public RetreatToKazandu copy() {
        return new RetreatToKazandu(this);
    }
}
