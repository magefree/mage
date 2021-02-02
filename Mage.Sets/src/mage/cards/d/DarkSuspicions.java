
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author ESOF_1617_T5_G3
 */
public final class DarkSuspicions extends CardImpl {

    public DarkSuspicions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // At the beginning of each opponent's upkeep, that player loses X life, where X is the number of cards in that player's hand minus the number of cards in your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DarkSuspicionsEffect(), TargetController.OPPONENT, false));
    }

    private DarkSuspicions(final DarkSuspicions card) {
        super(card);
    }

    @Override
    public DarkSuspicions copy() {
        return new DarkSuspicions(this);
    }
}

class DarkSuspicionsEffect extends OneShotEffect {

    public DarkSuspicionsEffect() {
        super(Outcome.LoseLife);
        staticText = "that player loses X life, where X is the number of cards in that player's hand minus the number of cards in your hand";
    }

    public DarkSuspicionsEffect(final mage.cards.d.DarkSuspicionsEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.d.DarkSuspicionsEffect copy() {
        return new mage.cards.d.DarkSuspicionsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && opponent != null) {
            int xValue = opponent.getHand().size() - controller.getHand().size();
            if (xValue > 0) {
                opponent.loseLife(xValue, game, source, false);
            }
            return true;
        }
        return false;
    }
}