package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.*;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author lunelis
 */
public final class RescueRetriever extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.SOLDIER, "other Soldier you control");
    private static final FilterAttackingCreature filterAttacking = new FilterAttackingCreature("attacking Soldiers you control");

    static {
        filter.add(AnotherPredicate.instance);
        filterAttacking.add(SubType.SOLDIER.getPredicate());
        filterAttacking.add(TargetController.YOU.getControllerPredicate());
        filterAttacking.add(AnotherPredicate.instance);
    }

    public RescueRetriever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Rescue Retriever enters the battlefield, put a +1/+1 counter on each other Soldier you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false));

        // Prevent all damage that would be dealt to other attacking Soldiers you control.
        this.addAbility(new SimpleStaticAbility(new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, filterAttacking)));
    }

    private RescueRetriever(final RescueRetriever card) {
        super(card);
    }

    @Override
    public RescueRetriever copy() {
        return new RescueRetriever(this);
    }
}
