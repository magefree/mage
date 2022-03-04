package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class BattleCry extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public BattleCry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Untap all white creatures you control.
        this.getSpellAbility().addEffect(new UntapAllEffect(filter));

        // Whenever a creature blocks this turn, it gets +0/+1 until end of turn.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BattleCryTriggeredAbility()));
    }

    private BattleCry(final BattleCry card) {
        super(card);
    }

    @Override
    public BattleCry copy() {
        return new BattleCry(this);
    }
}

class BattleCryTriggeredAbility extends DelayedTriggeredAbility {

    public BattleCryTriggeredAbility() {
        super(new BoostTargetEffect(0, 1, Duration.EndOfTurn), Duration.EndOfTurn, false, false);
    }

    public BattleCryTriggeredAbility(final BattleCryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BattleCryTriggeredAbility copy() {
        return new BattleCryTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "whenever a creature blocks this turn, it gets +0/+1 until end of turn";
    }
}
