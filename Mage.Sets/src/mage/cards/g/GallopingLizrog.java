package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GallopingLizrog extends CardImpl {

    public GallopingLizrog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Galloping Lizrog enters the battlefield, you may remove any number of +1/+1 counters from among creatures you control. If you do, put twice that many +1/+1 counters on Galloping Lizrog.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GallopingLizrogEffect(), true));
    }

    private GallopingLizrog(final GallopingLizrog card) {
        super(card);
    }

    @Override
    public GallopingLizrog copy() {
        return new GallopingLizrog(this);
    }
}

class GallopingLizrogEffect extends OneShotEffect {

    GallopingLizrogEffect() {
        super(Outcome.Benefit);
        staticText = "remove any number of +1/+1 counters from among creatures you control. " +
                "If you do, put twice that many +1/+1 counters on {this}";
    }

    private GallopingLizrogEffect(final GallopingLizrogEffect effect) {
        super(effect);
    }

    @Override
    public GallopingLizrogEffect copy() {
        return new GallopingLizrogEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        RemoveVariableCountersTargetCost variableCost
                = new RemoveVariableCountersTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE, CounterType.P1P1);
        int toPay = variableCost.announceXValue(source, game);
        Cost cost = variableCost.getFixedCostsFromAnnouncedValue(toPay);
        if (!cost.pay(source, game, source, source.getControllerId(), true)) {
            return false;
        }
        return new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(2 * toPay)
        ).apply(game, source);
    }
}