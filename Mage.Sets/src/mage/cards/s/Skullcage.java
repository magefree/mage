package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Skullcage extends CardImpl {

    public Skullcage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of each opponent's upkeep, Skullcage deals 2 damage to that player unless they have exactly three or exactly four cards in hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SkullcageEffect(), TargetController.OPPONENT, false, true));
    }

    private Skullcage(final Skullcage card) {
        super(card);
    }

    @Override
    public Skullcage copy() {
        return new Skullcage(this);
    }
}

class SkullcageEffect extends OneShotEffect {


    public SkullcageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to that player unless they have exactly three or exactly four cards in hand";
    }

    private SkullcageEffect(final SkullcageEffect effect) {
        super(effect);
    }

    @Override
    public SkullcageEffect copy() {
        return new SkullcageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            if (player.getHand().size() != 3 && player.getHand().size() != 4) {
                player.damage(2, source.getSourceId(), source, game);
            }
        }
        return false;
    }

}
