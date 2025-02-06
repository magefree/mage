package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OozePatrol extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_ARTIFACT_OR_CREATURE);
    private static final Hint hint = new ValueHint("Artifacts and creatures in your graveyard", xValue);

    public OozePatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, mill two cards, then put a +1/+1 counter on this creature for each artifact and/or creature card in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(0), xValue)
                .setText(", then put a +1/+1 counter on this creature for each artifact and/or creature card in your graveyard"));
        this.addAbility(ability.addHint(hint));
    }

    private OozePatrol(final OozePatrol card) {
        super(card);
    }

    @Override
    public OozePatrol copy() {
        return new OozePatrol(this);
    }
}
