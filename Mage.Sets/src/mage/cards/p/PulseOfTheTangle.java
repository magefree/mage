
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.BeastToken;
import mage.players.Player;

/**
 *
 * @author wetterlicht
 */
public final class PulseOfTheTangle extends CardImpl {

    public PulseOfTheTangle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{G}");

        // Create a 3/3 green Beast creature token. Then if an opponent controls more creatures than you, return Pulse of the Tangle to its owner's hand.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken()));
        this.getSpellAbility().addEffect(new PulseOfTheTangleReturnToHandEffect());
    }

    private PulseOfTheTangle(final PulseOfTheTangle card) {
        super(card);
    }

    @Override
    public PulseOfTheTangle copy() {
        return new PulseOfTheTangle(this);
    }
}

class PulseOfTheTangleReturnToHandEffect extends OneShotEffect {

    PulseOfTheTangleReturnToHandEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if an opponent controls more creatures than you, return {this} to its owner's hand";
    }

    private PulseOfTheTangleReturnToHandEffect(final PulseOfTheTangleReturnToHandEffect effect) {
        super(effect);
    }

    @Override
    public PulseOfTheTangleReturnToHandEffect copy() {
        return new PulseOfTheTangleReturnToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        FilterControlledCreaturePermanent controllerFilter = new FilterControlledCreaturePermanent();
        PermanentsOnBattlefieldCount controllerCount = new PermanentsOnBattlefieldCount(controllerFilter);
        int controllerAmount = controllerCount.calculate(game, source, this);
        boolean check = false;
        if (controller != null) {
            for (UUID opponentID : game.getOpponents(controller.getId())) {
                if (opponentID != null) {
                    FilterCreaturePermanent opponentFilter = new FilterCreaturePermanent();
                    opponentFilter.add(new ControllerIdPredicate(opponentID));
                    PermanentsOnBattlefieldCount opponentCreatureCount = new PermanentsOnBattlefieldCount(opponentFilter);
                    check = opponentCreatureCount.calculate(game, source, this) > controllerAmount;
                    if (check) {
                        break;
                    }
                }
            }
            if (check) {
                Card card = game.getCard(source.getSourceId());
                controller.moveCards(card, Zone.HAND, source, game);
                return true;
            }
        }
        return false;
    }
}
