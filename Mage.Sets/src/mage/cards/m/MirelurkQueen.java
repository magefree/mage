package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.OneOrMoreMilledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirelurkQueen extends CardImpl {

    public MirelurkQueen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Mirelurk Queen enters the battlefield, target player gets two rad counters.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.RAD.createInstance(2)));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever one or more nonland cards are milled, draw a card, then put a +1/+1 counter on Mirelurk Queen. This ability triggers only once each turn.
        TriggeredAbility triggeredAbility = new OneOrMoreMilledTriggeredAbility(
                StaticFilters.FILTER_CARDS_NON_LAND,
                new DrawCardSourceControllerEffect(1)
        );
        triggeredAbility.addEffect(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                        .concatBy(", then")
        );
        triggeredAbility.setTriggersOnceEachTurn(true);
        this.addAbility(triggeredAbility);
    }

    private MirelurkQueen(final MirelurkQueen card) {
        super(card);
    }

    @Override
    public MirelurkQueen copy() {
        return new MirelurkQueen(this);
    }
}