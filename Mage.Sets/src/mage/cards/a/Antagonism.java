package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.watchers.common.BloodthirstWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class Antagonism extends CardImpl {

    private static final String rule = "{this} deals 2 damage to that player unless one of their opponents was dealt damage this turn";

    public Antagonism(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // At the beginning of each player's end step, Antagonism deals 2 damage to that player unless one of their opponents was dealt damage this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ConditionalOneShotEffect(new DamageTargetEffect(2),
                new OpponentWasNotDealtDamageCondition(), rule), TargetController.ANY, false));

    }

    private Antagonism(final Antagonism card) {
        super(card);
    }

    @Override
    public Antagonism copy() {
        return new Antagonism(this);
    }
}

class OpponentWasNotDealtDamageCondition implements Condition {

    public OpponentWasNotDealtDamageCondition() {
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID activePlayer = game.getState().getActivePlayerId();
        if (activePlayer != null) {
            BloodthirstWatcher watcher = game.getState().getWatcher(BloodthirstWatcher.class, activePlayer);
            if (watcher != null) {
                return !watcher.conditionMet();
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if an opponent was dealt damage this turn";
    }
}
