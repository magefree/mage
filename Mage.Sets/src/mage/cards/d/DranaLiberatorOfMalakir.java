package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author fireshoes
 */
public final class DranaLiberatorOfMalakir extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DranaLiberatorOfMalakir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
       addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Drana, Liberator of Malakir deals combat damage to player, put a +1/+1 counter on each attacking creature you control.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false));
    }

    private DranaLiberatorOfMalakir(final DranaLiberatorOfMalakir card) {
        super(card);
    }

    @Override
    public DranaLiberatorOfMalakir copy() {
        return new DranaLiberatorOfMalakir(this);
    }
}
