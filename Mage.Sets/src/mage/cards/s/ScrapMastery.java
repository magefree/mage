
package mage.cards.s;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ScrapMastery extends CardImpl {

    public ScrapMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");

        // Each player exiles all artifact cards from their graveyard, then sacrifices all artifacts they control, then puts all cards they exiled this way onto the battlefield.
        this.getSpellAbility().addEffect(new ScrapMasteryEffect());
    }

    private ScrapMastery(final ScrapMastery card) {
        super(card);
    }

    @Override
    public ScrapMastery copy() {
        return new ScrapMastery(this);
    }
}

class ScrapMasteryEffect extends OneShotEffect {

    public ScrapMasteryEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Each player exiles all artifact cards from their graveyard, then sacrifices all artifacts they control, then puts all cards they exiled this way onto the battlefield";
    }

    private ScrapMasteryEffect(final ScrapMasteryEffect effect) {
        super(effect);
    }

    @Override
    public ScrapMasteryEffect copy() {
        return new ScrapMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Map<UUID, Set<Card>> exiledCards = new HashMap<>();
            // exile artifacts from graveyard
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cards = player.getGraveyard().getCards(new FilterArtifactCard(), game);
                    controller.moveCards(cards, Zone.EXILED, source, game);
                    exiledCards.put(player.getId(), cards);
                }
            }
            // sacrifice all artifacts
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), playerId, game)) {
                        permanent.sacrifice(source, game);
                    }
                }
            }
            // puts all cards they exiled this way onto the battlefield
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.moveCards(exiledCards.get(playerId), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
