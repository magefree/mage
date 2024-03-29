package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PlotAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RailwayBrawler extends CardImpl {

    public RailwayBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever another creature enters the battlefield under your control, put X +1/+1 counters on it, where X is its power.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD,
                new RailwayBrawlerEffect(),
                StaticFilters.FILTER_ANOTHER_CREATURE,
                false,
                SetTargetPointer.PERMANENT
        ));

        // Plot {3}{G}
        this.addAbility(new PlotAbility("{3}{G}"));
    }

    private RailwayBrawler(final RailwayBrawler card) {
        super(card);
    }

    @Override
    public RailwayBrawler copy() {
        return new RailwayBrawler(this);
    }
}

class RailwayBrawlerEffect extends OneShotEffect {

    RailwayBrawlerEffect() {
        super(Outcome.Benefit);
        this.staticText = "put X +1/+1 counters on it, where X is its power";
    }

    private RailwayBrawlerEffect(final RailwayBrawlerEffect effect) {
        super(effect);
    }

    @Override
    public RailwayBrawlerEffect copy() {
        return new RailwayBrawlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        int pow = creature.getPower().getValue();
        if (pow <= 0) {
            return false;
        }
        return new AddCountersTargetEffect(CounterType.P1P1.createInstance(pow))
                .setTargetPointer(getTargetPointer())
                .apply(game, source);
    }
}
