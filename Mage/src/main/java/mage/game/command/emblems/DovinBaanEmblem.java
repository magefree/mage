
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionUntapNotMoreThanEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public final class DovinBaanEmblem extends Emblem {

    public DovinBaanEmblem() {
        this.setName("Emblem Dovin");
        Ability ability = new SimpleStaticAbility(Zone.COMMAND, new DovinBaanCantUntapEffect());
        this.getAbilities().add(ability);

        this.setExpansionSetCodeForImage("KLD");
    }
}

class DovinBaanCantUntapEffect extends RestrictionUntapNotMoreThanEffect {

    DovinBaanCantUntapEffect() {
        super(Duration.WhileOnBattlefield, 2, StaticFilters.FILTER_CONTROLLED_PERMANENT);
        staticText = "Your opponents can't untap more than two permanents during their untap steps.";
    }

    DovinBaanCantUntapEffect(final DovinBaanCantUntapEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Player player, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(player.getId());
    }

    @Override
    public DovinBaanCantUntapEffect copy() {
        return new DovinBaanCantUntapEffect(this);
    }
}
