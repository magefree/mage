
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class UndiscoveredParadise extends CardImpl {

    public UndiscoveredParadise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add one mana of any color. During your next untap step, as you untap your permanents, return Undiscovered Paradise to its owner's hand.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new UndiscoveredParadiseEffect());
        this.addAbility(ability);
    }

    private UndiscoveredParadise(final UndiscoveredParadise card) {
        super(card);
    }

    @Override
    public UndiscoveredParadise copy() {
        return new UndiscoveredParadise(this);
    }
}

class UndiscoveredParadiseEffect extends OneShotEffect {

    public UndiscoveredParadiseEffect() {
        super(Outcome.Neutral);
        staticText = "During your next untap step, as you untap your permanents, return {this} to its owner's hand";
    }

    private UndiscoveredParadiseEffect(final UndiscoveredParadiseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Effect effect = new ReturnToHandTargetEffect();
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addDelayedTriggeredAbility(new AtBeginningOfUntapDelayedTriggeredAbility(effect), source);
            return true;
        }
        return false;
    }

    @Override
    public UndiscoveredParadiseEffect copy() {
        return new UndiscoveredParadiseEffect(this);
    }
}

class AtBeginningOfUntapDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public AtBeginningOfUntapDelayedTriggeredAbility(Effect effect) {
        super(effect);
        this.usesStack = false;
    }

    private AtBeginningOfUntapDelayedTriggeredAbility(final AtBeginningOfUntapDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public AtBeginningOfUntapDelayedTriggeredAbility copy() {
        return new AtBeginningOfUntapDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Return {this} to its owner's hand.";
    }
}
