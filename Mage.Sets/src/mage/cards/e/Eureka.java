
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class Eureka extends CardImpl {

    public Eureka(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");

        // Starting with you, each player may put a permanent card from their hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield.
        this.getSpellAbility().addEffect(new EurekaEffect());
    }

    private Eureka(final Eureka card) {
        super(card);
    }

    @Override
    public Eureka copy() {
        return new Eureka(this);
    }
}

class EurekaEffect extends OneShotEffect {

    public EurekaEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Starting with you, each player may put a permanent card from their hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield";
    }

    public EurekaEffect(final EurekaEffect effect) {
        super(effect);
    }

    @Override
    public EurekaEffect copy() {
        return new EurekaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;
            Target target = new TargetCardInHand(new FilterPermanentCard());

            while (controller.canRespond()) {
                if (firstInactivePlayer == null) {
                    firstInactivePlayer = currentPlayer.getId();
                }
                if (currentPlayer != null && currentPlayer.canRespond() && game.getState().getPlayersInRange(controller.getId(), game).contains(currentPlayer.getId())) {

                    target.clearChosen();
                    if (target.canChoose(currentPlayer.getId(), source, game)
                            && currentPlayer.chooseUse(outcome, "Put permanent from your hand to play?", source, game)) {
                        if (target.chooseTarget(outcome, currentPlayer.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                currentPlayer.moveCards(card, Zone.BATTLEFIELD, source, game);
                                firstInactivePlayer = null;
                            }
                        }
                    }
                }
                // get next player
                playerList.getNext();
                currentPlayer = game.getPlayer(playerList.get());
                // if all player since this player didn't put permanent in play finish the process
                if (currentPlayer != null &&currentPlayer.getId().equals(firstInactivePlayer)) {
                    break;
                }
            }
            return true;
        }
        return false;
    }

}
