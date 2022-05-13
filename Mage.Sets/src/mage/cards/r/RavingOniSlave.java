
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RavingOniSlave extends CardImpl {

    public RavingOniSlave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Raving Oni-Slave enters the battlefield or leaves the battlefield, you lose 3 life if you don't control a Demon.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new RavingOniSlaveEffect(), false));
    }

    private RavingOniSlave(final RavingOniSlave card) {
        super(card);
    }

    @Override
    public RavingOniSlave copy() {
        return new RavingOniSlave(this);
    }
}

class RavingOniSlaveEffect extends OneShotEffect {

    public RavingOniSlaveEffect() {
        super(Outcome.Benefit);
        this.staticText = "you lose 3 life if you don't control a Demon";
    }

    public RavingOniSlaveEffect(final RavingOniSlaveEffect effect) {
        super(effect);
    }

    @Override
    public RavingOniSlaveEffect copy() {
        return new RavingOniSlaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (game.getBattlefield().count(new FilterCreaturePermanent(SubType.DEMON, "Demon"), source.getControllerId(), source, game) < 1) {
                controller.loseLife(3, game, source, false);
            }
            return true;
        }
        return false;
    }
}
