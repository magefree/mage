

package mage.abilities.effects.common.continuous;

import java.util.Iterator;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class SwitchPowerToughnessAllEffect extends ContinuousEffectImpl {

    static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public SwitchPowerToughnessAllEffect(Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SwitchPT_e, Outcome.BoostCreature);
        this.staticText = "Switch each creature's power and toughness" + (duration.toString().isEmpty() ? "" : " " + duration.toString());
    }

    public SwitchPowerToughnessAllEffect(final SwitchPowerToughnessAllEffect effect) {
        super(effect);
    }

    @Override
    public SwitchPowerToughnessAllEffect copy() {
        return new SwitchPowerToughnessAllEffect(this);
    }
    
    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet && game.getPlayer(source.getControllerId()) != null) {
            for (Permanent perm :game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                affectedObjectList.add(new MageObjectReference(perm, game));
            }
        }
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        if (!this.affectedObjectsSet) {
            game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game).forEach(Permanent::switchPowerToughness);
        } else {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still gets boost
                Permanent creature = it.next().getPermanent(game);
                if (creature == null) {
                    it.remove();
                    continue;
                }
                creature.switchPowerToughness();
            }
        }
        return true;
    }
}
