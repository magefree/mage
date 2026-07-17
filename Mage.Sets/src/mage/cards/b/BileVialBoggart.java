package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BileVialBoggart extends CardImpl {

    public BileVialBoggart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature dies, put a -1/-1 counter on up to one target creature.
        Ability ability = new DiesSourceTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private BileVialBoggart(final BileVialBoggart card) {
        super(card);
    }

    @Override
    public BileVialBoggart copy() {
        return new BileVialBoggart(this);
    }
}
