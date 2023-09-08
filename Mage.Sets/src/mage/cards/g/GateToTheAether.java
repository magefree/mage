
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class GateToTheAether extends CardImpl {

    public GateToTheAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // At the beginning of each player's upkeep, that player reveals the top card of their library. If it's an artifact, creature, enchantment, or land card, the player may put it onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new GateToTheAetherEffect(), TargetController.ANY, false, true));
    }

    private GateToTheAether(final GateToTheAether card) {
        super(card);
    }

    @Override
    public GateToTheAether copy() {
        return new GateToTheAether(this);
    }
}

class GateToTheAetherEffect extends OneShotEffect {

    GateToTheAetherEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "that player reveals the top card of their library. If it's an artifact, creature, enchantment, or land card, the player may put it onto the battlefield";
    }

    private GateToTheAetherEffect(final GateToTheAetherEffect effect) {
        super(effect);
    }

    @Override
    public GateToTheAetherEffect copy() {
        return new GateToTheAetherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player activePlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (activePlayer != null) {
            Card card = activePlayer.getLibrary().getFromTop(game);
            if (card != null) {
                activePlayer.revealCards("Gate to the Aether", new CardsImpl(card), game);
                if (card.isArtifact(game)
                        || card.isCreature(game)
                        || card.isEnchantment(game)
                        || card.isLand(game)) {
                    if (activePlayer.chooseUse(Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield?", source, game)) {
                        activePlayer.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
