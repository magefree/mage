package mage.cards.m;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ManaBloom extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.CHARGE, ComparisonType.EQUAL_TO, 0);

    public ManaBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{G}");

        // Mana Bloom enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // Remove a charge counter from Mana Bloom: Add one mana of any color. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance())
        ));

        // At the beginning of your upkeep, if Mana Bloom has no charge counters on it, return it to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new ReturnToHandSourceEffect(true).setText("return it to its owner's hand")
        ).withInterveningIf(condition));
    }

    private ManaBloom(final ManaBloom card) {
        super(card);
    }

    @Override
    public ManaBloom copy() {
        return new ManaBloom(this);
    }
}
