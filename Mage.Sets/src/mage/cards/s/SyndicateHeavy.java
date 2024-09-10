package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.ExtortAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SyndicateHeavy extends CardImpl {

    public SyndicateHeavy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/B}{W/B}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Extort
        this.addAbility(new ExtortAbility());

        // At the beginning of each end step, if you gained 4 or more life this turn, investigate.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new InvestigateEffect(), TargetController.ANY,
                new YouGainedLifeCondition(ComparisonType.MORE_THAN, 3), false
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private SyndicateHeavy(final SyndicateHeavy card) {
        super(card);
    }

    @Override
    public SyndicateHeavy copy() {
        return new SyndicateHeavy(this);
    }
}
