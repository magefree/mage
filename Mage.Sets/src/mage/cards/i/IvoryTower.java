
package mage.cards.i;

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
 * @author LoneFox

 */
public final class IvoryTower extends CardImpl {

    public IvoryTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new IvoryTowerEffect(), TargetController.YOU, false));
    }

    private IvoryTower(final IvoryTower card) {
        super(card);
    }

    @Override
    public IvoryTower copy() {
        return new IvoryTower(this);
    }
}

class IvoryTowerEffect extends OneShotEffect {

    public IvoryTowerEffect() {
        super(Outcome.GainLife);
        this.staticText = "you gain X life, where X is the number of cards in your hand minus 4.";
    }

    private IvoryTowerEffect(final IvoryTowerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if(player != null) {
            int amount = player.getHand().size() - 4;
            if(amount > 0) {
                player.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public IvoryTowerEffect copy() {
        return new IvoryTowerEffect(this);
    }

}

