
package mage.cards.g;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class Gerrymandering extends CardImpl {

    public Gerrymandering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Exile all lands. Give each player a number of those cards chosen at random equal to the number of those cards the player controlled. Each player returns those cards to the battlefield under their control.
        this.getSpellAbility().addEffect(new GerrymanderingEffect());
    }

    private Gerrymandering(final Gerrymandering card) {
        super(card);
    }

    @Override
    public Gerrymandering copy() {
        return new Gerrymandering(this);
    }
}

class GerrymanderingEffect extends OneShotEffect {

    GerrymanderingEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile all lands. Give each player a number of those cards chosen at random equal to the number of those cards the player controlled. Each player returns those cards to the battlefield under their control";
    }

    GerrymanderingEffect(final GerrymanderingEffect effect) {
        super(effect);
    }

    @Override
    public GerrymanderingEffect copy() {
        return new GerrymanderingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> playerLandCount = new HashMap<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Exile all lands.
            Cards exiledCards = new CardsImpl();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LANDS, source.getControllerId(), source, game)) {
                exiledCards.add(permanent);
                playerLandCount.putIfAbsent(permanent.getControllerId(), 0);
                playerLandCount.put(permanent.getControllerId(), playerLandCount.get(permanent.getControllerId()) + 1);
                controller.moveCardsToExile(permanent, source, game, true, CardUtil.getCardExileZoneId(game, source.getSourceId()), "Gerrymandering");
            }

            // Give each player a number of those cards chosen at random equal to the number of those cards the player controlled.
            // Each player returns those cards to the battlefield under their control.
            for (UUID playerId : playerLandCount.keySet()) {
                if (exiledCards.isEmpty()) {
                    break;
                }
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (int i = 1; i <= playerLandCount.get(playerId); i++) {
                        Card card = exiledCards.getRandom(game);
                        exiledCards.remove(card);
                        player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                        if (exiledCards.isEmpty()) {
                            break;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
