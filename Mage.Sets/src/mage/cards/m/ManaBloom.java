
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ManaBloom extends CardImpl {

    static final String rule = "with X charge counters on it";

    public ManaBloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{G}");

        // Mana Bloom enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // Remove a charge counter from Mana Bloom: Add one mana of any color. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(),
                new RemoveCountersSourceCost(CounterType.CHARGE.createInstance())
        );
        this.addAbility(ability);

        // At the beginning of your upkeep, if Mana Bloom has no charge counters on it, return it to its owner's hand.
        TriggeredAbility triggeredAbility = new BeginningOfUpkeepTriggeredAbility(new ReturnToHandSourceEffect(true), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggeredAbility, new SourceHasCounterCondition(CounterType.CHARGE, 0, 0), "At the beginning of your upkeep, if Mana Bloom has no charge counters on it, return it to its owner's hand."));

    }

    private ManaBloom(final ManaBloom card) {
        super(card);
    }

    @Override
    public ManaBloom copy() {
        return new ManaBloom(this);
    }
}
