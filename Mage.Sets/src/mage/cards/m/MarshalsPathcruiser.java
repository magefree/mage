package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarshalsPathcruiser extends CardImpl {

    public MarshalsPathcruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // When this Vehicle enters, search your library for a basic land card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true)
        ));

        // Exhaust -- {W}{U}{B}{R}{G}: This Vehicle becomes an artifact creature. Put two +1/+1 counters on it.
        Ability ability = new ExhaustAbility(
                new AddCardTypeSourceEffect(Duration.Custom, CardType.ARTIFACT, CardType.CREATURE),
                new ManaCostsImpl<>("{W}{U}{B}{R}{G}")
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)).setText("Put two +1/+1 counters on it"));
        this.addAbility(ability);

        // Crew 5
        this.addAbility(new CrewAbility(5));
    }

    private MarshalsPathcruiser(final MarshalsPathcruiser card) {
        super(card);
    }

    @Override
    public MarshalsPathcruiser copy() {
        return new MarshalsPathcruiser(this);
    }
}
