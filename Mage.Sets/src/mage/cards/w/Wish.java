package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class Wish extends CardImpl {

    public Wish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // You may play a card you own from outside the game this turn.
        this.getSpellAbility().addEffect(new WishEffect());
    }

    private Wish(final Wish card) {
        super(card);
    }

    @Override
    public Wish copy() {
        return new Wish(this);
    }
}

class WishEffect extends OneShotEffect {

    public WishEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may play a card you own from outside the game this turn";
    }

    private WishEffect(final WishEffect effect) {
        super(effect);
    }

    @Override
    public WishEffect copy() {
        return new WishEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getSideboard().isEmpty()) {
            controller.lookAtCards(source, "Sideboard", controller.getSideboard(), game);
            game.addEffect(new WishPlayFromSideboardEffect(), source);
            return true;
        }
        return false;
    }
}

class WishPlayFromSideboardEffect extends AsThoughEffectImpl {

    public WishPlayFromSideboardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
    }

    private WishPlayFromSideboardEffect(final WishPlayFromSideboardEffect effect) {
        super(effect);
    }

    @Override
    public WishPlayFromSideboardEffect copy() {
        return new WishPlayFromSideboardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.getControllerId().equals(affectedControllerId)) {
            Player controller = game.getPlayer(source.getControllerId());
            UUID mainCardId = CardUtil.getMainCardId(game, objectId);
            if (controller != null && controller.getSideboard().contains(mainCardId)) {
                // Only allow one card to be played
                if (!game.inCheckPlayableState()) {
                    discard();
                }
                return true;
            }
        }
        return false;
    }
}
