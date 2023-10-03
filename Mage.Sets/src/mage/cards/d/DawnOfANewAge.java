package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LePwnerer
 */
public final class DawnOfANewAge extends CardImpl {

    public DawnOfANewAge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Dawn of a New Age enters the battlefield with a hope counter on it for each creature you control.
        DynamicValue numberCounters = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED);
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.HOPE.createInstance(0), numberCounters, true),
                "with a hope counter on it for each creature you control")
        );

        // At the beginning of your end step, remove a hope counter from Dawn of a New Age. If you do, draw a card. Then if Dawn of a New Age has no hope counters on it, sacrifice it and you gain 4 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DawnOfANewAgeEffect(), TargetController.YOU, false));
    }

    private DawnOfANewAge(final DawnOfANewAge card) {
        super(card);
    }

    @Override
    public DawnOfANewAge copy() {
        return new DawnOfANewAge(this);
    }
}

class DawnOfANewAgeEffect extends OneShotEffect {

    public DawnOfANewAgeEffect() {
        super(Outcome.Sacrifice);
        staticText = "remove a hope counter from {this}. If you do, draw a card. Then if {this} has no hope counters on it, sacrifice it and you gain 4 life";
    }

    private DawnOfANewAgeEffect(final DawnOfANewAgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && controller != null) {
            int numCounters = permanent.getCounters(game).getCount(CounterType.HOPE);
            if (numCounters >= 1) {
                permanent.removeCounters(CounterType.HOPE.getName(), 1, source, game);
                controller.drawCards(1, source, game);
                numCounters -= 1;
            }
            if (numCounters == 0) {
                permanent.sacrifice(source, game);
                controller.gainLife(4, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public DawnOfANewAgeEffect copy() {
        return new DawnOfANewAgeEffect(this);
    }
}
