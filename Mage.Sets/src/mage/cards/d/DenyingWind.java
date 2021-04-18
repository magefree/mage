
package mage.cards.d;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class DenyingWind extends CardImpl {

    public DenyingWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{U}{U}");

        // Search target player's library for up to seven cards and exile them. Then that player shuffles their library.
        getSpellAbility().addEffect(new DenyingWindEffect());
        getSpellAbility().addTarget(new TargetPlayer());
    }

    private DenyingWind(final DenyingWind card) {
        super(card);
    }

    @Override
    public DenyingWind copy() {
        return new DenyingWind(this);
    }
}

class DenyingWindEffect extends OneShotEffect {

    public DenyingWindEffect() {
        super(Outcome.Neutral);
        staticText = "search target player's library for up to seven cards and exile them. Then that player shuffles";
    }

    public DenyingWindEffect(final DenyingWindEffect effect) {
        super(effect);
    }

    @Override
    public DenyingWindEffect copy() {
        return new DenyingWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 7, new FilterCard("cards from player's library to exile"));
            if (controller.searchLibrary(target, source, game, player.getId())) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = player.getLibrary().remove(targetId, game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, null, source, game, Zone.LIBRARY, true);
                    }
                }
            }
            player.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
