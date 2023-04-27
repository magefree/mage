
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class MurasaRanger extends CardImpl {

    public MurasaRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, you may pay {3}{G}. IF you do, put two +1/+1 counters on Murasa Ranger.
        this.addAbility(new LandfallAbility(new DoIfCostPaid(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)), new ManaCostsImpl<>("{3}{G}")), false));
    }

    private MurasaRanger(final MurasaRanger card) {
        super(card);
    }

    @Override
    public MurasaRanger copy() {
        return new MurasaRanger(this);
    }
}
