package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;


import java.util.UUID;


/**
 *
 * @author notshauna
 */

public final class WandasVision extends CardImpl {

    public WandasVision(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // Whenever you cast your second spell each turn, exile cards from the top of your library until you exile a nonland card. You may cast that card without paying its mana cost.
        this.addAbility(new CastSecondSpellTriggeredAbility(new WandasVisionEffect()));
    }

    private WandasVision(final WandasVision card) {
        super(card);
    }

    @Override
    public WandasVision copy() {
        return new WandasVision(this);
    }

    class WandasVisionEffect extends OneShotEffect {

        WandasVisionEffect() {
            super(Outcome.PlayForFree);
            staticText = "exile cards from the top of your library until you exile a nonland card."
                    + " You may cast that card without paying its mana cost.";
        }

        private WandasVisionEffect(final WandasVisionEffect effect) {
            super(effect);
        }

        @Override
        public WandasVisionEffect copy() {
            return new WandasVisionEffect(this);
        }

        private Card getCard(Player player, Cards cards, Game game, Ability source) {
            for (Card card : player.getLibrary().getCards(game)) {
                cards.add(card);
                player.moveCards(card, Zone.EXILED, source, game);
                game.processAction();
                if (!card.isLand(game)) {
                    return card;
                }
            }
            return null;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player == null) {
                return false;
            }
            Cards cards = new CardsImpl();
            Card card = getCard(player, cards, game, source);
            if (card != null) {
                CardUtil.castSpellWithAttributesForFree(player, source, game, card);
                }
            cards.retainZone(Zone.EXILED, game);
            return true;
        }
    }
}
