package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class WheelOfTorture extends CardImpl {

    public WheelOfTorture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // At the beginning of each opponent's upkeep, Wheel of Torture deals X damage to that player, where X is 3 minus the number of cards in their hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new WheelOfTortureEffect(), TargetController.OPPONENT, false);
        this.addAbility(ability);
    }

    private WheelOfTorture(final WheelOfTorture card) {
        super(card);
    }

    @Override
    public WheelOfTorture copy() {
        return new WheelOfTorture(this);
    }
}

class WheelOfTortureEffect extends OneShotEffect {

    private WheelOfTortureEffect(final WheelOfTortureEffect effect) {
        super(effect);
        this.staticText = "Wheel of Torture deals X damage to that player, where X is 3 minus the number of cards in their hand";
    }

    public WheelOfTortureEffect() {
        super(Outcome.Damage);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int amount = 3 - player.getHand().size();
            if (amount > 0) {
                player.damage(amount, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public WheelOfTortureEffect copy() {
        return new WheelOfTortureEffect(this);
    }
}
