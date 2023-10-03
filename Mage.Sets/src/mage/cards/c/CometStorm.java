package mage.cards.c;

import mage.abilities.Ability;
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
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
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
        this.getSpellAbility().setTargetAdjuster(CometStormAdjuster.instance);
    }

    private CometStorm(final CometStorm card) {
        super(card);
    }

    @Override
    public CometStorm copy() {
        return new CometStorm(this);
    }
}

enum CometStormAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int numbTargets = MultikickerCount.instance.calculate(game, ability, null) + 1;
        ability.addTarget(new TargetAnyTarget(numbTargets));
    }
}

class CometStormEffect extends OneShotEffect {

    public CometStormEffect() {
        super(Outcome.Damage);
        staticText = "Choose any target, then choose another target for each time this spell was kicked. {this} deals X damage to each of them";
    }

    private CometStormEffect(final CometStormEffect effect) {
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
                    permanent.damage(damage, source.getSourceId(), source, game, false, true);
                }
                if (player != null) {
                    player.damage(damage, source.getSourceId(), source, game);
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
