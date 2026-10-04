package mage.cards.b;

import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BeastmasterAscension extends CardImpl {

    public BeastmasterAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // Whenever a creature you control attacks, put a quest counter on Beastmaster Ascension.
        this.addAbility(new AttacksCreatureYouControlTriggeredAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));

        // As long as Beastmaster Ascension has seven or more quest counters on it, creatures you control get +5/+5.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(5, 5, Duration.WhileOnBattlefield),
                new SourceHasCounterCondition(CounterType.QUEST, 7),
                "As long as {this} has seven or more quest counters on it, creatures you control get +5/+5"
        )));
    }

    private BeastmasterAscension(final BeastmasterAscension card) {
        super(card);
    }

    @Override
    public BeastmasterAscension copy() {
        return new BeastmasterAscension(this);
    }

}
