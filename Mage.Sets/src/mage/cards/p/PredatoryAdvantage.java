
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.LizardToken;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public final class PredatoryAdvantage extends CardImpl {

    public PredatoryAdvantage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{G}");

        // At the beginning of each opponent's end step, if that player didn't cast a creature spell this turn, create a 2/2 green Lizard creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new LizardToken()), TargetController.OPPONENT, new DidNotCastCreatureCondition(), false), new CastCreatureWatcher());
    }

    private PredatoryAdvantage(final PredatoryAdvantage card) {
        super(card);
    }

    @Override
    public PredatoryAdvantage copy() {
        return new PredatoryAdvantage(this);
    }
}

class DidNotCastCreatureCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            Watcher watcher = game.getState().getWatcher(CastCreatureWatcher.class, source.getSourceId());
            if (watcher != null && !watcher.conditionMet()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "if that player didn't cast a creature spell this turn";
    }
}

class CastCreatureWatcher extends Watcher {

    public CastCreatureWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && game.isActivePlayer(event.getPlayerId())
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.isCreature(game)) {
                condition = true;
            }
        }
    }
}
