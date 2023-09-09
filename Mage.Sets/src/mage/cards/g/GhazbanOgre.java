
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author MarcoMarin, Watch out! This one I actually made from scratch!(1st
 * time \o/) Not even checked similars :) beware!
 */
public final class GhazbanOgre extends CardImpl {

    public GhazbanOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, if a player has more life than each other player, the player with the most life gains control of Ghazbán Ogre.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GhazbanOgreEffect(), TargetController.YOU, false));

    }

    private GhazbanOgre(final GhazbanOgre card) {
        super(card);
    }

    @Override
    public GhazbanOgre copy() {
        return new GhazbanOgre(this);
    }
}

class GhazbanOgreEffect extends OneShotEffect {

    public GhazbanOgreEffect() {
        super(Outcome.GainControl);
        this.staticText = "the player with the most life gains control of {this}";
    }

    private GhazbanOgreEffect(final GhazbanOgreEffect effect) {
        super(effect);
    }

    @Override
    public GhazbanOgreEffect copy() {
        return new GhazbanOgreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                Player newController = null;
                int lowLife = Integer.MIN_VALUE;
                boolean tie = false;
                for (UUID playerID : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerID);
                    if (player != null) {
                        if (player.getLife() > lowLife) {
                            lowLife = player.getLife();
                            newController = player;
                            tie = false;
                        } else if (player.getLife() == lowLife) {
                            tie = true;
                        }
                    }
                }
                if (!controller.equals(newController) && !tie && newController != null) {
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newController.getId());
                    effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;

    }
}
