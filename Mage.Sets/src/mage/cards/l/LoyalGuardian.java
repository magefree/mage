package mage.cards.l;

import mage.MageInt;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
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
        this.addAbility(new BeginningOfCombatTriggeredAbility(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        )).withInterveningIf(ControlYourCommanderCondition.instance).setAbilityWord(AbilityWord.LIEUTENANT));
    }

    private LoyalGuardian(final LoyalGuardian card) {
        super(card);
    }

    @Override
    public LoyalGuardian copy() {
        return new LoyalGuardian(this);
    }
}
