
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author jeffwadsworth
 */
public final class LayBare extends CardImpl {

    public LayBare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}{U}");


        // Counter target spell. Look at its controller's hand.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new LayBareEffect());
    }

    private LayBare(final LayBare card) {
        super(card);
    }

    @Override
    public LayBare copy() {
        return new LayBare(this);
    }
}

class LayBareEffect extends OneShotEffect {

    public LayBareEffect() {
        super(Outcome.Benefit);
        staticText = "Look at its controller's hand";
    }

    private LayBareEffect(final LayBareEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card target = (Card) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK);
        if (target != null) {
            Player controller = game.getPlayer(target.getOwnerId());
            if (controller != null && player != null) {
                player.lookAtCards("Lay Bare", controller.getHand(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public LayBareEffect copy() {
        return new LayBareEffect(this);
    }
}