
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.CyclingAbility;
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
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class WildDogs extends CardImpl {

    public WildDogs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, if a player has more life than each other player, the player with the most life gains control of Wild Dogs.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WildDogsEffect(), TargetController.YOU, false));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private WildDogs(final WildDogs card) {
        super(card);
    }

    @Override
    public WildDogs copy() {
        return new WildDogs(this);
    }
}

class WildDogsEffect extends OneShotEffect {

    public WildDogsEffect() {
        super(Outcome.GainControl);
        this.staticText = "the player with the most life gains control of {this}";
    }

    public WildDogsEffect(final WildDogsEffect effect) {
        super(effect);
    }

    @Override
    public WildDogsEffect copy() {
        return new WildDogsEffect(this);
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