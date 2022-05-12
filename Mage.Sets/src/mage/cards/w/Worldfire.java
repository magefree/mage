
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class Worldfire extends CardImpl {

    public Worldfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}{R}{R}");

        // Exile all permanents. Exile all cards from all hands and graveyards. Each player's life total becomes 1.
        this.getSpellAbility().addEffect(new WorldfireEffect());
    }

    private Worldfire(final Worldfire card) {
        super(card);
    }

    @Override
    public Worldfire copy() {
        return new Worldfire(this);
    }
}

class WorldfireEffect extends OneShotEffect {

    private static FilterPermanent filter = new FilterPermanent();

    public WorldfireEffect() {
        super(Outcome.Detriment);
        staticText = "Exile all permanents. Exile all cards from all hands and graveyards. Each player's life total becomes 1";
    }

    public WorldfireEffect(final WorldfireEffect effect) {
        super(effect);
    }

    @Override
    public WorldfireEffect copy() {
        return new WorldfireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            permanent.moveToExile(null, "", source, game);
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getHand().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source, game);
                    }
                }
                for (UUID cid : player.getGraveyard().copy()) {
                    Card c = game.getCard(cid);
                    if (c != null) {
                        c.moveToExile(null, null, source, game);
                    }
                }
                player.setLife(1, game, source);
            }
        }
        return true;
    }
}
