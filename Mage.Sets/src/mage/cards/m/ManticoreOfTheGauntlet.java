
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponentOrPlaneswalker;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author stravant
 */
public final class ManticoreOfTheGauntlet extends CardImpl {

    public ManticoreOfTheGauntlet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.MANTICORE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When Manticore of the Gauntlet enters the battlefield, put a -1/-1 counter on target creature you control. Manticore of the Gauntlet deals 3 damage to target opponent.
        Effect counters = new AddCountersTargetEffect(CounterType.M1M1.createInstance());
        counters.setText("put a -1/-1 counter on target creature you control");
        counters.setTargetPointer(new FirstTargetPointer());

        Effect damage = new DamageTargetEffect(StaticValue.get(3), true, "", true);
        damage.setText("{this} deals 3 damage to target opponent or planeswalker.");
        damage.setTargetPointer(new SecondTargetPointer());

        Ability ability = new EntersBattlefieldTriggeredAbility(counters);
        ability.addEffect(damage);

        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetOpponentOrPlaneswalker());

        this.addAbility(ability);
    }

    private ManticoreOfTheGauntlet(final ManticoreOfTheGauntlet card) {
        super(card);
    }

    @Override
    public ManticoreOfTheGauntlet copy() {
        return new ManticoreOfTheGauntlet(this);
    }
}
