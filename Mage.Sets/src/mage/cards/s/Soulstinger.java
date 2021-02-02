
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author stravant
 */
public final class Soulstinger extends CardImpl {

    public Soulstinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SCORPION);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Soulstinger enters the battlefield, put two -1/-1 counter on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(2)));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // When Soulstinger dies, you may put a -1/-1 counter on target creature for each -1/-1 counter on Soulstinger.
        AddCountersTargetEffect effect
                = new AddCountersTargetEffect(
                        CounterType.M1M1.createInstance(0),
                        new CountersSourceCount(CounterType.M1M1),
                        Outcome.Detriment);
        effect.setText("you may put a -1/-1 counter on target creature for each -1/-1 counter on {this}");
        ability = new DiesSourceTriggeredAbility(effect, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Soulstinger(final Soulstinger card) {
        super(card);
    }

    @Override
    public Soulstinger copy() {
        return new Soulstinger(this);
    }
}
