
package mage.cards.l;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class LivingEnd extends CardImpl {

    public LivingEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "");

        this.color.setBlack(true);

        // Suspend 3-{2}{B}{B}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl("{2}{B}{B}"), this));
        // Each player exiles all creature cards from their graveyard, then sacrifices all creatures
        // he or she controls, then puts all cards he or she exiled this way onto the battlefield.
        this.getSpellAbility().addEffect(new LivingEndEffect());

    }

    public LivingEnd(final LivingEnd card) {
        super(card);
    }

    @Override
    public LivingEnd copy() {
        return new LivingEnd(this);
    }
}

class LivingEndEffect extends OneShotEffect {

    public LivingEndEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player exiles all creature cards from their graveyard, then sacrifices all creatures he or she controls, then puts all cards he or she exiled this way onto the battlefield";
    }

    public LivingEndEffect(final LivingEndEffect effect) {
        super(effect);
    }

    @Override
    public LivingEndEffect copy() {
        return new LivingEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Map<UUID, Set<Card>> exiledCards = new HashMap<>();
            // move creature cards from graveyard to exile
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cardsPlayer = player.getGraveyard().getCards(new FilterCreatureCard(), game);
                    if (!cardsPlayer.isEmpty()) {
                        exiledCards.put(player.getId(), cardsPlayer);
                        player.moveCards(cardsPlayer, Zone.EXILED, source, game);
                    }
                }
            }
            // sacrifice all creatures
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game)) {
                permanent.sacrifice(source.getSourceId(), game);
            }
            // put exiled cards to battlefield
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> cardsPlayer = exiledCards.get(playerId);
                    if (cardsPlayer != null && !cardsPlayer.isEmpty()) {
                        player.moveCards(cardsPlayer, Zone.BATTLEFIELD, source, game, false, false, false, null);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
