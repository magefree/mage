package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 * @author TheElk801
 */
public final class LukkaWaywardBonderEmblem extends Emblem {

    // −7: You get an emblem with "Whenever a creature enters the battlefield under your control, it deals damage equal to its power to any target."
    public LukkaWaywardBonderEmblem() {
        this.setName("Emblem Lukka");
        this.setExpansionSetCodeForImage("STX");
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.COMMAND, new LukkaWaywardBonderEmblemEffect(),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, false
        );
        ability.addTarget(new TargetAnyTarget());
        this.getAbilities().add(ability);
    }
}

class LukkaWaywardBonderEmblemEffect extends OneShotEffect {

    LukkaWaywardBonderEmblemEffect() {
        super(Outcome.Benefit);
        staticText = "it deals damage equal to its power to any target";
    }

    private LukkaWaywardBonderEmblemEffect(final LukkaWaywardBonderEmblemEffect effect) {
        super(effect);
    }

    @Override
    public LukkaWaywardBonderEmblemEffect copy() {
        return new LukkaWaywardBonderEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null || permanent.getPower().getValue() < 1) {
            return false;
        }
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        if (targetPermanent != null) {
            return targetPermanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game) > 0;
        }
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            return targetPlayer.damage(permanent.getPower().getValue(), permanent.getId(), source, game) > 0;
        }
        return false;
    }
}
