
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class CellarDoor extends CardImpl {

    public CellarDoor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {3}, {tap}: Target player puts the bottom card of their library into their graveyard. If it's a creature card, you create a 2/2 black Zombie creature token.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CellarDoorEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private CellarDoor(final CellarDoor card) {
        super(card);
    }

    @Override
    public CellarDoor copy() {
        return new CellarDoor(this);
    }
}

class CellarDoorEffect extends OneShotEffect {

    public CellarDoorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Target player puts the bottom card of their library into their graveyard. If it's a creature card, you create a 2/2 black Zombie creature token";
    }

    private CellarDoorEffect(final CellarDoorEffect effect) {
        super(effect);
    }

    @Override
    public CellarDoorEffect copy() {
        return new CellarDoorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null && player.getLibrary().hasCards()) {
            Card card = player.getLibrary().removeFromBottom(game);
            if (card != null) {
                player.moveCards(card, Zone.GRAVEYARD, source, game);
                if (card.isCreature(game)) {
                    ZombieToken token = new ZombieToken();
                    token.putOntoBattlefield(1, game, source, source.getControllerId());
                }
            }
            return true;
        }
        return false;
    }
}
