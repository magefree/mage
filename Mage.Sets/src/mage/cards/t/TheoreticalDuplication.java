package mage.cards.t;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.PermanentToken;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheoreticalDuplication extends CardImpl {

    public TheoreticalDuplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Whenever a nontoken creature enters the battlefield under an opponent's control this turn, create a token that's a copy of that creature.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new TheoreticalDuplicationTriggeredAbility()));
    }

    private TheoreticalDuplication(final TheoreticalDuplication card) {
        super(card);
    }

    @Override
    public TheoreticalDuplication copy() {
        return new TheoreticalDuplication(this);
    }
}

class TheoreticalDuplicationTriggeredAbility extends DelayedTriggeredAbility {

    private static final CreateTokenCopyTargetEffect makeEffect() {
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setUseLKI(true);
        return effect;
    }

    TheoreticalDuplicationTriggeredAbility() {
        super(makeEffect(), Duration.EndOfTurn, false, false);
    }

    private TheoreticalDuplicationTriggeredAbility(final TheoreticalDuplicationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent eEvent = (EntersTheBattlefieldEvent) event;
        if (!eEvent.getTarget().isCreature(game)
                || eEvent.getTarget() instanceof PermanentToken
                || !game.getOpponents(getControllerId()).contains(eEvent.getTarget().getControllerId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(eEvent.getTarget(), game));
        return true;
    }

    @Override
    public TheoreticalDuplicationTriggeredAbility copy() {
        return new TheoreticalDuplicationTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature enters the battlefield under an opponent's control this turn, " +
                "create a token that's a copy of that creature.";
    }
}
