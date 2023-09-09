
package mage.cards.h;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class HowlingMine extends CardImpl {

    public HowlingMine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        this.addAbility(new HowlingMineAbility());
    }

    private HowlingMine(final HowlingMine card) {
        super(card);
    }

    @Override
    public HowlingMine copy() {
        return new HowlingMine(this);
    }

}

class HowlingMineAbility extends TriggeredAbilityImpl {

    public HowlingMineAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1));
    }

    private HowlingMineAbility(final HowlingMineAbility ability) {
        super(ability);
    }

    @Override
    public HowlingMineAbility copy() {
        return new HowlingMineAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Permanent source = game.getPermanentOrLKIBattlefield(this.sourceId);
        return source != null && !source.isTapped();
    }

    @Override
    public String getRule() {
        return "At the beginning of each player's draw step, if {this} is untapped, that player draws an additional card.";
    }

}
