package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormWorld extends CardImpl {

    public StormWorld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.supertype.add(SuperType.WORLD);

        // At the beginning of each player's upkeep, Storm World deals X damage to that player, where X is 4 minus the number of cards in their hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new StormWorldEffect(), TargetController.ANY, false, true));

    }

    private StormWorld(final StormWorld card) {
        super(card);
    }

    @Override
    public StormWorld copy() {
        return new StormWorld(this);
    }
}

class StormWorldEffect extends OneShotEffect {

    public StormWorldEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals X damage to that player, where X is 4 minus the number of cards in their hand";
    }

    public StormWorldEffect(final StormWorldEffect effect) {
        super(effect);
    }

    @Override
    public StormWorldEffect copy() {
        return new StormWorldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            int damage = 4 - player.getHand().size();
            if (damage > 0) {
                player.damage(damage, source.getSourceId(), source, game);
            }
            return true;
        }

        return false;
    }
}
