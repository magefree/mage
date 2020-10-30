package mage.cards.d;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class DauntlessSurvivor extends CardImpl {

    public DauntlessSurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Dauntless Survivor enters the battlefield, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DauntlessSurvivor(final DauntlessSurvivor card) {
        super(card);
    }

    @Override
    public DauntlessSurvivor copy() {
        return new DauntlessSurvivor(this);
    }
}
