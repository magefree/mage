package mage.cards.b;

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
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class Bulwark extends CardImpl {

    public Bulwark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");


        // At the beginning of your upkeep, Bulwark deals X damage to target opponent, where X is
        // the number of cards in your hand minus the number of cards in that player's hand.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new BulwarkDamageEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private Bulwark(final Bulwark card) {
        super(card);
    }

    @Override
    public Bulwark copy() {
        return new Bulwark(this);
    }
}

class BulwarkDamageEffect extends OneShotEffect {

    public BulwarkDamageEffect() {
        super(Outcome.Damage);
        staticText = "Bulwark deals X damage to target opponent, where X is the number of cards in your hand minus the number of cards in that player's hand";
    }

    private BulwarkDamageEffect(final BulwarkDamageEffect effect) {
        super(effect);
    }

    @Override
    public BulwarkDamageEffect copy() {
        return new BulwarkDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (opponent != null && controller != null) {
            int amount = controller.getHand().size() - opponent.getHand().size();
            if (amount > 0) {
                opponent.damage(amount, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}
