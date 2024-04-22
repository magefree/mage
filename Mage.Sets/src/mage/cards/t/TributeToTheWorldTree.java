package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TributeToTheWorldTree extends CardImpl {

    public TributeToTheWorldTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}{G}{G}");

        // Whenever a creature enters the battlefield under your control, draw a card if its power is 3 or greater. Otherwise, put two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new TributeToTheWorldTreeEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE
        ));
    }

    private TributeToTheWorldTree(final TributeToTheWorldTree card) {
        super(card);
    }

    @Override
    public TributeToTheWorldTree copy() {
        return new TributeToTheWorldTree(this);
    }
}

class TributeToTheWorldTreeEffect extends OneShotEffect {

    TributeToTheWorldTreeEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card if its power is 3 or greater. Otherwise, put two +1/+1 counters on it";
    }

    private TributeToTheWorldTreeEffect(final TributeToTheWorldTreeEffect effect) {
        super(effect);
    }

    @Override
    public TributeToTheWorldTreeEffect copy() {
        return new TributeToTheWorldTreeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanentEntering = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanentEntering == null) {
            return false;
        }

        // We need to ask the game for the actualized object for the entering permanent.
        Permanent permanent = game.getPermanent(permanentEntering.getId());
        if (permanent == null) {
            return false;
        }

        int power = permanent.getPower().getValue();
        if (power < 3) {
            return permanent.addCounters(CounterType.P1P1.createInstance(2), source, game);
        }
        else {
            Player player = game.getPlayer(source.getControllerId());
            return player != null && player.drawCards(1, source, game) > 0;
        }
    }
}
