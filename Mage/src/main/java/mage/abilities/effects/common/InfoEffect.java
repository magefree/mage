package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class InfoEffect extends OneShotEffect {

    public InfoEffect(String text) {
        super(Outcome.Neutral);
        this.staticText = text;
    }

    public InfoEffect(final InfoEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public InfoEffect copy() {
        return new InfoEffect(this);
    }

    public static void addInfoToPermanent(Game game, Ability source, Permanent permanent, String info) {
        // add simple static info to permanent's rules
        SimpleStaticAbility ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect(info));
        GainAbilityTargetEffect gainAbilityEffect = new GainAbilityTargetEffect(ability, Duration.WhileOnBattlefield);
        gainAbilityEffect.setTargetPointer(new FixedTarget(permanent.getId()));
        game.addEffect(gainAbilityEffect, source);
    }
}
