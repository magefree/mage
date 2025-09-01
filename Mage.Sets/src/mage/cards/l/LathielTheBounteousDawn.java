package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LathielTheBounteousDawn extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition();

    public LathielTheBounteousDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of each end step, if you gained life this turn, distribute up to that many +1/+1 counters among any number of other target creatures.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new DistributeCountersEffect()
                .setText("distribute up to that many +1/+1 counters " +
                        "among any number of other target creatures"),
                false
        ).withInterveningIf(condition);
        ability.addTarget(new TargetCreaturePermanentAmount(
                ControllerGainedLifeCount.instance,
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        ));
        this.addAbility(ability.addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private LathielTheBounteousDawn(final LathielTheBounteousDawn card) {
        super(card);
    }

    @Override
    public LathielTheBounteousDawn copy() {
        return new LathielTheBounteousDawn(this);
    }
}
