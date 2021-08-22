
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.WolfToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class WolfcallersHowl extends CardImpl {

    public WolfcallersHowl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}");


        // At the beginning of your upkeep, create X 2/2 green Wolf creature tokens, where X is the number of your opponents with four or more cards in hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new WolfcallersHowlEffect(), TargetController.YOU, false));
    }

    private WolfcallersHowl(final WolfcallersHowl card) {
        super(card);
    }

    @Override
    public WolfcallersHowl copy() {
        return new WolfcallersHowl(this);
    }
}

class WolfcallersHowlEffect extends OneShotEffect {

    public WolfcallersHowlEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create X 2/2 green Wolf creature tokens, where X is the number of your opponents with four or more cards in hand";
    }

    public WolfcallersHowlEffect(final WolfcallersHowlEffect effect) {
        super(effect);
    }

    @Override
    public WolfcallersHowlEffect copy() {
        return new WolfcallersHowlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = 0;
            for(UUID playerId :game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(playerId, game)) {
                    Player opponent = game.getPlayer(playerId);
                    if (opponent != null) {
                        if (opponent.getHand().size() >= 4) {
                            count++;
                        }
                    }
                }
            }
            if (count > 0) {
                return new CreateTokenEffect(new WolfToken(), count).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
