package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DescentIntoAvernus extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.DESCENT);

    public DescentIntoAvernus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // At the beginning of your upkeep, put two descent counters on Descent into Avernus. Then each player creates X Treasure tokens and Descent into Avernus deals X damage to each player, where X is the number of descent counters on Descent into Avernus.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new AddCountersSourceEffect(CounterType.DESCENT.createInstance(2)),
                TargetController.YOU, false
        );
        ability.addEffect(new CreateTokenAllEffect(
                new TreasureToken(), xValue, TargetController.EACH_PLAYER
        ).setText("then each player creates X Treasure tokens"));
        ability.addEffect(new DamagePlayersEffect(
                Outcome.Damage, xValue, TargetController.ANY
        ).setText("and {this} deals X damage to each player, where X is the number of descent counters on {this}"));
        this.addAbility(ability);
    }

    private DescentIntoAvernus(final DescentIntoAvernus card) {
        super(card);
    }

    @Override
    public DescentIntoAvernus copy() {
        return new DescentIntoAvernus(this);
    }
}
