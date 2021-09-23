
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.Target;
import mage.target.TargetSpell;

/**
 *
 * @author Styxo
 */
public final class PersonalEnergyShield extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell that targets you or a permanent you control");

    static {
        filter.add(new PersonalEnergyFieldPredicate());
    }

    public PersonalEnergyShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Counter target spell that targets you or a permanent you control.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    private PersonalEnergyShield(final PersonalEnergyShield card) {
        super(card);
    }

    @Override
    public PersonalEnergyShield copy() {
        return new PersonalEnergyShield(this);
    }
}

class PersonalEnergyFieldPredicate implements ObjectSourcePlayerPredicate<StackObject> {

    @Override
    public boolean apply(ObjectSourcePlayer<StackObject> input, Game game) {
        UUID controllerId = input.getPlayerId();
        if (controllerId == null) {
            return false;
        }

        for (UUID modeId : input.getObject().getStackAbility().getModes().getSelectedModes()) {
            Mode mode = input.getObject().getStackAbility().getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetId : target.getTargets()) {
                    if (controllerId.equals(targetId)) {
                        return true;
                    }
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null && controllerId.equals(permanent.getControllerId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "spell that targets you or a permanent you control";
    }
}
