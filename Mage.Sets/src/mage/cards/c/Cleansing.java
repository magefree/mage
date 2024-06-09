
package mage.cards.c;

import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public final class Cleansing extends CardImpl {

    public Cleansing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}{W}");

        // For each land, destroy that land unless any player pays 1 life.
        this.getSpellAbility().addEffect(new CleansingEffect());
    }

    private Cleansing(final Cleansing card) {
        super(card);
    }

    @Override
    public Cleansing copy() {
        return new Cleansing(this);
    }
}

class CleansingEffect extends OneShotEffect {

    CleansingEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "For each land, destroy that land unless any player pays 1 life";
    }

    private CleansingEffect(final CleansingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Iterator<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game).iterator();
        while (permanents.hasNext()) {
            boolean paidLife = false;
            Permanent p = permanents.next();
            if (p.isLand(game)) {
                game.informPlayers("Any player may pay 1 life to prevent the destruction of " + p.getLogName() + " controlled by " + game.getPlayer(p.getControllerId()).getLogName() + ".");
                PayLifeCost cost = new PayLifeCost(1);
                for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                    Player player = game.getPlayer(playerId);
                    if(player != null) {
                        cost.clearPaid();
                        if (cost.canPay(source, source, player.getId(), game)
                                && player.chooseUse(outcome, "Pay 1 life to prevent this?", source, game)) {
                            if (cost.pay(source, game, source, player.getId(), false, null)) {
                                game.informPlayers(player.getLogName() + " pays 1 life to prevent the destruction of " + p.getLogName());
                                paidLife = true;
                            }
                        }
                    }
                }
                if (!paidLife) {
                    p.destroy(source, game, false);
                }
            }
        }

        return true;
    }

    @Override
    public CleansingEffect copy() {
        return new CleansingEffect(this);
    }
}
