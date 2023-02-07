
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class CreepingBloodsucker extends CardImpl {

    public CreepingBloodsucker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, Creeping Bloodsucker deals 1 damage to each opponent. You gain life equal to the damage dealt this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreepingBloodsuckerEffect(), TargetController.YOU, false));
    }

    private CreepingBloodsucker(final CreepingBloodsucker card) {
        super(card);
    }

    @Override
    public CreepingBloodsucker copy() {
        return new CreepingBloodsucker(this);
    }
}

class CreepingBloodsuckerEffect extends OneShotEffect {
    public CreepingBloodsuckerEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 1 damage to each opponent. You gain life equal to the damage dealt this way";
    }

    public CreepingBloodsuckerEffect(final CreepingBloodsuckerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damageDealt = 0;
        Player player = game.getPlayer(source.getControllerId());
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            if (player.hasOpponent(playerId, game)) {
                damageDealt += game.getPlayer(playerId).damage(1, source.getSourceId(), source, game);
            }
        }
        if (damageDealt > 0) {
            game.getPlayer(source.getControllerId()).gainLife(damageDealt, game, source);
        }
        return true;
    }

    @Override
    public CreepingBloodsuckerEffect copy() {
        return new CreepingBloodsuckerEffect(this);
    }

}