package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class OsaiVultures extends CardImpl {

    public OsaiVultures(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if a creature died this turn, put a carrion counter on Osai Vultures.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new AddCountersSourceEffect(CounterType.CARRION.createInstance()), false
        ).withInterveningIf(MorbidCondition.instance).addHint(MorbidHint.instance));

        // Remove two carrion counters from Osai Vultures: Osai Vultures gets +1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn),
                new RemoveCountersSourceCost(CounterType.CARRION.createInstance(2))
        ));
    }

    private OsaiVultures(final OsaiVultures card) {
        super(card);
    }

    @Override
    public OsaiVultures copy() {
        return new OsaiVultures(this);
    }
}
