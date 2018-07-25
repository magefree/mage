package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.PsionicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author NinthWorld
 */
public final class ChronoBoost extends CardImpl {

    public ChronoBoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");
        

        // Psionic 2
        this.addAbility(new PsionicAbility(2));

        // At the beginning of your upkeep, remove a psi counter from Chrono Boost. If you do, draw a card. Then if there are no psi counters on Chrono Boost, sacrifice it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ChronoBoostEffect(), TargetController.YOU, false));
    }

    public ChronoBoost(final ChronoBoost card) {
        super(card);
    }

    @Override
    public ChronoBoost copy() {
        return new ChronoBoost(this);
    }
}

class ChronoBoostEffect extends OneShotEffect {

    public ChronoBoostEffect() {
        super(Outcome.DrawCard);
        this.staticText = "remove a psi counter from {this}. If you do, draw a card. Then if there are no psi counters on Chrono Boost, sacrifice it";
    }

    public ChronoBoostEffect(final ChronoBoostEffect effect) {
        super(effect);
    }

    @Override
    public ChronoBoostEffect copy() {
        return new ChronoBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if(sourcePermanent != null) {
            // Remove a psi counter
            sourcePermanent.removeCounters(CounterType.PSI.createInstance(1), game);

            // Draw a card
            Player sourceController = game.getPlayer(sourcePermanent.getControllerId());
            sourceController.drawCards(1, game);

            // If there are no psi counters
            if(!sourcePermanent.getCounters(game).containsKey(CounterType.PSI)) {
                // Sacrifice it
                sourcePermanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}