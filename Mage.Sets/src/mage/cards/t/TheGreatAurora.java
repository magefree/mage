
package mage.cards.t;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class TheGreatAurora extends CardImpl {

    public TheGreatAurora(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{6}{G}{G}{G}");

        // Each player shuffles all cards from their hand and all permanents they own into their library, then draws that many cards. Each player may put any number of land cards from their hand onto the battlefield. Exile The Great Aurora.
        this.getSpellAbility().addEffect(new TheGreatAuroraEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private TheGreatAurora(final TheGreatAurora card) {
        super(card);
    }

    @Override
    public TheGreatAurora copy() {
        return new TheGreatAurora(this);
    }
}

class TheGreatAuroraEffect extends OneShotEffect {

    public TheGreatAuroraEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player shuffles all cards from their hand and all permanents they own into their library, then draws that many cards. Each player may put any number of land cards from their hand onto the battlefield";
    }

    private TheGreatAuroraEffect(final TheGreatAuroraEffect effect) {
        super(effect);
    }

    @Override
    public TheGreatAuroraEffect copy() {
        return new TheGreatAuroraEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {

            Map<UUID, List<Permanent>> permanentsOwned = new HashMap<>();
            Collection<Permanent> permanents = game.getBattlefield().getActivePermanents(source.getControllerId(), game);
            for (Permanent permanent : permanents) {
                List<Permanent> list = permanentsOwned.computeIfAbsent(permanent.getOwnerId(), k -> new ArrayList<>());
                list.add(permanent);
            }

            // shuffle permanents and hand cards into owner's library
            Map<UUID, Integer> permanentsCount = new HashMap<>();

            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int handCards = player.getHand().size();
                    player.moveCards(player.getHand(), Zone.LIBRARY, source, game);
                    List<Permanent> list = permanentsOwned.remove(player.getId());
                    permanentsCount.put(playerId, handCards + (list != null ? list.size() : 0));
                    if (list != null) {
                        for (Permanent permanent : list) {
                            player.moveCardToLibraryWithInfo(permanent, source, game, Zone.BATTLEFIELD, true, true);
                        }
                        player.shuffleLibrary(source, game);
                    }
                }
            }

            game.getState().processAction(game); // so effects from creatures that were on the battlefield won't trigger from draw or put into play

            // Draw cards
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int count = permanentsCount.get(playerId);
                    if (count > 0) {
                        player.drawCards(count, source, game);
                    }
                }
            }

            // put lands onto the battlefield
            Cards toBattlefield = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, new FilterLandCard("put any number of land cards from your hand onto the battlefield"));
                    player.chooseTarget(Outcome.PutLandInPlay, player.getHand(), target, source, game);
                    toBattlefield.addAll(target.getTargets());
                }
            }
            return controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
        }
        return false;
    }
}
