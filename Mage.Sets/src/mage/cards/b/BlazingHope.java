
package mage.cards.b;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BlazingHope extends CardImpl {

    public BlazingHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Exile target creature with power greater than or equal to your life total.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new BlazingHopeTarget());
    }

    private BlazingHope(final BlazingHope card) {
        super(card);
    }

    @Override
    public BlazingHope copy() {
        return new BlazingHope(this);
    }
}

class BlazingHopeTarget extends TargetCreaturePermanent {

    public BlazingHopeTarget() {
        super(new FilterCreaturePermanent("creature with power greater than or equal to your life total"));
    }

    public BlazingHopeTarget(final BlazingHopeTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        if (permanent != null) {
            if (!isNotTarget()) {
                if (!permanent.canBeTargetedBy(game.getObject(source.getId()), controllerId, game)
                        || !permanent.canBeTargetedBy(game.getObject(source), controllerId, game)) {
                    return false;
                }
            }
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null && permanent.getPower().getValue() >= controller.getLife()) {
                return filter.match(permanent, controllerId, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        int remainingTargets = this.minNumberOfTargets - targets.size();
        if (remainingTargets <= 0) {
            return true;
        }
        int count = 0;
        Player controller = game.getPlayer(sourceControllerId);
        MageObject targetSource = game.getObject(source);
        if(targetSource != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
                if (!targets.containsKey(permanent.getId())) {
                    if (notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, game)) {
                        if (controller != null && permanent.getPower().getValue() >= controller.getLife()) {
                            count++;
                            if (count >= remainingTargets) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public BlazingHopeTarget copy() {
        return new BlazingHopeTarget(this);
    }
}
