
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 *
 * @author LoneFox

 */
public final class Impatience extends CardImpl {

    public Impatience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // At the beginning of each player's end step, if that player didn't cast a spell this turn, Impatience deals 2 damage to that player.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("{this} deals 2 damage to that player.");
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.ANY,
            new ImpatienceCondition(), false));
    }

    private Impatience(final Impatience card) {
        super(card);
    }

    @Override
    public Impatience copy() {
        return new Impatience(this);
    }
}

class ImpatienceCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(game.getActivePlayerId()) == 0;
    }

    public String toString() {
        return "if that player didn't cast a spell this turn";
    }
}
