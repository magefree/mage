package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEndOfTurnEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.RatCantBlockToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RedcapGutterDweller extends CardImpl {

    public RedcapGutterDweller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // When Redcap Gutter-Dweller enters the battlefield, create two 1/1 black Rat creature tokens with "This creature can't block."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new RatCantBlockToken(), 2)));

        // At the beginning of your upkeep, you may sacrifice another creature. If you do, put a +1/+1 counter on Redcap Gutter-Dweller and exile the top card of your library. You may play that card this turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)),
                        "Sacrifice another creature? If you do, put a +1/+1 counter on {this} "
                                + "and exile the top card of your library. You may play that card this turn."
                ).addEffect(new ExileTopXMayPlayUntilEndOfTurnEffect(1, false)
                        .setText("and exile the top card of your library. You may play that card this turn")),
                TargetController.YOU,
                false
        ));
    }

    private RedcapGutterDweller(final RedcapGutterDweller card) {
        super(card);
    }

    @Override
    public RedcapGutterDweller copy() {
        return new RedcapGutterDweller(this);
    }
}
