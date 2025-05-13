package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CyanVengefulSamurai extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE, 1);
    private static final Hint hint = new ValueHint("Creatures in your graveyard", xValue);

    public CyanVengefulSamurai(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This spell costs {1} less to cast for each creature card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue)
        ).addHint(hint));

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever one or more creature cards leave your graveyard, put a +1/+1 counter on Cyan.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_CARD_CREATURES
        ));
    }

    private CyanVengefulSamurai(final CyanVengefulSamurai card) {
        super(card);
    }

    @Override
    public CyanVengefulSamurai copy() {
        return new CyanVengefulSamurai(this);
    }
}
