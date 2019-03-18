
package mage.cards.m;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
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
public final class MartyrsCry extends CardImpl {

    public MartyrsCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}");

        // Exile all white creatures. For each creature exiled this way, its controller draws a card.
        this.getSpellAbility().addEffect(new MartyrsCryEffect());
    }

    public MartyrsCry(final MartyrsCry card) {
        super(card);
    }

    @Override
    public MartyrsCry copy() {
        return new MartyrsCry(this);
    }
}

class MartyrsCryEffect extends OneShotEffect {

    MartyrsCryEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all white creatures. For each creature exiled this way, its controller draws a card.";
    }

    MartyrsCryEffect(final MartyrsCryEffect effect) {
        super(effect);
    }

    @Override
    public MartyrsCryEffect copy() {
        return new MartyrsCryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playerCrtCount = new HashMap<>();
        for (Iterator<Permanent> it = game.getBattlefield().getActivePermanents(source.getControllerId(), game).iterator(); it.hasNext();) {
            Permanent perm = it.next();
            if (perm != null && perm.isCreature() && perm.getColor(game).isWhite() && perm.moveToExile(null, null, source.getSourceId(), game)) {
                playerCrtCount.putIfAbsent(perm.getControllerId(), 0);
                playerCrtCount.compute(perm.getControllerId(), (p, amount) -> amount + 1);
            }
        }
        for (UUID playerId : game.getPlayerList().toList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.drawCards(playerCrtCount.getOrDefault(playerId, 0), game);
            }
        }
        return true;
    }
}
