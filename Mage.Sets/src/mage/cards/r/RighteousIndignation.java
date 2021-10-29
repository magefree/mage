package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class RighteousIndignation extends CardImpl {

    public RighteousIndignation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Whenever a creature blocks a black or red creature, the blocking creature gets +1/+1 until end of turn.
        this.addAbility(new RighteousIndignationTriggeredAbility());

    }

    private RighteousIndignation(final RighteousIndignation card) {
        super(card);
    }

    @Override
    public RighteousIndignation copy() {
        return new RighteousIndignation(this);
    }
}

class RighteousIndignationTriggeredAbility extends TriggeredAbilityImpl {

    public RighteousIndignationTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn));
    }

    public RighteousIndignationTriggeredAbility(final RighteousIndignationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RighteousIndignationTriggeredAbility copy() {
        return new RighteousIndignationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BLOCKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent blocker = game.getPermanent(event.getSourceId());
        Permanent blocked = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (blocker != null) {
            if (blocked != null) {
                if (blocked.getColor(game).contains(ObjectColor.BLACK)
                        || blocked.getColor(game).contains(ObjectColor.RED)) {
                    getEffects().get(0).setTargetPointer(new FixedTarget(blocker.getId(), game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature blocks a black or red creature, the blocking creature gets +1/+1 until end of turn.";
    }
}
