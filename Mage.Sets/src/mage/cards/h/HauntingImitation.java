package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.PermanentCard;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HauntingImitation extends CardImpl {

    public HauntingImitation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player reveals the top card of their library. For each creature card revealed this way, create a token that's a copy of that card, except it's 1/1, it's a Spirit in addition to its other types, and it has flying. If no creature cards were revealed this way, return Haunting Imitation to its owner's hand.
        this.getSpellAbility().addEffect(new HauntingImitationEffect());
    }

    private HauntingImitation(final HauntingImitation card) {
        super(card);
    }

    @Override
    public HauntingImitation copy() {
        return new HauntingImitation(this);
    }
}

class HauntingImitationEffect extends OneShotEffect {

    HauntingImitationEffect() {
        super(Outcome.Benefit);
        staticText = "each player reveals the top card of their library. For each creature card revealed this way, " +
                "create a token that's a copy of that card, except it's 1/1, it's a Spirit " +
                "in addition to its other types, and it has flying. If no creature cards were revealed this way, " +
                "return {this} to its owner's hand";
    }

    private HauntingImitationEffect(final HauntingImitationEffect effect) {
        super(effect);
    }

    @Override
    public HauntingImitationEffect copy() {
        return new HauntingImitationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            Card card = player.getLibrary().getFromTop(game);
            if (card == null) {
                continue;
            }
            player.revealCards(source, new CardsImpl(cards), game);
            if (card.isCreature(game)) {
                cards.add(card);
            }
        }
        if (cards.isEmpty()) {
            Player player = game.getPlayer(source.getControllerId());
            MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
            if (player != null && sourceObject instanceof Card) {
                player.moveCards((Card) sourceObject, Zone.HAND, source, game);
            }
            return true;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, 1, false,
                false, null, 1, 1, true
        );
        effect.setAdditionalSubType(SubType.SPIRIT);
        for (Card card : cards.getCards(game)) {
            effect.setSavedPermanent(new PermanentCard(card, source.getControllerId(), game));
            effect.apply(game, source);
        }
        return true;
    }
}
