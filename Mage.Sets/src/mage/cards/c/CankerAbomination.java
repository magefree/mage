
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class CankerAbomination extends CardImpl {

    public CankerAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B/G}{B/G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // As Canker Abomination enters the battlefield, choose an opponent. Canker Abomination enters the battlefield with a -1/-1 counter on it for each creature that player controls.
        this.addAbility(new AsEntersBattlefieldAbility(new CankerAbominationEffect()));

    }

    private CankerAbomination(final CankerAbomination card) {
        super(card);
    }

    @Override
    public CankerAbomination copy() {
        return new CankerAbomination(this);
    }
}

class CankerAbominationEffect extends OneShotEffect {

    public CankerAbominationEffect() {
        super(Outcome.Neutral);
        this.staticText = "choose an opponent. {this} enters the battlefield with a -1/-1 counter on it for each creature that player controls";
    }

    public CankerAbominationEffect(final CankerAbominationEffect effect) {
        super(effect);
    }

    @Override
    public CankerAbominationEffect copy() {
        return new CankerAbominationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent cankerAbomination = game.getPermanentEntering(source.getSourceId());
        if (controller != null && cankerAbomination != null) {
            Target target = new TargetOpponent();
            target.setNotTarget(true);
            controller.choose(outcome, target, source, game);
            Player opponent = game.getPlayer(target.getFirstTarget());
            if (opponent != null) {
                game.informPlayers(cankerAbomination.getName() + ": " + controller.getLogName() + " has chosen " + opponent.getLogName());
                int amount = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game).size();
                if (amount > 0) {
                    cankerAbomination.addCounters(CounterType.M1M1.createInstance(amount), source.getControllerId(), source, game);
                }
                return true;
            }
        }
        return false;
    }
}
