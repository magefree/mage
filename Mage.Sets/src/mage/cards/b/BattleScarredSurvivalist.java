package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BattleScarredSurvivalist extends CardImpl {

    public BattleScarredSurvivalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever another creature dies, put a +1/+1 counter on this creature. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
            false, true
        ).setTriggersLimitEachTurn(1));
    }

    private BattleScarredSurvivalist(final BattleScarredSurvivalist card) {
        super(card);
    }

    @Override
    public BattleScarredSurvivalist copy() {
        return new BattleScarredSurvivalist(this);
    }
}
