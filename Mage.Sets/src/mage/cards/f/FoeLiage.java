package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FoeLiage extends CardImpl {

    public FoeLiage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a land enters during your turn, put a +1/+1 counter on this creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_LAND
        ).withTriggerCondition(MyTurnCondition.instance));
    }

    private FoeLiage(final FoeLiage card) {
        super(card);
    }

    @Override
    public FoeLiage copy() {
        return new FoeLiage(this);
    }
}
