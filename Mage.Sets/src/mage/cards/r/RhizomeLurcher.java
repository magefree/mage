package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author TheElk801
 */
public final class RhizomeLurcher extends CardImpl {

    public RhizomeLurcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.FUNGUS);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Undergrowth — Rhizome Lurcher enters the battlefield with a number of +1/+1 counters on it equal to the number of creature cards in your graveyard.
        Ability ability = new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(0),
                        new CardsInControllerGraveyardCount(
                                StaticFilters.FILTER_CARD_CREATURE
                        ), true
                ), null, "<i>Undergrowth</i> &mdash; {this} enters the battlefield with a number of +1/+1 counters on it equal to the number of creature cards in your graveyard.",
                null
        );
        this.addAbility(ability);
    }

    public RhizomeLurcher(final RhizomeLurcher card) {
        super(card);
    }

    @Override
    public RhizomeLurcher copy() {
        return new RhizomeLurcher(this);
    }
}
