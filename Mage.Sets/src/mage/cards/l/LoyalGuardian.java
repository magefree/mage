package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CommanderInPlayCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class LoyalGuardian extends CardImpl {

    public LoyalGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.RHINO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lieutenant — At the beginning of combat on your turn, if you control your commander, put a +1/+1 counter on each creature you control.
        this.addAbility(new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new AddCountersAllEffect(
                                CounterType.P1P1.createInstance(),
                                StaticFilters.FILTER_CONTROLLED_CREATURE
                        ), TargetController.YOU, false
                ), CommanderInPlayCondition.instance,
                "<i>Lieutenant</i> &mdash; At the beginning of combat "
                + "on your turn, if you control your commander, "
                + "put a +1/+1 counter on each creature you control."
        ));
    }

    private LoyalGuardian(final LoyalGuardian card) {
        super(card);
    }

    @Override
    public LoyalGuardian copy() {
        return new LoyalGuardian(this);
    }
}
