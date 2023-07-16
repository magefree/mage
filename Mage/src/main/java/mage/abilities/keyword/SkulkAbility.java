package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class SkulkAbility extends StaticAbility {

    public SkulkAbility() {
        super(Zone.BATTLEFIELD, new SkulkEffect(Duration.WhileOnBattlefield));
    }

    public SkulkAbility(final SkulkAbility ability) {
        super(ability);
    }

    @Override
    public Ability copy() {
        return new SkulkAbility(this);
    }

    @Override
    public String getRule() {
        return "skulk <i>(This creature can't be blocked by creatures with greater power.)</i>";
    }

}

class SkulkEffect extends RestrictionEffect {

    public SkulkEffect(Duration duration) {
        super(duration);
        staticText = "Skulk <i>(This creature can't be blocked by creatures with greater power.)</i>";
    }

    public SkulkEffect(final SkulkEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return blocker.getPower().getValue() <= attacker.getPower().getValue();
    }

    @Override
    public SkulkEffect copy() {
        return new SkulkEffect(this);
    }
}
