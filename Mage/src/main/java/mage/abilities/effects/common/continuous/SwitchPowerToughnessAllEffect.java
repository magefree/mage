

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
        if (this.affectedObjectsSet) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                for (Permanent perm :game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    affectedObjectList.add(new MageObjectReference(perm, game));
                }
            }            
        }
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (this.affectedObjectsSet) {
                for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                    Permanent creature = it.next().getPermanent(game);
                    if (creature != null) {
                        int power = creature.getPower().getValue();
                        creature.getPower().setValue(creature.getToughness().getValue());
                        creature.getToughness().setValue(power);                                            
                    } else {
                        it.remove();
                    }                    
                }                
            } else {
                for (Permanent creature :game.getState().getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
                    int power = creature.getPower().getValue();
                    creature.getPower().setValue(creature.getToughness().getValue());
                    creature.getToughness().setValue(power);                    
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        StringBuilder sb = new StringBuilder();
        sb.append("Switch each creature's power and toughness");
        if (!duration.toString().isEmpty()) {
            sb.append(' ');
            sb.append(duration.toString());
        }
        return sb.toString();
    }

}
