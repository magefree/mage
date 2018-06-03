
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class SoulConduit extends CardImpl {

    public SoulConduit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {6}, {tap}: Two target players exchange life totals.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SoulConduitEffect(), new GenericManaCost(6));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer(2));
        this.addAbility(ability);
    }

    public SoulConduit(final SoulConduit card) {
        super(card);
    }

    @Override
    public SoulConduit copy() {
        return new SoulConduit(this);
    }
}

class SoulConduitEffect extends OneShotEffect {

    public SoulConduitEffect() {
        super(Outcome.Neutral);
        this.staticText = "Two target players exchange life totals";
    }

    public SoulConduitEffect(final SoulConduitEffect effect) {
        super(effect);
    }

    @Override
    public SoulConduitEffect copy() {
        return new SoulConduitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player1 = game.getPlayer(source.getTargets().get(0).getTargets().get(0));
        Player player2 = game.getPlayer(source.getTargets().get(0).getTargets().get(1));
        if (player1 != null && player2 != null) {
            int lifePlayer1 = player1.getLife();
            int lifePlayer2 = player2.getLife();

            if (lifePlayer1 == lifePlayer2) {
                return false;
            }

            if (!player1.isLifeTotalCanChange() || !player2.isLifeTotalCanChange()) {
                return false;
            }

            // 20110930 - 118.7, 118.8
            if (lifePlayer1 < lifePlayer2 && (!player1.isCanGainLife() || !player2.isCanLoseLife())) {
                return false;
            }

            if (lifePlayer1 > lifePlayer2 && (!player1.isCanLoseLife() || !player2.isCanGainLife())) {
                return false;
            }

            player1.setLife(lifePlayer2, game, source);
            player2.setLife(lifePlayer1, game, source);
            return true;
        }
        return false;
    }
}
