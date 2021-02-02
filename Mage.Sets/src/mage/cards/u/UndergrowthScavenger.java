package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CardsInAllGraveyardsCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class UndergrowthScavenger extends CardImpl {

    public UndergrowthScavenger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Undergrowth Scavenger enters the battlefield with a number of +1/+1 counters on it equal to the number of creature cards in all graveyards.
        Effect effect = new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), new CardsInAllGraveyardsCount(StaticFilters.FILTER_CARD_CREATURE), true);
        effect.setText("with a number of +1/+1 counters on it equal to the number of creature cards in all graveyards");
        this.addAbility(new EntersBattlefieldAbility(effect));
    }

    private UndergrowthScavenger(final UndergrowthScavenger card) {
        super(card);
    }

    @Override
    public UndergrowthScavenger copy() {
        return new UndergrowthScavenger(this);
    }
}
