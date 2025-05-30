
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 *
 * @author LoneFox
 */
public final class KhabalGhoul extends CardImpl {

    public KhabalGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of each end step, put a +1/+1 counter on Khabal Ghoul for each creature that died this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.ANY, new AddCountersSourceEffect(CounterType.P1P1.createInstance(),
            CreaturesDiedThisTurnCount.instance, true), false).addHint(CreaturesDiedThisTurnHint.instance));
    }

    private KhabalGhoul(final KhabalGhoul card) {
        super(card);
    }

    @Override
    public KhabalGhoul copy() {
        return new KhabalGhoul(this);
    }
}
