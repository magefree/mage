package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.Set;
import java.util.UUID;

/**
 * @author fireshoes
 */
public final class FeveredVisions extends CardImpl {

    public FeveredVisions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{R}");

        // At the beginning of each player's end step, that player draws a card. If the player is your opponent and has four or more cards in hand,
        // Fevered Visions deals 2 damage to that player.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new FeveredVisionsEffect(), TargetController.ANY, false));
    }

    private FeveredVisions(final FeveredVisions card) {
        super(card);
    }

    @Override
    public FeveredVisions copy() {
        return new FeveredVisions(this);
    }
}

class FeveredVisionsEffect extends OneShotEffect {

    public FeveredVisionsEffect() {
        super(Outcome.DrawCard);
        staticText = "that player draws a card. If the player is your opponent and has four or more cards in hand, {this} deals 2 damage to that player";
    }

    private FeveredVisionsEffect(final FeveredVisionsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID activePlayerId = game.getActivePlayerId();
        Player player = game.getPlayer(activePlayerId);
        if (controller != null && player != null) {
            player.drawCards(1, source, game);
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            if (opponents.contains(player.getId()) && player.getHand().size() > 3) {
                player.damage(2, source.getSourceId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public FeveredVisionsEffect copy() {
        return new FeveredVisionsEffect(this);
    }

}
