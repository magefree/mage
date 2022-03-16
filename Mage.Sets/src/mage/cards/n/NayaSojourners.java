package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleOrDiesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class NayaSojourners extends CardImpl {

    public NayaSojourners(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When you cycle Naya Sojourners or it dies, you may put a +1/+1 counter on target creature.
        Ability ability = new CycleOrDiesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), true
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Cycling {2}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{G}")));
    }

    private NayaSojourners(final NayaSojourners card) {
        super(card);
    }

    @Override
    public NayaSojourners copy() {
        return new NayaSojourners(this);
    }
}
