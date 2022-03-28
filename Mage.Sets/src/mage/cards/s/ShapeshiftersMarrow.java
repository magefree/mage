
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * author HCrescent
 */
public final class ShapeshiftersMarrow extends CardImpl {

    public ShapeshiftersMarrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // At the beginning of each opponent's upkeep, that player reveals the top card of their library. If it's a creature card, the player puts the card into their graveyard and Shapeshifter's Marrow becomes a copy of that card. (If it does, it loses this ability.)
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new ShapeshiftersMarrowEffect(), TargetController.OPPONENT, false));
    }

    private ShapeshiftersMarrow(final ShapeshiftersMarrow card) {
        super(card);
    }

    @Override
    public ShapeshiftersMarrow copy() {
        return new ShapeshiftersMarrow(this);
    }

    static class ShapeshiftersMarrowEffect extends OneShotEffect {

        public ShapeshiftersMarrowEffect() {
            super(Outcome.BecomeCreature);
            this.staticText = "that player reveals the top card of their library. If it's a creature card, the player puts the card into their graveyard and {this} becomes a copy of that card. (If it does, it loses this ability.)";
        }

        public ShapeshiftersMarrowEffect(final ShapeshiftersMarrowEffect effect) {
            super(effect);
        }

        @Override
        public ShapeshiftersMarrowEffect copy() {
            return new ShapeshiftersMarrowEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player activePlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            MageObject sourceObject = game.getObject(source);
            if (activePlayer != null && sourceObject != null) {
                Card card = activePlayer.getLibrary().getFromTop(game);
                if (card != null) {
                    activePlayer.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                    if (card.isCreature(game)) {
                        activePlayer.moveCards(activePlayer.getLibrary().getTopCards(game, 1), Zone.GRAVEYARD, source, game);
                        CopyEffect copyEffect = new CopyEffect(Duration.Custom, card, source.getSourceId());
                        game.addEffect(copyEffect, source);
                    }
                }

                return true;
            }
            return false;
        }

    }

}
