
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

/**
 *
 * @author TheElk801
 */
public final class ArcticNishoba extends CardImpl {

    public ArcticNishoba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cumulative upkeep {G} or {W}
        this.addAbility(new CumulativeUpkeepAbility(new OrCost(
                "{G} or {W}", new ManaCostsImpl<>("{G}"),
                new ManaCostsImpl<>("{W}")
        )));

        // When Arctic Nishoba dies, you gain 2 life for each age counter on it.
        Effect effect = new GainLifeEffect(new MultipliedValue(new CountersSourceCount(CounterType.AGE), 2));
        effect.setText("you gain 2 life for each age counter on it");
        this.addAbility(new DiesSourceTriggeredAbility(effect));
    }

    private ArcticNishoba(final ArcticNishoba card) {
        super(card);
    }

    @Override
    public ArcticNishoba copy() {
        return new ArcticNishoba(this);
    }
}
