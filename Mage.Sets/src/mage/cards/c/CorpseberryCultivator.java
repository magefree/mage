package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.ForageTriggeredAbility;
import mage.abilities.costs.common.ForageCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CorpseberryCultivator extends CardImpl {

    public CorpseberryCultivator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/G}{B/G}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, you may forage.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(null, new ForageCost()),
                TargetController.YOU, false
        ));

        // Whenever you forage, put a +1/+1 counter on Corpseberry Cultivator.
        this.addAbility(new ForageTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())));
    }

    private CorpseberryCultivator(final CorpseberryCultivator card) {
        super(card);
    }

    @Override
    public CorpseberryCultivator copy() {
        return new CorpseberryCultivator(this);
    }
}
