
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControlledFromStartOfControllerTurnPredicate;
import mage.game.Game;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author spjspj & L_J
 */
public final class KeldonTwilight extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you controlled since the beginning of the turn");

    static {
        filter.add(new ControlledFromStartOfControllerTurnPredicate());
    }

    public KeldonTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{R}");

        // At the beginning of each player's end step, if no creatures attacked this turn, that player sacrifices a creature they controlled since the beginning of the turn.
        Effect effect = new SacrificeEffect(filter, 1, "that player ");
        effect.setText("that player sacrifices a creature they controlled since the beginning of the turn");
        BeginningOfEndStepTriggeredAbility ability
                = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY, new KeldonTwilightCondition(), false);
        this.addAbility(ability, new AttackedThisTurnWatcher());
    }

    private KeldonTwilight(final KeldonTwilight card) {
        super(card);
    }

    @Override
    public KeldonTwilight copy() {
        return new KeldonTwilight(this);
    }
}

class KeldonTwilightCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        AttackedThisTurnWatcher watcher = game.getState().getWatcher(AttackedThisTurnWatcher.class);
        if (watcher != null) {
            return watcher.getAttackedThisTurnCreatures().isEmpty();
        }
        return true;
    }

    @Override
    public String toString() {
        return "if no creatures attacked this turn";
    }

}
