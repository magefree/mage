package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SiegfriedFamedSwordsman extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE, 2);
    private static final Hint hint = new ValueHint(
            "Creatures in your graveyard", new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE)
    );

    public SiegfriedFamedSwordsman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // When Siegfried enters, mill three cards. Then put X +1/+1 counters on Siegfried, where X is twice the number of creature cards in your graveyard.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), xValue)
                .setText("Then put X +1/+1 counters on {this}, where X is " +
                        "twice the number of creature cards in your graveyard"));
        this.addAbility(ability.addHint(hint));
    }

    private SiegfriedFamedSwordsman(final SiegfriedFamedSwordsman card) {
        super(card);
    }

    @Override
    public SiegfriedFamedSwordsman copy() {
        return new SiegfriedFamedSwordsman(this);
    }
}
