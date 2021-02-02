
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesMonstrousTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.AbilitiesCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Styxo
 */
public final class WeequayBeastmaster extends CardImpl {

    public WeequayBeastmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.WEEQUAY);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Monstrosity abilities you activate cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbilitiesCostReductionControllerEffect(MonstrosityAbility.class, "Monstrosity")));

        // Whenever a creature you control becomes monstrous, put an additional +1/+1 counter on that creature.
        this.addAbility(new BecomesMonstrousTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance())));
    }

    private WeequayBeastmaster(final WeequayBeastmaster card) {
        super(card);
    }

    @Override
    public WeequayBeastmaster copy() {
        return new WeequayBeastmaster(this);
    }
}
