package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegolasGreenleaf extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public LegolasGreenleaf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Legolas Greenleaf can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Whenever another legendary creature enters the battlefield under your control, put a +1/+1 counter on Legolas Greenleaf.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));

        // Whenever Legolas Greenleaf deals combat damage to a player, draw a card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false
        ));
    }

    private LegolasGreenleaf(final LegolasGreenleaf card) {
        super(card);
    }

    @Override
    public LegolasGreenleaf copy() {
        return new LegolasGreenleaf(this);
    }
}
