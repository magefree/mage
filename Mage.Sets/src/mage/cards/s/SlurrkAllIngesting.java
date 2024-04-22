package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlurrkAllIngesting extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(CounterType.P1P1.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SlurrkAllIngesting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Slurrk, All-Ingesting enters the battlefield with five +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(5)),
                "with five +1/+1 counters on it"
        ));

        // Whenever Slurrk or another creature you control dies, if it had a +1/+1 counter on it, put a +1/+1 counter on each creature you control that has a +1/+1 counter on it.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), filter
        ).setText("if it had a +1/+1 counter on it, put a +1/+1 counter " +
                "on each creature you control that has a +1/+1 counter on it"), false, filter));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private SlurrkAllIngesting(final SlurrkAllIngesting card) {
        super(card);
    }

    @Override
    public SlurrkAllIngesting copy() {
        return new SlurrkAllIngesting(this);
    }
}
