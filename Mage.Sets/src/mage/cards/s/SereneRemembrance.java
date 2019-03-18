package mage.cards.s;

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
import mage.target.common.TargetCardInASingleGraveyard;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SereneRemembrance extends CardImpl {

    public SereneRemembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");


        // Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries.
        this.getSpellAbility().addEffect(new SereneRemembranceEffect());
        this.getSpellAbility().addTarget(new TargetCardInASingleGraveyard(0, 3, new FilterCard("up to three target cards from a single graveyard")));

    }

    public SereneRemembrance(final SereneRemembrance card) {
        super(card);
    }

    @Override
    public SereneRemembrance copy() {
        return new SereneRemembrance(this);
    }
}

class SereneRemembranceEffect extends OneShotEffect {

    public SereneRemembranceEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle Serene Remembrance and up to three target cards from a single graveyard into their owners' libraries";
    }

    public SereneRemembranceEffect(final SereneRemembranceEffect effect) {
        super(effect);
    }

    @Override
    public SereneRemembranceEffect copy() {
        return new SereneRemembranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;

        // 3 cards to graveyard
        Player graveyardPlayer = null;
        for (UUID cardInGraveyard : targetPointer.getTargets(game, source)) {
            Card card = game.getCard(cardInGraveyard);
            if (card != null) {
                for (Player player : game.getPlayers().values()) {
                    if (player.getGraveyard().contains(card.getId())) {
                        graveyardPlayer = player;
                        player.getGraveyard().remove(card);
                        result |= card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
                    }
                }
            }
        }

        // source card to graveyard
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            result |= card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
            Player player = game.getPlayer(card.getOwnerId());
            if (player != null) {
                player.shuffleLibrary(source, game);
            }
            if (graveyardPlayer != null && !graveyardPlayer.equals(player)) {
                graveyardPlayer.shuffleLibrary(source, game);
            }
        }

        return result;
    }
}
