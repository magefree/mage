
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.common.MultikickerCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MultikickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class CometStorm extends CardImpl {

    public CometStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}");

        // Multikicker {1}
        this.addAbility(new MultikickerAbility("{1}"));

        // Choose any target, then choose another any target for each time Comet Storm was kicked. Comet Storm deals X damage to each of them.
        this.getSpellAbility().addEffect(new CometStormEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget(1));
    }

    public CometStorm(final CometStorm card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numbTargets = new MultikickerCount().calculate(game, ability, null) + 1;
            ability.addTarget(new TargetAnyTarget(numbTargets));
        }
    }

    @Override
    public CometStorm copy() {
        return new CometStorm(this);
    }
}

class CometStormEffect extends OneShotEffect {

    public CometStormEffect() {
        super(Outcome.Damage);
        staticText = "Choose any target, then choose another target for each time Comet Storm was kicked. Comet Storm deals X damage to each of them";
    }

    public CometStormEffect(final CometStormEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = source.getManaCostsToPay().getX();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID uuid : this.getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(uuid);
                Player player = game.getPlayer(uuid);
                if (permanent != null) {
                    permanent.damage(damage, source.getSourceId(), game, false, true);
                }
                if (player != null) {
                    player.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CometStormEffect copy() {
        return new CometStormEffect(this);
    }
}
